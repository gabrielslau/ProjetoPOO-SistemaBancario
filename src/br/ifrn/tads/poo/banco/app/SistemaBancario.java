package br.ifrn.tads.poo.banco.app;

import java.util.Scanner;

import com.sun.org.apache.bcel.internal.generic.InstructionConstants.Clinit;

import br.ifrn.tads.poo.banco.*;
import br.ifrn.tads.poo.banco.agencia.Agencia;
import br.ifrn.tads.poo.banco.agencia.Conta;
import br.ifrn.tads.poo.banco.agencia.ContaCorrente;
import br.ifrn.tads.poo.banco.cliente.*;

public class SistemaBancario {

	public static void main(String[] args) {
		
		int comando = -1;
        Scanner sc = new Scanner(System.in);
        
        Banco banco = null;						
        PessoaFisica pessoafisica = null;		
        PessoaJuridica pessoajuridica = null;
        Cliente cliente = null;
        Agencia agencia = null;
        Conta conta = null;
 
        while(comando != 0){

        	//	Criar e limpar as variavies
        	String 	nome, endereco, nomeGerente, telefone, 
        			email, cpf, cnpj, nomeFantasia = "";
        	int 	numero;
        	double 	limite, valor;
        	
        	System.out.println("");
            System.out.println("************* MENU **************");
            System.out.println("*********************************");
            //System.out.println("");
            System.out.println("* INICIALIZAR BANCO");
            System.out.println("10 - Adicionar banco");
            System.out.println("11 - Adicionar agencia");
            System.out.println("12 - Buscar agencia");
            //System.out.println("");
            System.out.println("* PESSOA FISICA");           
            System.out.println("21 - Adicionar cliente PF");          
            System.out.println("22 - Adicionar conta corrente");
            System.out.println("23 - Adicionar conta poupanca");
            //System.out.println("");
            System.out.println("* PESSOA JURIDICA");            
            System.out.println("31 - Adicionar cliente PJ");
            System.out.println("32 - Adicionar conta corrente");
            System.out.println("33 - Adicionar conta poupanca");
            //System.out.println("");
            System.out.println("* OPERAÇÕES BANCARIAS");
            System.out.println("41 - Localizar conta");            
            System.out.println("42 - Cancelar/reativar conta");
            System.out.println("43 - Alterar limite");
            System.out.println("44 - Depositar");
            System.out.println("45 - Sacar");
            System.out.println("46 - Transferir");
            //System.out.println("");
            System.out.println("* CONSULTAS/RELATORIOS");
            System.out.println("51 - Listar contas");           
            System.out.println("52 - Ver saldo");
            System.out.println("53 - Listar clientes");
            System.out.println("54 - Listar contas do cliente");
            System.out.println("");
            System.out.println("0 - Sair");
            //System.out.println(""); 
            System.out.print("Escolha a operacao: ");
            comando = sc.nextInt();
 
            switch(comando){
                
            	case 0:		// SAIR
            			
                    System.out.println("Ate logo!");
                    break;
                    
                case 10:		// Adicionar banco 
                
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
                    
                case 11:		// CRIAR AGENCIA
                	
                	// Mostrar na tela                	
                	System.out.println("---");
                	System.out.println("Adicionar nova agencia ao meu banco: " + banco.getNumero()+" - "+banco.getNome());
                	if (banco.sizeAgencias() > 0){ 
                		System.out.println("Agencias ja cadastradas:");
                		System.out.println(banco.getAgencias());
                	}
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
                    agencia = banco.adicionarAgencia(numero, nome, endereco, nomeGerente);
                    // destruir (caso ja tenha sido instanciado)
                    conta = null;
                    
                    break;
                    
                case 12:		// BUSCAR AGENCIA
                	
                	// Mostrar na tela                	
                	System.out.println("---");
                	System.out.println("Localizar agencia:");
                    System.out.println("---");
                    System.out.println("Informe o numero da agencia: ");
                    numero = sc.nextInt();
                    
                    Agencia buscaragencia = null;
                    buscaragencia = banco.buscarAgencia(numero);
                    
                    // buscar agencia
                    if (buscaragencia != null){
                    	System.out.println("Agencia localizada com sucesso.");
                    	agencia = buscaragencia;
                    	System.out.println(agencia.getNumero()+" - "+agencia.getNome());
                    }else
                    	System.out.println("Agencia informada nao foi localizada.");
                    
                    buscaragencia = null;
                    break;
                                
                case 21:		// CRIAR CLIENTE PF
                	
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
                    
                    // Adicionar pessoa fisica
                    pessoafisica = new PessoaFisica(nome, telefone, email, cpf);                    
                    System.out.println("Operacao realizada com sucesso");                    
                    System.out.println(pessoafisica.toString());
                    
                    break;
                
                case 22:		// ADICIONAR CONTA CORRENTE PARA O CLIENTE PF
                	
					cliente	= pessoafisica;
					
                	// Mostrar na tela                	
                	System.out.println("---");
                	System.out.println("ADICIONAR NOVA CONTA:");
                	System.out.println("BANCO: " + banco.getNumero()+" - "+banco.getNome());
                    System.out.println("AGENCIA : "+agencia.getNumero()+" - "+agencia.getNome());
                    System.out.println("CLIENTE: "+cliente.getNome());
                    System.out.println("---");
                    
                    System.out.println("Número: ");
                    numero = sc.nextInt();
                    System.out.println("Limite: ");
                    limite = sc.nextDouble();
                                        
                    agencia.criarConta(cliente, numero, limite, "corrente");
                    conta = agencia.buscarConta(numero);
                    System.out.println("Conta corrente criada com sucesso");
                    System.out.println(conta);
                    
                    break;
                    
				case 23:		// ADICIONAR CONTA POUPANCA PARA O CLIENTE PF
                	
					cliente	= pessoafisica;
					
                	// Mostrar na tela                	
                	System.out.println("---");
                	System.out.println("BANCO: " + banco.getNumero()+" - "+banco.getNome());
                    System.out.println("AGENCIA : "+agencia.getNumero()+" - "+agencia.getNome());
                    System.out.println("CLIENTE: "+cliente.getNome());
                    System.out.println("---");
                    
                    System.out.println("Número: ");
                    numero = sc.nextInt();                  
                                        
                    agencia.criarConta(cliente, numero, 0, "poupanca");
                    conta = agencia.buscarConta(numero);
                    System.out.println("Conta poupanca criada com sucesso");
                    System.out.println(conta);
                    
                    break;
                    
				case 31:		// CRIAR CLIENTE PJ
                	
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
                    
                    // Adicionar pessoa juridica
                    pessoajuridica = new PessoaJuridica(nome, telefone, email, cnpj, nomeFantasia);                    
                    System.out.println("Operacao realizada com sucesso");                    
                    System.out.println(pessoajuridica.toString());
                                        
                    break;
                
				case 32:		// ADICIONAR CONTA CORRENTE PARA O CLIENTE PJ
                	
					cliente	= pessoajuridica;
					
                	// Mostrar na tela                	
                	System.out.println("---");
                	System.out.println("ADICIONAR NOVA CONTA:");
                	System.out.println("BANCO: " + banco.getNumero()+" - "+banco.getNome());
                    System.out.println("AGENCIA : "+agencia.getNumero()+" - "+agencia.getNome());
                    System.out.println("CLIENTE: "+cliente.getNome());
                    System.out.println("---");
                    
                    System.out.println("Número: ");
                    numero = sc.nextInt();
                    System.out.println("Limite: ");
                    limite = sc.nextDouble();
                                        
                    agencia.criarConta(cliente, numero, limite, "corrente");
                    conta = agencia.buscarConta(numero);
                    System.out.println("Conta corrente criada com sucesso");
                    System.out.println(conta);
                    
                    break;
                    
				case 33:		// ADICIONAR CONTA POUPANCA PARA O CLIENTE PJ
                	
					cliente	= pessoajuridica;
					
                	// Mostrar na tela                	
                	System.out.println("---");
                	System.out.println("ADICIONAR NOVA CONTA:");
                	System.out.println("BANCO: " + banco.getNumero()+" - "+banco.getNome());
                    System.out.println("AGENCIA : "+agencia.getNumero()+" - "+agencia.getNome());
                    System.out.println("CLIENTE: "+cliente.getNome());
                    System.out.println("---");
                    
                    System.out.println("Número: ");
                    numero = sc.nextInt();                  
                                        
                    agencia.criarConta(cliente, numero, 0, "poupanca");
                    conta = agencia.buscarConta(numero);
                    System.out.println("Conta poupanca criada com sucesso");
                    System.out.println(conta);
                    
                    break;
                    
				case 41:		// LOCALIZAR CONTA
                	
                	// Mostrar na tela                	
                	System.out.println("---");
                	System.out.println("Informe o numero da conta:");
                	numero = sc.nextInt();                   
                    
                	Conta buscarconta = null;
                    buscarconta = agencia.buscarConta(numero);
                    
                    // buscar conta
                    if (buscarconta != null){
                    	System.out.println("Conta localizada com sucesso.");
                    	conta = buscarconta;
                    	System.out.println(conta.toString());
                    }else
                    	System.out.println("A conta informada nao foi localizada.");
                    
                    buscarconta = null;
                    break;
                    
				case 42:		// CANCELAR CONTA
                	
					if (conta != null){
						System.out.println("---");
						if (conta.isAtiva()){
							conta.cancelarConta();
							System.out.println("Conta cancelada com sucesso!");	
						}else{
							conta.reativarConta();
							System.out.println("Conta reativada com sucesso!");
						}
												
					}else{
						System.out.println("---");
						System.out.println("Localizar primeiro a conta");
					}
                	
                	break;
                
				case 43:		// MUDAR LIMITE
                	
                	if (conta != null){
                		
                		if (conta instanceof ContaCorrente){
                			
	                		if (conta.isAtiva()){
	                			System.out.println("---");
	                        	System.out.println("Informe o novo limite:");
	                        	limite = sc.nextDouble();                   
	                            
	    						System.out.println("---");
	    						conta.mudarLimiteDeConta(limite);
	    						System.out.println("Limite da conta alterado com sucesso!");	
	                		}else
	                			System.out.println("Operacao nao realizada. A conta nao esta ativa!");
	                		       
                		}else
                			System.out.println("Operacao nao realizada. Sua conta não é do tipo conta corrente!");             
                								
					}else{
						System.out.println("---");
						System.out.println("Localizar primeiro a conta");
					}
                    
                    break;
                    
				case 44:		// DEPOSITAR
                	
                	if (conta != null){
                		
                		if (conta.isAtiva()){
                			System.out.println("---");
                        	System.out.println("Informe o valor do deposito:");
                        	valor = sc.nextDouble();                   
                            
                        	System.out.println("---");
    						conta.depositar(valor);
    						System.out.println("Deposito realizado com sucesso!");	
                		}else{
                			System.out.println("Operacao nao realizada. A conta nao esta ativa!");
                		}
                								
					}else{
						System.out.println("---");
						System.out.println("Localizar primeiro a conta");
					}
                    
                    break;
                
				case 45:		// SACAR
                	
                	if (conta != null){
                		
                		if (conta.isAtiva()){
                			
                			System.out.println("---");
                			System.out.println("Informe o valor do saque:");
                			valor = sc.nextDouble();
                			System.out.println("---");
                			if (conta.sacar(valor))
                				System.out.println("Saque realizado com sucesso!");
                			else
                				System.out.println("Saldo insuficiente!");
                			
                		}else{
                			System.out.println("Operacao nao realizada. A conta nao esta ativa!");
                		}
                		
					}else{
						System.out.println("---");
						System.out.println("Localizar primeiro a conta");
					}
                    
                    break;
                
				case 46:		// TRANSFERIR
                	
                	if (conta != null){
                		
                		if (conta.isAtiva()){
                		
                			System.out.println("---");
                        	System.out.println("Informe o numero da conta destino:");
                        	numero = sc.nextInt();                   
                            
                        	Conta contadestino = null;
                        	contadestino = agencia.buscarConta(numero);
                            
                            if (contadestino != null){
                            	
                            	if (contadestino.isAtiva()){
                            		System.out.println("---");
                                	System.out.println("Informe o valor da transferencia:");
                                	valor = sc.nextDouble();
                                	System.out.println("---");
            						if (conta.transferirValor(contadestino, valor))
            							System.out.println("Tranferencia realizada com sucesso!");
            						else
            							System.out.println("Saldo insuficiente!");	
                            	}else{
                        			System.out.println("Operacao nao realizada. A conta de destino nao esta ativa!");
                        		}
                            	
    	
                            }else{
        						System.out.println("---");
        						System.out.println("A conta de destino nao foi localizada.");
                            }
                            
                            contadestino = null;
                            
                		}else{
                			System.out.println("Operacao nao realizada. A conta de origem nao esta ativa!");
                		}
                								
					}else{
						System.out.println("---");
						System.out.println("Localizar primeiro a conta de origem.");
					}
                    
                    break;
                
				case 51:		// LISTAR AS CONTAS CRIADAS
                	
                	// Mostrar na tela                	
                	System.out.println("---");
                	System.out.println("Relacao de minhas contas:");
                    System.out.println(agencia.getContas());                   
                    
                    break;
                    
				case 52:		// VER SALDO
                	
                	// Mostrar na tela       
					
					if (conta != null){
						System.out.println("---");
						System.out.println("Detalhes da conta:");
						System.out.println("---");
						System.out.println("Titular: "+conta.getCliente());
						System.out.println("Saldo atual: "+conta.getSaldo());
						System.out.println("Limite: "+ conta.getLimite());
						System.out.println("Situação: "+ conta.verSituacaoConta());						
					}else{
						System.out.println("---");
						System.out.println("Localizar primeiro a conta");
					}
                	
                	break;
                
				case 53:		// LISTAR CLIENTES
					
                	// Mostrar na tela
					System.out.println("---");
                	System.out.println("Relacao de meus clientes:");
                    System.out.println(agencia.getClientes());                   
                    
                    break;
                    
				case 54:		// LISTAR CONTAS DO CLIENTE
                	
                	// Mostrar na tela
					System.out.println("---");
                	System.out.println("Relacao das contas do cliente: "+ cliente.getNome());
                    System.out.println(agencia.ListContasCliente(cliente));                   
                    
                    break;
                   
				default:	// Opcao invalida
                    System.out.println("Nao entendi o comando.");
                    
                    break;
                    
            }
            
        }

	}

}