package br.ifrn.tads.poo.banco.agencia;

import  br.ifrn.tads.poo.banco.cliente.*;

public class Conta implements IConta {

	protected final double TR = 0; // Taxa Referencial
	protected final double TAXA = 0.50;

	protected int numero;
	protected double saldo;
	protected boolean ativa;
	protected Cliente cliente; // Conta belongsTo Cliente
	protected Agencia agencia; // Conta belongsTo Agencia

	public Conta(Agencia agencia, Cliente cliente, int numero, double saldo, boolean ativa) {
		this.agencia = agencia;
		this.cliente = cliente;
		this.numero = numero;
		this.saldo  = saldo;
		this.ativa  = ativa;
	}
	
	public Conta(Agencia agencia, Cliente cliente, int numero, double saldo) {
		this.agencia = agencia;
		this.cliente = cliente;
		this.numero = numero;
		this.saldo  = saldo;
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
	
	public String verSituacaoConta(){
		return this.ativa ? "Ativa" : "Desativada";
	}
	
	public void mudarLimiteDeConta(double limite){} // é sobrescrita nas classes herdeiras
	
	public Cliente verInformacoesCliente(){
		return this.cliente;
	}
	
	public boolean transferirValor(int numConta, int numAgencia, double valor){
		if(this.saldo - valor < 0) return false; // dinheiro insuficiente
		Conta conta_destino = this.agencia.buscarConta(numConta);
		if(conta_destino == null) return false; // conta inexistente
		
		// precisa do numero da Agencia para quê ???
		conta_destino.depositar(valor);
		return true;
	}
	
}