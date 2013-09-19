package br.ifrn.tads.poo.banco.agencia;

import br.ifrn.tads.poo.banco.cliente.Cliente;

public class ContaCorrente extends Conta {

	public double limite;

	public ContaCorrente(Cliente cliente, int numero, double limite) {
		super(cliente, numero);
		this.limite = limite;
	}

	public void mudarLimiteDeConta(double limite){
		this.limite = limite;
	}
	
	public double getLimite() {
		return limite;
	}

}