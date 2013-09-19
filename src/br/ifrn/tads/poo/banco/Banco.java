package br.ifrn.tads.poo.banco;

import  br.ifrn.tads.poo.banco.agencia.Agencia;
import  java.util.ArrayList;

public class Banco {

	private int numero;	
	private String 	nome;
  
	private ArrayList<Agencia> agencias; // lista das agencias cadastradas no banco

	// Constructor
	public Banco(int numero, String nome) {
		this.numero = numero;
		this.nome 	= nome;
		this.agencias = new ArrayList<Agencia>();
	}
	
	public String getNome() {
		return nome;
	}
	
	public int getNumero() {
		return numero;
	}
	
	public Agencia buscarAgencia(int numero) {
		Agencia out = null;
		for(Agencia agencia: this.agencias){
			if( numero == agencia.getNumero() ){
				out = agencia;
				break;
			}
		}
		return out;
	}
	
	public Agencia adicionarAgencia(int numero, String nome, String endereco, String nomeGerente) {
		Agencia novaagencia = new Agencia(numero, nome, endereco, nomeGerente);
		this.agencias.add(novaagencia);
		return novaagencia;
	}
	
	public String getAgencias() {
		String retorno = "";
		for(Agencia agencia: this.agencias){
			retorno += agencia.getNumero()+" - "+agencia.getNome()+"\n";
		}		
        return retorno;
	}
	
	public int sizeAgencias() {
		return this.agencias.size();
	}
	
}