package br.ifrn.tads.poo.banco.cliente;

public class PessoaFisica extends Cliente {

	private String cpf;
	
	public PessoaFisica(String nome, String telefone, String email, String cpf) {
		super(nome, telefone, email);
		this.cpf = cpf;
	}
	
	public String getCpf(){
		return this.cpf;
	}
	
	public String toString(){
		return cpf+" - "+super.getNome();
	}	
		
}