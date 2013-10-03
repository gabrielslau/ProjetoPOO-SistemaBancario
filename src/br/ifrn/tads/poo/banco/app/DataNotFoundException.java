package br.ifrn.tads.poo.banco.app;

public class DataNotFoundException extends Exception{
	private static final long serialVersionUID = 1L;
	
	DataNotFoundException(String message) {
		super(message);
	}
	DataNotFoundException() {
		super("O registro não foi encontrado");
	}
}
