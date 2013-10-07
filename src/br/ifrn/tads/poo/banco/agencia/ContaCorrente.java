package br.ifrn.tads.poo.banco.agencia;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import br.ifrn.tads.poo.banco.app.ConnectionFactory;
import br.ifrn.tads.poo.banco.cliente.Cliente;

public class ContaCorrente extends Conta {

	public double limite;
	private final int tiposconta_id = 2; // ID da categoria no DB
	
	/**
	 * Cria uma conta com informações vindas do banco de dados
	 * 
	 * @param id
	 * @param agencia Objeto da Agencia associada
	 * @param cliente Objeto do Cliente associado
	 * @param numero
	 * @param saldo
	 * @param limite
	 * @param ativa
	 */
	public ContaCorrente(int id, Agencia agencia, Cliente cliente, int numero, double saldo, double limite, boolean ativa) {
		super(agencia, cliente);
		this.id = id;
		this.numero = numero;
		this.saldo = saldo;
		this.limite = limite;
		this.ativa = ativa;
	}
	
	public ContaCorrente(Agencia agencia, Cliente cliente, double limite) throws SQLException {
		super(agencia, cliente);
		this.limite = limite;
		
		this.insert();
	}
	
	private void insert() throws SQLException{
		this.connection = new ConnectionFactory().getConnection();
		String sql = "INSERT INTO contas (tiposconta_id, agencia_id, cliente_id, numero, saldo, limite, ativa) VALUES (?,?,?,?,?,?,?,?)";
		PreparedStatement stmt = connection.prepareStatement(sql);
		
		stmt.setInt(1, this.tiposconta_id);
		stmt.setInt(2, this.agencia.getId());
		stmt.setInt(3, this.cliente.getId());
		stmt.setInt(4, this.numero);
		stmt.setDouble(5, this.saldo);
		stmt.setDouble(6, this.limite);
		stmt.setBoolean(7, this.ativa);
		stmt.execute();
	}
	// TODO: usar update sql
	public void mudarLimiteDeConta(double limite){
		this.limite = limite;
	}
	
	public double getLimite() {
		return limite;
	}

}