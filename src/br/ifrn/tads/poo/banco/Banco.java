package br.ifrn.tads.poo.banco;

import br.ifrn.tads.poo.banco.agencia.Agencia;

public class Banco {

  private String nome;
  private int numero;

  public Agencia buscarAgencia(int numero) {
	  return null;
  }

  public void adicionarAgencia(Agencia agencia) {
  }
  
  public Banco(int numero, String nome) {
	  this.nome = nome;
	  this.numero = numero;
  }

  public String getNome() {
	  return nome;
  }
  
  public int getNumero() {
	  return numero;
  }
  
}