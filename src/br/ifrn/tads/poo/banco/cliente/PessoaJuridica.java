package br.ifrn.tads.poo.banco.cliente;

public class PessoaJuridica extends Cliente {

	public String cnpj;
	public String nomeFantasia;

	public PessoaJuridica(String nome, String telefone, String email, String cnpj, String nomeFantasia) {
		super(nome, telefone, email);
		this.cnpj = cnpj;
		this.nomeFantasia = nomeFantasia;
	}
	
	public String getCnpj() {
		return this.cnpj;
	}
	
	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}
	
	public String getNomeFantasia() {
		return this.nomeFantasia;
	}
	
	public void setNomeFantasia(String nomeFantasia) {
		this.nomeFantasia = nomeFantasia;
	}
	
	public String toString(){
		return cnpj+" - "+super.getNome();
	}

}