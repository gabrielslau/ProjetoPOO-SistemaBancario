package br.ifrn.tads.poo.banco.cliente;

public class PessoaFisica extends Cliente {

	private String cpf;

	/**
	 * @param nome
	 * @param telefone
	 * @param email
	 * @param cpf
	 */
	public PessoaFisica(String nome, String telefone, String email, String cpf) {
		super(nome, telefone, email);
		this.cpf = cpf;
	}
	public String getCpf() {
		return this.cpf;
	}
}