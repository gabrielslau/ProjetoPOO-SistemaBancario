package br.ifrn.tads.poo.banco.agencia;
import  br.ifrn.tads.poo.banco.cliente.*;
import  java.util.ArrayList;

public class Agencia {

	private int numero;
	private String nome;
	private String endereco;
	private String nomeGerente;
	private ArrayList<Cliente> clientes; // Agencia hasMany Cliente
	private ArrayList<Conta> contas;	 // Agencia hasMany Conta
										// Agencia belongsTo Banco

	public Agencia(int numero, String nome, String endereco, String nomeGerente) {
		this.numero = numero;
		this.nome = nome;
		this.endereco = endereco;
		this.nomeGerente = nomeGerente;
		this.clientes = new ArrayList<Cliente>(); // habilita a adição de clientes
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
	public void adicionarCliente(Cliente cliente) {
		this.clientes.add(cliente);
	}
	public void criarConta(Cliente cliente, int numConta, int limiteConta, double saldoConta, String tipoConta) {
		if(tipoConta.equalsIgnoreCase("corrente"))
			this.contas.add(new ContaCorrente(this, cliente, numConta, 0, limiteConta));
		else if(tipoConta.equalsIgnoreCase("poucanca"))
			this.contas.add(new ContaPoupanca(this, cliente, numConta, saldoConta));
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
	public Cliente buscarCliente(String nome) {
		Cliente out = null;
		for(Cliente cliente: this.clientes){
			if( nome.equalsIgnoreCase(cliente.getNome()) ){
				out = cliente;
				break;
			}
		}
		return out;
	}
}