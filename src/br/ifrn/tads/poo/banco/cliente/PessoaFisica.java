package br.ifrn.tads.poo.banco.cliente;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import br.ifrn.tads.poo.banco.app.ConnectionFactory;

public class PessoaFisica extends Cliente {
	
	private Connection connection;
	
	private int tiposcliente_id = 3;
	private String cpf;
	
	public PessoaFisica(String nome, String telefone, String email, String cpf) throws SQLException {
		super(nome, telefone, email);
		this.cpf = cpf;
		
		this.insert(nome, telefone, email, cpf);
	}
	
	// Usado para atualiza a lista de objetos cliente
	public PessoaFisica(int id, int tiposcliente_id, String nome, String telefone, String email, String cpf){
		super(id, tiposcliente_id, nome, telefone, email);
		this.cpf = cpf;
	}

	private void insert(String nome, String telefone, String email, String cpf) throws SQLException{
		// como os campos são obrigatorios, aproveita para cadastrar automaticamente no BD
		this.connection = new ConnectionFactory().getConnection();

	    ResultSet generatedKeys = null;

		String sql = "INSERT INTO `clientes` (tiposcliente_id, nome, telefone, email, cpf) VALUES (?,?,?,?,?)";
		PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

		stmt.setInt(1, this.tiposcliente_id);
		stmt.setString(2, nome);
		stmt.setString(3, telefone);
		stmt.setString(4, email);
		stmt.setString(5, cpf);
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

	public String getCpf(){
		return this.cpf;
	}		
}