package br.ifrn.tads.poo.banco.agencia;

import  java.util.Date;

import br.ifrn.tads.poo.banco.cliente.Cliente;

public class ContaPoupanca extends Conta {

	private Date aniversario;

	public ContaPoupanca(Cliente cliente, int numero) {
		super(cliente, numero);
		this.aniversario = new Date(); 
		// o aniversário é criado no momento em que se cria uma nova conta
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