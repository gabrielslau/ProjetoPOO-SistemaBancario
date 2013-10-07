package br.ifrn.tads.poo.banco.app;

public class LimboException extends Exception{
	
	private static final long serialVersionUID = 1L;
	
	public LimboException() {
		super("Onde é que eu estou??");
	}

	public LimboException(String message) {
		super(message);
	}
	
	public LimboException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
