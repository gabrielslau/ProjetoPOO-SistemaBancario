package br.ifrn.tads.poo.banco.agencia;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import  java.util.Date;

import br.ifrn.tads.poo.banco.app.ConnectionFactory;
import br.ifrn.tads.poo.banco.cliente.Cliente;

public class ContaPoupanca extends Conta {
	private Date aniversario;
	private final int tiposconta_id = 1;  // ID da categoria no DB
	
	/**
	 * Cria uma conta com informações vindas do banco de dados
	 * 
	 * @param id
	 * @param agencia Objeto da Agencia associada
	 * @param cliente Objeto do Cliente associado
	 * @param numero
	 * @param saldo
	 * @param aniversario
	 * @param ativa
	 */
	public ContaPoupanca(int id, Agencia agencia, Cliente cliente, int numero, double saldo, Date aniversario, boolean ativa) {
		super(agencia, cliente);
		this.id = id;
		this.numero = numero;
		this.saldo = saldo;
		this.aniversario = aniversario;
		this.ativa = ativa;
	}
	public ContaPoupanca(Agencia agencia, Cliente cliente, double saldo) throws SQLException {
		super(agencia, cliente, saldo);
		this.aniversario = new Date(); // o aniversário é criado no momento em que se cria uma nova conta
		
		this.insert();
	}
	public ContaPoupanca(Agencia agencia, Cliente cliente) throws SQLException {
		super(agencia, cliente);
		this.aniversario = new Date(); // o aniversário é criado no momento em que se cria uma nova conta
		
		this.insert();
	}
	
	private void insert() throws SQLException{
		this.connection = new ConnectionFactory().getConnection();
		String sql = "INSERT INTO contas (tiposconta_id, agencia_id, cliente_id, numero, saldo, aniversario, ativa) VALUES (?,?,?,?,?,?,?)";
		PreparedStatement stmt = connection.prepareStatement(sql);
		
		stmt.setInt(1, this.tiposconta_id);
		stmt.setInt(2, this.agencia.getId());
		stmt.setInt(3, this.cliente.getId());
		stmt.setInt(4, this.numero);
		stmt.setDouble(5, this.saldo);
		stmt.setString(6, this.aniversario.toString());
		stmt.setBoolean(7, this.ativa);
		stmt.execute();
	}
	
	public Date getAniversario() {
		return aniversario;
	}
	
	public void setAniversario(Date aniversario) {
		this.aniversario = aniversario;
	}
	
	public void calcularRendimentoMes() {
		// Calculo de rendimento de poupança
		this.saldo = (this.saldo * this.TAXA) + this.TR;
	}
	
}