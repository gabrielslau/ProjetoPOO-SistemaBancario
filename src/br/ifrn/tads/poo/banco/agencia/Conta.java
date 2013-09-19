package br.ifrn.tads.poo.banco.agencia;

import br.ifrn.tads.poo.banco.cliente.Cliente;

public class Conta implements IConta {

	protected final double TR = 0; 		// Taxa Referencial
	protected final double TAXA = 0.50;
	
	protected Cliente cliente; 
	protected int numero;
	protected double saldo;
	protected boolean ativa;
	
	public Conta(Cliente cliente, int numero) {
		this.cliente = cliente;
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
	
	public String getCliente() {
		return this.cliente.getNome();
	}
	
	public boolean sacar(double valor) {
		if(this.saldo - valor < 0){
			if (this instanceof ContaCorrente){
				// conta corrente - Checar o limite
				if(this.saldo + this.getLimite() - valor < 0)
					return false; // dinheiro insuficiente				
			}else{			
				// conta poupanca 
				return false; // dinheiro insuficiente
			}
		}
		
		this.saldo -= valor;
		return true;
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
	
	public boolean transferirValor(Conta contadestino, double valor){
		if(this.saldo - valor < 0){
			if (this instanceof ContaCorrente){
				// conta corrente - Checar o limite
				if(this.saldo + this.getLimite() - valor < 0)
					return false; // dinheiro insuficiente
			}else{
				// conta poupanca 
				return false; // dinheiro insuficiente
			}
		}
		
		this.sacar(valor);				// Sacar da conta atual
		contadestino.depositar(valor);	// Depositar na conta destino
		
		return true;
	}
	
}