package br.ifrn.tads.poo.banco.app;

public class UserNotFoundException extends Exception {
	private static final long serialVersionUID = 1L;
	
	UserNotFoundException(String message) {
		super(message);
	}
	UserNotFoundException() {
		super("Usuário não encontrado");
	}
}
