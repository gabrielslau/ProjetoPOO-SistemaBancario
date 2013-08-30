package br.ifrn.tads.poo.banco.agencia;
import  br.ifrn.tads.poo.banco.cliente.Cliente;

public class ContaCorrente extends Conta {

	public double limite;

	public ContaCorrente(Agencia agencia, Cliente cliente, int numero, double saldo, boolean ativa, double limite) {
		super(agencia, cliente, numero, saldo, ativa);
		this.limite = limite;
	}
	public ContaCorrente(Agencia agencia, Cliente cliente, int numero, double saldo, double limite) {
		super(agencia, cliente, numero, saldo);
		this.limite = limite;
	}
	public void mudarLimiteDeConta(double limite){
		this.limite = limite;
	}
}