package br.ifrn.tads.poo.banco.cliente;
import java.util.ArrayList;

import  br.ifrn.tads.poo.banco.agencia.*;

public class Cliente {

	private String nome;
	private String telefone;
	private String email;
	private ArrayList<Agencia> agencias; // Cliente hasMany Agencia
	private ArrayList<Conta> contas;	 // Cliente hasMany Conta

	public Cliente(String nome, String telefone, String email) {
		this.nome = nome;
		this.telefone = telefone;
		this.email = email;
	}
	public String getTelefone() {
		return telefone;
	}
	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getNome() {
		return nome;
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
	public Agencia buscarAgencia(int numero) {
		Agencia out = null;
		for(Agencia agencia: this.agencias){
			if( nome.equalsIgnoreCase(agencia.getNome()) ){
				out = agencia;
				break;
			}
		}
		return out;
	}
}