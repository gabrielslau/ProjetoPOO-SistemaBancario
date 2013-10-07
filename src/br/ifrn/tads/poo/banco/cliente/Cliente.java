package br.ifrn.tads.poo.banco.cliente;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import br.ifrn.tads.poo.banco.agencia.Agencia;
import br.ifrn.tads.poo.banco.agencia.Conta;
import br.ifrn.tads.poo.banco.agencia.ContaCorrente;
import br.ifrn.tads.poo.banco.agencia.ContaPoupanca;
import br.ifrn.tads.poo.banco.app.ConnectionFactory;


public class Cliente implements ICliente {
	/* 
	 * Classe abstratra.
	 * So pode ser herdada por outras. Nao  
	 * Nao pode ser instanciada diretamente 
	*/
	private int id;
	private int tiposcliente_id;
	private String nome;
	private String telefone;
	private String email;
	private ArrayList<Agencia> agencias; // Cliente hasMany Agencia
	private ArrayList<Conta> contas;	 // Cliente hasMany Conta
	
	private Connection connection;
	
	public Cliente(String nome, String telefone, String email) {
		this.nome = nome;
		this.telefone = telefone;
		this.email = email;
		
		this.connection = new ConnectionFactory().getConnection();
	}
	
	// Usa-se para atualizar o objeto
	public Cliente(int id, int tiposcliente_id, String nome, String telefone, String email){
		this.id = id;
		this.tiposcliente_id = tiposcliente_id;
		this.nome = nome;
		this.telefone = telefone;
		this.email = email;
		
		this.connection = new ConnectionFactory().getConnection();
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	public String getTelefone() {
		return telefone;
	}
	
	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getNome() {
		return nome;
	}
	
	public String toString(){
		String Tipo = (this instanceof PessoaFisica) ? "Pessoa Fisica" : "Pessoa Juridica";		
		return this.id + ", " + this.nome + ", " + Tipo;
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
	
	public Agencia buscarAgencia(int numero) {
		Agencia out = null;
		for(Agencia agencia: this.agencias){
			if( nome.equalsIgnoreCase(agencia.getNome()) ){
				out = agencia;
				break;
			}
		}
		return out;
	}
	
	public Cliente buscarCliente(Cliente[] clientes, String nome) {
		Cliente aux = null;

		for (int i = 0; i < clientes.length; i++) {
			if (nome.equalsIgnoreCase(clientes[i].getNome())) {
				aux = clientes[i];				
			}
		}
		
		return aux;
	}

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
			String sql = "SELECT conta.* FROM `contas` as conta LEFT JOIN `clientes` as cliente ON(cliente.id = conta.cliente_id) LEFT JOIN `agencias` as agencia ON (agencia.id = conta.agencia_id) WHERE conta.cliente_id = "+this.getId();			
			
			ResultSet result = stm.executeQuery(sql);
	        while(result.next()){
	            // TODO: ler mais informações dos Models associados ao cliente ???
	        	
	        	if( result.getInt("tiposconta_id") == 1 ){
	        		// Suponho que não seja preciso verificar se o cliente está cadastrado na base local
	        		contas.add(new ContaPoupanca(result.getInt("id"), this.buscarAgencia(result.getInt("agencia_id")), this, result.getInt("numero"), result.getInt("saldo"), result.getDate("aniversario"), result.getBoolean("ativa")));
	        	}else{
	        		contas.add(new ContaCorrente(result.getInt("id"), this.buscarAgencia(result.getInt("agencia_id")), this, result.getInt("numero"), result.getDouble("saldo"), result.getDouble("limite"), result.getBoolean("ativa")));
	        	}
	        }
	        // TODO: fechar a conexão a cada consulta ou deixar persistente ???
	        //connection.close();
		}
		
		return contas;
	}
}