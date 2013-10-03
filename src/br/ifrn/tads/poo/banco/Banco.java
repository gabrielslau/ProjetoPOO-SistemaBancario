package br.ifrn.tads.poo.banco;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import br.ifrn.tads.poo.banco.agencia.Agencia;
import br.ifrn.tads.poo.banco.app.ConnectionFactory;
import br.ifrn.tads.poo.banco.cliente.Cliente;
import br.ifrn.tads.poo.banco.cliente.PessoaFisica;
import br.ifrn.tads.poo.banco.cliente.PessoaJuridica;

public class Banco {

	private int id;
	private int numero;	
	private String 	nome;
	
	private Connection connection;
  
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	private ArrayList<Agencia> agencias; // lista das agencias cadastradas no banco

	// Constructor
	public Banco(int id, int numero, String nome) {
		this.id 	= id;
		this.numero = numero;
		this.nome 	= nome;
		this.agencias = new ArrayList<Agencia>();
	}
	
	public Banco(int numero, String nome) throws SQLException {
		this.numero = numero;
		this.nome 	= nome;
		this.agencias = new ArrayList<Agencia>();
		
		this.insert(numero, nome);
	}
	
	public Banco(String nome) throws SQLException{
		this.numero = new Double( Math.random() * 100000 ).intValue(); // numero automatico
		this.nome 	= nome;
		this.agencias = new ArrayList<Agencia>();
		
		this.insert(numero, nome);
	}
	
	private void insert(int numero, String nome) throws SQLException{
		// como os campos são obrigatorios, aproveita para cadastrar automaticamente no BD
		this.connection = new ConnectionFactory().getConnection();
		String sql = "insert into bancos " +"(numero, nome) " +"values (?,?)";
		PreparedStatement stmt = connection.prepareStatement(sql);
		
		stmt.setLong(1, numero);
		stmt.setString(2, nome.substring(0, 45));
		stmt.execute();
	}
	
	public String getNome() {
		return nome;
	}
	
	public int getNumero() {
		return numero;
	}
	
	public String toString(){
		return String.valueOf(this.id) + "; " + String.valueOf(this.numero) + "; " + String.valueOf(this.nome);
		//return "ID: " + String.valueOf(this.id) + "; Numero: " + String.valueOf(this.numero) + "; Nome: " + String.valueOf(this.nome);
	}
	
	/*
	 * buscarAgencia
	 * 
	 * @param int numero : numero da agencia a buscar
	 * @return Agencia
	 */
	public Agencia buscarAgencia(int numero) {
		Agencia out = null;
		for(Agencia agencia: this.agencias){
			if( numero == agencia.getNumero() ){
				out = agencia;
				break;
			}
		}
		return out;
	}
	
	public Agencia cadastrarAgencia(String nome, String endereco, String nomeGerente) throws SQLException {
		Agencia novaagencia = new Agencia(this, nome, endereco, nomeGerente);
		this.agencias.add(novaagencia);
		return novaagencia;
	}
	
	public ArrayList<Agencia> getAgencias() throws SQLException {
		// tenta recuperar do banco de dados
		if (agencias.isEmpty()) {
			this.connection = new ConnectionFactory().getConnection();
			Statement stm = connection.createStatement();

			// seleciona os clientes APENAS desta agencia
			String sql = "SELECT * FROM `agencias` as agencia WHERE banco_id = " + this.getId();

			ResultSet result = stm.executeQuery(sql);
			while (result.next()) {
				agencias.add(new Agencia(result.getInt("id"), this, result.getInt("numero"), result.getString("nome"), result.getString("endereco"), result.getString("nome_gerente")));
			}
			// TODO: fechar a conexão a cada consulta ou deixar persistente ???
			// connection.close();
		}
        return agencias;
	}
}