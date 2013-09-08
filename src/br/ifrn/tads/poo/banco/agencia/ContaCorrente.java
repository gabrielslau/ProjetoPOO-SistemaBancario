package br.ifrn.tads.poo.banco.agencia;

public class ContaCorrente extends Conta {

	public double limite;

	public ContaCorrente(int numero, double limite) {
		super(numero);
		this.limite = limite;
	}

	public void mudarLimiteDeConta(double limite){
		this.limite = limite;
	}
	
	public double getLimite() {
		return limite;
	}

}