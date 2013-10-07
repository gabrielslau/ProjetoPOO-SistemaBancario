package br.ifrn.tads.poo.banco.app;

public class DataNotFoundException extends Exception{
	
	private static final long serialVersionUID = 1L;
	
	public DataNotFoundException() {
		super("O registro não foi encontrado");
	}
	public DataNotFoundException(String message) {
		super(message);
	}
	public DataNotFoundException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
