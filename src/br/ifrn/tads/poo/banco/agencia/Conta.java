package br.ifrn.tads.poo.banco.agencia;

import br.ifrn.tads.poo.banco.cliente.Cliente;

public class Conta implements IConta {

	protected final double TR = 0; // Taxa Referencial
	protected final double TAXA = 0.50;

	protected int numero;
	protected double saldo;
	protected boolean ativa;
	
	public Conta(int numero) {
		this.numero = numero;
		this.saldo  = 0;
		this.ativa  = true;
	}
	
	public int getNumero() {
		return numero;
	}
	
	public void setNumero(int numero) {
		this.numero = numero;
	}
	
	public double getSaldo() {
		return saldo;
	}
	
	public boolean isAtiva() {
		return ativa;
	}
	
	public boolean sacar(double valor) {
		if(this.saldo - valor < 0)
			return false;
		else{
			this.saldo -= valor;
			return true;
		}
	}
	
	public void depositar(double valor) {
		this.saldo += valor;
	}
	
	public void cancelarConta() {
		this.ativa = false;
	}
	
	public void reativarConta() {
		this.ativa = true;
	}
	
	public String toString(){
		return "Numero: " + String.valueOf(this.numero) + "; Situacao: " + (this.ativa ? "Ativa" : "Desativada");
	}
	
	public String verSituacaoConta(){
		return this.ativa ? "Ativa" : "Desativada";
	}
	
	public void mudarLimiteDeConta(double limite){} // é sobrescrita nas classes herdeiras
	
	public double getLimite(){	
		return 0;
	}
	
	public boolean transferirValor(Conta contadestino, double valor){
		if(this.saldo - valor < 0) return false; // dinheiro insuficiente
		
		this.sacar(valor);				// Sacar da conta atual
		contadestino.depositar(valor);	// Depositar na conta destino
		
		return true;
	}
	
}