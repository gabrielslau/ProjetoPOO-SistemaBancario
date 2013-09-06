package br.ifrn.tads.poo.banco.cliente;

import br.ifrn.tads.poo.banco.agencia.*;

public interface ICliente {
	
	public String getTelefone();
	
	public void setTelefone(String telefone);
	
	public String getEmail();
	
	public void setEmail(String email);
	
	public String getNome();
	
	public Conta buscarConta(int numero);
	
	public Agencia buscarAgencia(int numero);
	
	public Cliente buscarCliente(Cliente[] clientes, String nome);	
	
}