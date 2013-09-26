package br.ifrn.tads.poo.banco.agencia;

public class SaldoInsuficienteException extends Exception {
	private static final long serialVersionUID = 1L;
	
	SaldoInsuficienteException(String message) {
		super(message);
	}
	SaldoInsuficienteException() {
		super("Saldo Insuficiente");
	}
}
