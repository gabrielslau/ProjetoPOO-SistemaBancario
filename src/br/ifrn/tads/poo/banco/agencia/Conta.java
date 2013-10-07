package br.ifrn.tads.poo.banco.agencia;

import java.sql.Connection;

import br.ifrn.tads.poo.banco.cliente.Cliente;

public class Conta implements IConta {
	protected Connection connection;
	
	protected final double TR = 0; 		// Taxa Referencial
	protected final double TAXA = 0.50;
	
	protected Cliente cliente; // Conta belongsTo Cliente
	protected Agencia agencia; // Conta belongsTo Agencia
	protected int id, numero;
	protected double saldo;
	protected boolean ativa;
	
	public Conta(Agencia agencia, Cliente cliente) {
		this.agencia = agencia;
		this.cliente = cliente;
		this.numero = new Double( Math.random() * 100000 ).intValue();
		this.saldo  = 0;
		this.ativa  = true;
	}
	
	public Conta(Agencia agencia, Cliente cliente, double saldo) {
		this.agencia = agencia;
		this.cliente = cliente;
		this.numero = new Double( Math.random() * 100000 ).intValue();
		this.saldo  = saldo; // saldo inicial
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
	
	public Cliente getCliente() {
		return this.cliente;
	}
	
	public String getClienteName() {
		return this.cliente.getNome();
	}
	
	public void sacar(double valor) throws SaldoInsuficienteException{
		if(this.saldo - valor < 0){
			if (this instanceof ContaCorrente){
				// conta corrente - Checar o limite
				if(this.saldo + this.getLimite() - valor < 0)
					throw new SaldoInsuficienteException();
			}else{			
				// conta poupanca 
				throw new SaldoInsuficienteException();
			}
		}
		
		this.saldo -= valor;
	}
	
	public void depositar(double valor) {
		if(valor < 0){
			throw new IllegalArgumentException("Não é permitido inserir valores negativos");
		}
		else{
			this.saldo += valor;
		}
	}
	
	public void cancelarConta() {
		this.ativa = false;
	}
	
	public void reativarConta() {
		this.ativa = true;
	}
	
	public String toString(){
		String Tipo = (this instanceof ContaCorrente) ? "Corrente" : "Poupança";
		String Retorno = "";	
		Retorno 	= "Numero: " + String.valueOf(this.numero);
		Retorno 	+= "; Titular: " + this.cliente.getNome();
		Retorno 	+= "; Tipo: " + Tipo + "; Saldo: " + this.saldo;
		Retorno 	+= "; Situacao: " + (this.ativa ? "Ativa" : "Desativada");
		if 	(this instanceof ContaCorrente){
			Retorno 	+= "; Limite: " + this.getLimite();
		}
		return Retorno;
	}
	
	public String verSituacaoConta(){
		return this.ativa ? "Ativa" : "Desativada";
	}
	
	public void mudarLimiteDeConta(double limite){} // é sobrescrita nas classes herdeiras
	
	public double getLimite(){	
		return 0;
	}
	
	public void transferirValor(Conta contadestino, double valor) throws SaldoInsuficienteException{
		if(this.saldo - valor < 0){
			if (this instanceof ContaCorrente){
				// conta corrente - Checar o limite
				if(this.saldo + this.getLimite() - valor < 0)
					throw new SaldoInsuficienteException();
			}else{
				// conta poupanca 
				throw new SaldoInsuficienteException();
			}
		}
		
		this.sacar(valor);				// Sacar da conta atual
		contadestino.depositar(valor);	// Depositar na conta destino
	}
	
}