package br.ifrn.tads.poo.banco.app;

public class InvalidInputException extends Exception{
	
	private static final long serialVersionUID = 1L;
	
	public InvalidInputException() {
		super("Entrada de dados inválida");
	}
	public InvalidInputException(String message) {
		super(message);
	}
	public InvalidInputException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
