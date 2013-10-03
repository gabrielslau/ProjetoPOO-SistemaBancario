package br.ifrn.tads.poo.banco.cliente;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import br.ifrn.tads.poo.banco.app.ConnectionFactory;

public class PessoaJuridica extends Cliente {
	
	private Connection connection;
	
	private int tiposcliente_id = 2;
	public String cnpj;
	public String nome_fantasia;

	public PessoaJuridica(String nome, String telefone, String email, String cnpj, String nome_fantasia) throws SQLException {
		super(nome, telefone, email);
		this.cnpj = cnpj;
		this.nome_fantasia = nome_fantasia;
		
		this.insert(nome, telefone, email, cnpj, nome_fantasia);
	}
	
	public PessoaJuridica(int id, int tiposcliente_id, String nome, String telefone, String email, String cnpj, String nome_fantasia){
		super(id, tiposcliente_id, nome, telefone, email);
		this.cnpj = cnpj;
		this.nome_fantasia = nome_fantasia;
	}
	
	private void insert(String nome, String telefone, String email, String cnpj, String nome_fantasia) throws SQLException{
		// como os campos são obrigatorios, aproveita para cadastrar automaticamente no BD
		this.connection = new ConnectionFactory().getConnection();

	    ResultSet generatedKeys = null;

		String sql = "INSERT INTO `clientes` (tiposcliente_id, nome, telefone, email, cnpj, nome_fantasia) VALUES (?,?,?,?,?,?)";
		PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

		stmt.setInt(1, this.tiposcliente_id);
		stmt.setString(2, nome);
		stmt.setString(3, telefone);
		stmt.setString(4, email);
		stmt.setString(5, cnpj);
		stmt.setString(6, nome_fantasia);
		//stmt.execute();

		// Precisa mesmo checar ???
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
	
	public String getCnpj() {
		return this.cnpj;
	}
	
	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}
	
	public String getNomeFantasia() {
		return this.nome_fantasia;
	}
	
	public void setNomeFantasia(String nome_fantasia) {
		this.nome_fantasia = nome_fantasia;
	}
	
}