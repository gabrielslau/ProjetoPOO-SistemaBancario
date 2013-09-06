package br.ifrn.tads.poo.banco.agencia;

import  java.util.Date;

import br.ifrn.tads.poo.banco.cliente.Cliente;

public class ContaPoupanca extends Conta {

	private Date aniversario;

	public ContaPoupanca(Agencia agencia, Cliente cliente, int numero, double saldo, boolean ativa) {
		super(agencia, cliente, numero, saldo, ativa);
		this.aniversario = new Date(); // o aniversário é criado no momento em que se cria uma nova conta
	}
	
	public ContaPoupanca(Agencia agencia, Cliente cliente, int numero, double saldo) {
		super(agencia, cliente, numero, saldo);
		this.aniversario = new Date();
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