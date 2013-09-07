package br.ifrn.tads.poo.banco.agencia;

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
	
	public double verSaldo() {
		return this.saldo;
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
	
	public boolean transferirValor(int numConta, int numAgencia, double valor){
		if(this.saldo - valor < 0) return false; // dinheiro insuficiente
		//Conta conta_destino = this.agencia.buscarConta(numConta);
		//if(conta_destino == null) return false; // conta inexistente
		
		// precisa do numero da Agencia para quê ???
		//conta_destino.depositar(valor);
		return true;
	}
	
}