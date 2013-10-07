package br.ifrn.tads.poo.banco.agencia;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import br.ifrn.tads.poo.banco.Banco;
import br.ifrn.tads.poo.banco.app.ConnectionFactory;
import br.ifrn.tads.poo.banco.cliente.Cliente;
import br.ifrn.tads.poo.banco.cliente.PessoaFisica;
import br.ifrn.tads.poo.banco.cliente.PessoaJuridica;

public class Agencia {

	private Connection connection;

	private int id, numero;
	private String nome, endereco, nome_gerente;
	private Banco banco;
	private Cliente cliente;
	private ArrayList<Conta> contas;	 	// Agencia hasMany Conta -> Conta belongsTo Agencia
	private ArrayList<Cliente> clientes;	// Agencia hasMany Cliente -> Cliente belongsTo Agencia
	
	public Agencia(int id, Banco banco, int numero, String nome, String endereco, String nome_gerente) {
		this.id 			= id;
		this.numero 		= numero;
		this.nome 			= nome;
		this.endereco 		= endereco;
		this.nome_gerente 	= nome_gerente;
		
		this.banco 			= banco;
		this.contas 		= new ArrayList<Conta>(); 		// habilita a adição de contas
		this.clientes 		= new ArrayList<Cliente>(); 	// habilita a adição de clientes
	}
	
	public Agencia(Banco banco, String nome, String endereco, String nome_gerente) throws SQLException{
		this.numero = new Double( Math.random() * 100000 ).intValue(); // numero automatico
		this.nome = nome;
		this.endereco = endereco;
		this.nome_gerente = nome_gerente;
		
		this.banco = banco;
		this.contas = new ArrayList<Conta>(); 		// habilita a adição de contas
		this.clientes = new ArrayList<Cliente>(); 	// habilita a adição de clientes
		
		this.insert();
	}
	
	private void insert() throws SQLException{
		// como os campos são obrigatorios, aproveita para cadastrar automaticamente no BD
		this.connection = new ConnectionFactory().getConnection();
		
		//PreparedStatement statement = null;
	    ResultSet generatedKeys = null;
		
		String sql = "INSERT INTO `agencias` (banco_id, numero, nome, endereco, nome_gerente) VALUES (?,?,?,?,?)";
		PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
		
		stmt.setLong(1, this.banco.getId());
		stmt.setLong(2, this.numero);
		stmt.setString(3, this.nome);
		stmt.setString(4, this.endereco);
		stmt.setString(5, this.nome_gerente);
		//stmt.execute();
		
		int affectedRows = stmt.executeUpdate();
        if (affectedRows == 0) {
            throw new SQLException("Creating user failed, no rows affected.");
        }

        generatedKeys = stmt.getGeneratedKeys();
        if (generatedKeys.next()) {
            this.setId(generatedKeys.getInt(1));
        } else {
            throw new SQLException("Creating user failed, no generated key obtained.");
        }
	}
	
	public void delete() throws SQLException{
		this.connection = new ConnectionFactory().getConnection();
		String sql = "DELETE FROM agencias WHERE id = ?";
		PreparedStatement stmt = connection.prepareStatement(sql);
		
		stmt.setLong(1, this.id); 
		stmt.execute();
	}

	public String toString(){
		return this.id + "; " + this.numero + "; " + this.nome;
		//return "ID: " + String.valueOf(this.id) + "; Numero: " + String.valueOf(this.numero) + "; Nome: " + String.valueOf(this.nome);
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public int getNumero() {
		return numero;
	}
	
	public void setNumero(int numero) {
		this.numero = numero;
	}
	
	public String getNome() {
		return nome;
	}
	
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public String getEndereco() {
		return endereco;
	}
	
	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}
	
	public String getNomeGerente() {
		return nome_gerente;
	}
	
	public void setNomeGerente(String nome_gerente) {
		this.nome_gerente = nome_gerente;
	}
	
	public void criarContaCorrente(Cliente cliente, double limiteConta) throws SQLException {
		this.contas.add(new ContaCorrente(this, cliente, limiteConta));
	}
	
	public void criarContaPoupanca(Cliente cliente) throws SQLException {
		this.contas.add(new ContaPoupanca(this, cliente));
	}
	
	/*
	public void adicionarCliente(Cliente clienteAdd) {
		for(Cliente cliente: this.clientes){
			if	(cliente.equals(clienteAdd)){
				return; 	// Achei! Sair sem adicionar.
			}
		}
		
		this.clientes.add(clienteAdd); // Adicionar o cliente ao meu array de clients
	}*/

	
	/**
	 * Sincroniza os dados das contas com o database
	 * @return
	 * @throws SQLException
	 */
	public ArrayList<Conta> getContas() throws SQLException {
		// tenta recuperar do banco de dados
		if(contas.isEmpty()){
			this.connection = new ConnectionFactory().getConnection();
			Statement stm = connection.createStatement();

			// seleciona as contas APENAS desta agencia
			String sql = "SELECT conta.* FROM `contas` as conta LEFT JOIN `clientes` as cliente ON(cliente.id = conta.cliente_id) LEFT JOIN `agencias` as agencia ON (agencia.id = conta.agencia_id) WHERE conta.agencia_id = "+this.getId();			
			
			ResultSet result = stm.executeQuery(sql);
	        while(result.next()){
	            // TODO: ler mais informações dos Models associados ao cliente ???
	        	
	        	if( result.getInt("tiposconta_id") == 1 ){
	        		// Suponho que não seja preciso verificar se o cliente está cadastrado na base local
	        		contas.add(new ContaPoupanca(result.getInt("id"), this, this.buscarCliente(result.getInt("cliente_id")), result.getInt("numero"), result.getInt("saldo"), result.getDate("aniversario"), result.getBoolean("ativa")));
	        	}else{
	        		contas.add(new ContaCorrente(result.getInt("id"), this, this.buscarCliente(result.getInt("cliente_id")), result.getInt("numero"), result.getDouble("saldo"), result.getDouble("limite"), result.getBoolean("ativa")));
	        	}
	        }
	        // TODO: fechar a conexão a cada consulta ou deixar persistente ???
	        //connection.close();
		}
		
		return contas;
	}
	
	
	/**
	 * Sincroniza os dados de clientes com o database
	 * @return
	 * @throws SQLException
	 */
	public ArrayList<Cliente> getClientes() throws SQLException {
		// tenta recuperar do banco de dados
		if(clientes.isEmpty()){
			this.connection = new ConnectionFactory().getConnection();
			Statement stm = connection.createStatement();

			// seleciona os clientes APENAS desta agencia
			String sql = "SELECT cliente.* FROM `clientes` as cliente LEFT JOIN `contas` as conta ON(cliente.id = conta.cliente_id) LEFT JOIN `agencias` as agencia ON (agencia.id = conta.agencia_id) WHERE agencia.id = "+this.getId();			
			
			ResultSet result = stm.executeQuery(sql);
	        while(result.next()){
	            // TODO: ler mais informações dos Models associados ao cliente ???

	        	if(result.getInt("tiposcliente_id") == 2){
	        		// Pessoa jurídica
	        		clientes.add( new PessoaJuridica(result.getInt("id"), result.getInt("tiposcliente_id"), result.getString("nome"), result.getString("telefone"), result.getString("email"), result.getString("cnpj"), result.getString("nome_fantasia")));
	        	}else if(result.getInt("tiposcliente_id") == 3){
	        		clientes.add( new PessoaFisica(result.getInt("id"), result.getInt("tiposcliente_id"), result.getString("nome"), result.getString("telefone"), result.getString("email"), result.getString("cpf")));
	        	}
	        }
	        // TODO: fechar a conexão a cada consulta ou deixar persistente ???
	        //connection.close();
		}
		
		return clientes;
	}
	
	public Conta buscarConta(int numero) {
		Conta out = null;
		for(Conta conta: this.contas){
			if( numero == conta.getNumero() ){
				out = conta;
				break;
			}
		}
		return out;
	}
	
	public Cliente buscarCliente(int id) {
		Cliente out = null;
		for(Cliente _cliente: this.clientes){
			if( id == _cliente.getId() ){
				out = _cliente;
				break;
			}
		}
		return out;
	}
	
	public Cliente buscarCliente(String email) {
		Cliente out = null;
		for(Cliente _cliente: this.clientes){
			if( email == _cliente.getEmail() ){
				out = _cliente;
				break;
			}
		}
		return out;
	}
	
	public String ListContasCliente(Cliente clienteList) {
		// Listar todas as contas do cliente
		String retorno = "";
		for(Conta conta: this.contas){
			if	(conta.cliente.equals(clienteList)){
				retorno += conta+"\n";
			}
		}		
        return retorno;
	}
	
	public Banco getBanco(){
		return this.banco;
	}
}