package br.ifrn.tads.poo.banco.agencia;

public interface IConta {

	public int getNumero();
	
	public void setNumero(int numero);
	
	public double getSaldo();
	
	public boolean isAtiva();
	
	public void sacar(double valor) throws SaldoInsuficienteException;
	
	public void depositar(double valor);
	
	public void cancelarConta();
	
	public void reativarConta();
	
	public String verSituacaoConta();
	
	public void mudarLimiteDeConta(double limite);
	
	public void transferirValor(Conta contadestino, double valor) throws SaldoInsuficienteException;
	
}