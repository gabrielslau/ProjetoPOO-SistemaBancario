package br.ifrn.tads.poo.banco.agencia;

import  br.ifrn.tads.poo.banco.cliente.*;

import  java.util.ArrayList;

public class Agencia {

	private int numero;
	private String nome;
	private String endereco;
	private String nomeGerente;
	private ArrayList<Conta> contas;	 	// Agencia hasMany Conta
	private ArrayList<Cliente> clientes;	// Agencia hasMany Conta

	public Agencia(int numero, String nome, String endereco, String nomeGerente) {
		this.numero = numero;
		this.nome = nome;
		this.endereco = endereco;
		this.nomeGerente = nomeGerente;
		this.contas = new ArrayList<Conta>(); // habilita a adição de contas
		this.clientes = new ArrayList<Cliente>(); // habilita a adição de clientes
	}
	
	public String toString(){
		return numero+" - "+nome;
	}
	
	public int getNumero() {
		return numero;
	}
	
	public void setNumero(int numero) {
		this.numero = numero;
	}
	
	public String getNome() {
		return nome;
	}
	
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public String getEndereco() {
		return endereco;
	}
	
	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}
	
	public String getNomeGerente() {
		return nomeGerente;
	}
	
	public void setNomeGerente(String nomeGerente) {
		this.nomeGerente = nomeGerente;
	}
	
	public void criarConta(Cliente cliente, int numConta, double limiteConta, String tipoConta) {
		Conta novaconta = null;
		if(tipoConta.equalsIgnoreCase("corrente"))
			novaconta = new ContaCorrente(numConta, limiteConta);			
		else if(tipoConta.equalsIgnoreCase("poupanca"))
			novaconta = new ContaPoupanca(numConta);
		
		this.contas.add(novaconta);	// Adicionar conta criada no meu array de contas
		this.clientes.add(cliente); // Adicionar o cliente ao meu array de clients
	}
	
	public String getContas() {
		String retorno = "";
		for(Conta conta: this.contas){
			retorno += conta+"\n";	
		}		
        return retorno;
	}
	
	public Conta buscarConta(int numero) {
		Conta out = null;
		for(Conta conta: this.contas){
			if( numero == conta.getNumero() ){
				out = conta;
				break;
			}
		}
		return out;
	}
	
}