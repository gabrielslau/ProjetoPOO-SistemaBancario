package br.ifrn.tads.poo.banco.app;

import java.util.Scanner;
import br.ifrn.tads.poo.banco.*;
import br.ifrn.tads.poo.banco.cliente.*;

public class SistemaBancario {

	public static void main(String[] args) {
		
		int comando = -1;
        Scanner sc = new Scanner(System.in);
        
        Banco banco = null;
        PessoaFisica pessoafisica = null;
        PessoaJuridica pessoajuridica = null;
 
        while(comando != 0){

        	//	Criar e limpar as variavies
        	String 	nome, endereco, nomeGerente, telefone, 
        			email, cpf, cnpj, nomeFantasia = "";
        	int 	numero;
        	
            System.out.println("");
            System.out.println("1 - Iniciar banco");
            System.out.println("2 - Adicionar agencia");
            System.out.println("3 - Buscar agencia");
            System.out.println("4 - Adicionar cliente PF");
            System.out.println("5 - Adicionar cliente PJ");
            System.out.println("6 - Adicionar conta");
            System.out.println("0 - Sair");
            System.out.println("");
 
            System.out.println("Escolha a operacao: ");
            comando = sc.nextInt();
 
            switch(comando){
                
            	case 0:		// SAIR
            			
                    System.out.println("Ate logo!");
                    break;
                    
                case 1:		// INICIA BANCO 
                
                	// 	Mostrar na tela
                	System.out.println("---");
                    System.out.println("Inicializar...");
                    System.out.println("---");
                    System.out.println("Número do banco: ");
                    numero = sc.nextInt();
                    System.out.println("Nome do banco: ");
                    sc = new Scanner(System.in);
                    nome = sc.nextLine();
                    
                    // Instanciar a classe banco
                    banco = new Banco(numero, nome);
                    
                    break;
                    
                case 2:		// CRIAR AGENCIA
                	
                	// Mostrar na tela                	
                	System.out.println("---");
                	System.out.println("Adicionar nova agencia ao meu banco:");
                    System.out.println(banco.getNumero()+" - "+banco.getNome());
                    System.out.println("---");                    
                    System.out.println("Relacao de minhas agencias:");
                    System.out.println(banco.getAgencias());
                    System.out.println("---");
                    System.out.println("Número da agencia: ");
                    numero = sc.nextInt();
                    System.out.println("Nome da agencia: ");
                    sc = new Scanner(System.in);
                    nome = sc.nextLine();
                    System.out.println("Endereco: ");
                    sc = new Scanner(System.in);
                    endereco = sc.nextLine();
                    System.out.println("Nome do gerente: ");
                    sc = new Scanner(System.in);
                    nomeGerente = sc.nextLine();
                    
                    // Adicionar agencia ao banco
                    banco.adicionarAgencia(numero, nome, endereco, nomeGerente);
                    		
                    break;
                    
                case 3:		// BUSCAR AGENCIA
                	
                	// Mostrar na tela                	
                	System.out.println("---");
                	System.out.println("Localizar agencia:");
                    System.out.println("---");
                    System.out.println("Informe o numero da agencia: ");
                    numero = sc.nextInt();
                    
                    // buscar agencia
                    if (banco.buscarAgencia(numero) != null){
                    	System.out.println("Agencia localizada com sucesso.");
                    	System.out.println(banco.buscarAgencia(numero).toString());
                    }else
                    	System.out.println("Agencia informada nao foi localizada.");
                    		
                    break;
                                
                case 4:		// CRIAR CLIENTE PF
                	
                	// Mostrar na tela                	
                	System.out.println("---");
                	System.out.println("Adicionar nova pessoa fisica:");
                    System.out.println("---");
                    
                    System.out.println("CPF: ");
                    cpf = sc.next();
                    System.out.println("Nome do cliente: ");
                    sc = new Scanner(System.in);
                    nome = sc.nextLine();
                    System.out.println("Email: ");
                    email = sc.next();
                    System.out.println("Telefone: ");
                    telefone = sc.next();
                    
                    // Adicionar agencia ao banco
                    pessoafisica = new PessoaFisica(nome, telefone, email, cpf);                    
                    System.out.println("Operacao realizada com sucesso");                    
                    System.out.println(pessoafisica.toString());
                                        
                    break;
                
                case 5:		// CRIAR CLIENTE PJ
                	
                	// Mostrar na tela                	
                	System.out.println("---");
                	System.out.println("Adicionar nova pessoa juridica:");
                    System.out.println("---");
                    
                    System.out.println("CNPJ: ");
                    cnpj = sc.next();
                    System.out.println("Razão social: ");
                    sc = new Scanner(System.in);
                    nome = sc.nextLine();
                    System.out.println("Nome Fantasia: ");
                    sc = new Scanner(System.in);
                    nomeFantasia = sc.nextLine();
                    System.out.println("Email: ");
                    email = sc.next();
                    System.out.println("Telefone: ");
                    telefone = sc.next();
                    
                    // Adicionar agencia ao banco
                    pessoajuridica = new PessoaJuridica(nome, telefone, email, cnpj, nomeFantasia);                    
                    System.out.println("Operacao realizada com sucesso");                    
                    System.out.println(pessoajuridica.toString());
                                        
                    break;
                
				case 6:		// ADICIONAR CONTA PARA O CLIENTE
                	
                	// Mostrar na tela                	
                	System.out.println("---");
                	System.out.println("Adicionar conta. \nCliente na memoria");
                    System.out.println(pessoafisica.getCpf()+" - "+pessoafisica.getNome());
                    System.out.println("---");
                    
                    break;
                    
                
				default:	// Opcao invalida
                    System.out.println("Nao entendi o comando.");
                    break;
            }
            
        }

	}

}