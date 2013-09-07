package br.ifrn.tads.poo.banco.agencia;

public interface IConta {

	public int getNumero();
	
	public void setNumero(int numero);
	
	public double getSaldo();
	
	public boolean isAtiva();
	
	public boolean sacar(double valor);
	
	public void depositar(double valor);
	
	public double verSaldo();
	
	public void cancelarConta();
	
	public void reativarConta();
	
	public String verSituacaoConta();
	
	public void mudarLimiteDeConta(double limite);
	
	public boolean transferirValor(int numConta, int numAgencia, double valor);
	
}