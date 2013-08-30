package br.ifrn.tads.poo.banco;
import  br.ifrn.tads.poo.banco.agencia.Agencia;
import  java.util.ArrayList;

public class Banco {

	private String nome;
	private int numero;
	private ArrayList<Agencia> agencias; // lista das agencias cadastradas no banco
  
	public Banco(int numero, String nome) {
		this.numero = numero;
		this.nome = nome;
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
	public void adicionarAgencia(Agencia agencia) {
		this.agencias.add(agencia);
	}
}