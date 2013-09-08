package br.ifrn.tads.poo.banco.app;

import java.util.Scanner;

import br.ifrn.tads.poo.banco.*;
import br.ifrn.tads.poo.banco.agencia.Agencia;
import br.ifrn.tads.poo.banco.agencia.Conta;
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
            System.out.println("1 - Iniciar banco");
            System.out.println("2 - Adicionar agencia");
            System.out.println("3 - Buscar agencia");
            System.out.println("4 - Adicionar cliente PF");
            System.out.println("5 - Adicionar cliente PJ");
            System.out.println("6 - Adicionar conta corrente");
            System.out.println("7 - Adicionar conta poupanca");
            System.out.println("8 - Listar contas");
            System.out.println("9 - Localizar conta");
            System.out.println("10 - Ver saldo");
            System.out.println("11 - Cancelar/reativar conta");
            System.out.println("12 - Alterar limite");
            System.out.println("13 - Depositar");
            System.out.println("14 - Sacar");
            System.out.println("15 - Transferir");
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
                    agencia = banco.adicionarAgencia(numero, nome, endereco, nomeGerente);
                    
                    break;
                    
                case 3:		// BUSCAR AGENCIA
                	
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
                    
                    // Adicionar pessoa fisica
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
                    
                    // Adicionar pessoa juridica
                    pessoajuridica = new PessoaJuridica(nome, telefone, email, cnpj, nomeFantasia);                    
                    System.out.println("Operacao realizada com sucesso");                    
                    System.out.println(pessoajuridica.toString());
                                        
                    break;
                
				case 6:		// ADICIONAR CONTA CORRENTE PARA O CLIENTE PF
                	
					cliente	= pessoafisica;
					
                	// Mostrar na tela                	
                	System.out.println("---");
                	System.out.println("Adicionar conta.");
                	System.out.print("Cliente na memoria: ");
                    System.out.println(cliente.getNome());
                    System.out.print("Agencia memoria: ");
                    System.out.println(agencia.getNumero()+" - "+agencia.getNome());
                    System.out.println("---");
                    
                    System.out.println("Número: ");
                    numero = sc.nextInt();
                    System.out.println("Limite: ");
                    limite = sc.nextDouble();
                                        
                    agencia.criarConta(cliente, numero, limite, "corrente");
                    conta = agencia.buscarConta(numero);
                    System.out.println("Conta corrente criada com sucesso");
                    
                    break;
                    
				case 7:		// ADICIONAR CONTA POUPANCA PARA O CLIENTE PF
                	
					cliente	= pessoafisica;
					
                	// Mostrar na tela                	
                	System.out.println("---");
                	System.out.println("Adicionar conta.");
                	System.out.print("Cliente na memoria: ");
                    System.out.println(cliente.getNome());
                    System.out.print("Agencia memoria: ");
                    System.out.println(agencia.getNumero()+" - "+agencia.getNome());
                    System.out.println("---");
                    
                    System.out.println("Número: ");
                    numero = sc.nextInt();                  
                                        
                    agencia.criarConta(cliente, numero, 0, "poupanca");
                    conta = agencia.buscarConta(numero);
                    System.out.println("Conta poupanca criada com sucesso");
                    
                    break;
                    
				case 8:		// RELACIONAR AS CONTAS CRIADAS
                	
                	// Mostrar na tela                	
                	System.out.println("---");
                	System.out.println("Relacao de minhas contas:");
                    System.out.println(agencia.getContas());                   
                    
                    break;
                    
				case 9:		// LOCALIZAR CONTA
                	
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
                    
                    break;
                    
				case 10:		// VER SALDO
                	
                	// Mostrar na tela       
					
					if (conta != null){
						System.out.println("---");
						System.out.println("Detalhes da conta:");
						System.out.println("---");						
						System.out.println("Saldo atual: "+conta.getSaldo());
						System.out.println("Limite: "+ conta.getLimite());
						System.out.println("Situação: "+ conta.verSituacaoConta());						
					}else{
						System.out.println("---");
						System.out.println("Localizar primeiro a conta");
					}
                	
                	break;
                
				case 11:		// CANCELAR CONTA
                	
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
                
				case 12:		// MUDAR LIMITE
                	
                	if (conta != null){
                		
                		System.out.println("---");
                    	System.out.println("Informe o novo limite:");
                    	limite = sc.nextDouble();                   
                        
						System.out.println("---");
						conta.mudarLimiteDeConta(limite);
						System.out.println("Limite da conta alterado com sucesso!");						
					}else{
						System.out.println("---");
						System.out.println("Localizar primeiro a conta");
					}
                    
                    break;
                    
				case 13:		// DEPOSITAR
                	
                	if (conta != null){
                		System.out.println("---");
                    	System.out.println("Informe o valor do deposito:");
                    	valor = sc.nextDouble();                   
                        
                    	System.out.println("---");
						conta.depositar(valor);
						System.out.println("Deposito realizado com sucesso!");						
					}else{
						System.out.println("---");
						System.out.println("Localizar primeiro a conta");
					}
                    
                    break;
                
				case 14:		// SACAR
                	
                	if (conta != null){
                		System.out.println("---");
                    	System.out.println("Informe o valor do saque:");
                    	valor = sc.nextDouble();
                    	System.out.println("---");
						if (conta.sacar(valor))
							System.out.println("Saque realizado com sucesso!");
						else
							System.out.println("Saldo insuficiente!");
						
					}else{
						System.out.println("---");
						System.out.println("Localizar primeiro a conta");
					}
                    
                    break;
                
				case 15:		// TRANSFERIR
                	
                	if (conta != null){
                		
                		System.out.println("---");
                    	System.out.println("Informe o numero da conta destino:");
                    	numero = sc.nextInt();                   
                        
                    	Conta contadestino = null;
                    	contadestino = agencia.buscarConta(numero);
                        
                        if (contadestino != null){
                        	System.out.println("---");
                        	System.out.println("Informe o valor da transferencia:");
                        	valor = sc.nextDouble();
                        	System.out.println("---");
    						if (conta.transferirValor(contadestino, valor))
    							System.out.println("Tranferencia realizada com sucesso!");
    						else
    							System.out.println("Saldo insuficiente!");
	
                        }else{
    						System.out.println("---");
    						System.out.println("A conta de destino nao foi localizada.");
                        }
                								
					}else{
						System.out.println("---");
						System.out.println("Localizar primeiro a conta de origem.");
					}
                    
                    break;
                
				default:	// Opcao invalida
                    System.out.println("Nao entendi o comando.");
                    
                    break;
                    
            }
            
        }

	}

}