package br.ifrn.tads.poo.banco.app;

import java.util.Scanner;

import com.sun.org.apache.bcel.internal.generic.InstructionConstants.Clinit;

import br.ifrn.tads.poo.banco.*;
import br.ifrn.tads.poo.banco.agencia.Agencia;
import br.ifrn.tads.poo.banco.agencia.Conta;
import br.ifrn.tads.poo.banco.agencia.ContaCorrente;
import br.ifrn.tads.poo.banco.agencia.SaldoInsuficienteException;
import br.ifrn.tads.poo.banco.cliente.*;

public class SistemaBancario {
	//throw new IllegalArgumentException("Saldo insuficiente");
	
	private static Banco banco = null;
	private static PessoaFisica pessoafisica = null;
	private static PessoaJuridica pessoajuridica = null;
	private static Cliente cliente = null;
	private static Agencia agencia = null;
	private static Conta conta = null;
	private static Scanner sc = new Scanner(System.in);
	
	// variaveis 
	private static String nome, endereco, nomeGerente, telefone, email, cpf, cnpj, nomeFantasia = "";
	private static int numero;
	private static double limite, valor;
	
	public static void cadastrarBanco(){
		int numero;
		String nome;
		
		// Mostrar na tela
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
	}
	
	public static void adicionarAgencia(){
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
        // destruir (caso ja tenha sido instanciado)
        conta = null;
	}
	
	public static void buscarAgencia(){
		int numero;
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
	}
	
	public static void adicionarClientePF(){
		//String cpf, nome, email, telefone;
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
	}
	
	public static void adicionarClientePJ(){
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
	}
	
	public static void adicionarContaCorrentePF(){
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
	}
	
	public static void adicionarContaPoupancaPF(){
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
	}
	
	public static void listarContas(){
		// Mostrar na tela                	
    	System.out.println("---");
    	System.out.println("Relacao de minhas contas:");
        System.out.println(agencia.getContas());
	}
	
	public static void localizarConta(){
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
	}
	
	public static void verSaldo(){
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
	}
	
	// Ativa/Desativa uma conta
	public static void toggleConta(){
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
	}
	
	public static void alterarLimite(){
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
	}
	
	public static void depositar(){
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
	}
	
	public static void sacar(){
		if (conta != null){
    		
    		if (conta.isAtiva()){
    			
    			System.out.println("---");
    			System.out.println("Informe o valor do saque:");
    			valor = sc.nextDouble();
    			System.out.println("---");
    			
    			try{
    				conta.sacar(valor);
    				System.out.println("Saque realizado com sucesso!");
    			}
    			catch(SaldoInsuficienteException e){
            		System.out.println(e.getMessage());
            	}
    		}else{
    			System.out.println("Operacao nao realizada. A conta nao esta ativa!");
    		}
    		
		}else{
			System.out.println("---");
			System.out.println("Localizar primeiro a conta");
		}
	}
	
	public static void transferir(){
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
						try{
							conta.transferirValor(contadestino, valor);
                    		System.out.println("Tranferencia realizada com sucesso!");
						}
						catch(SaldoInsuficienteException e){
			        		System.out.println(e.getMessage());
			        	}
                    	//if (conta.transferirValor(contadestino, valor))
						//	System.out.println("Tranferencia realizada com sucesso!");
						//else
						//	System.out.println("Saldo insuficiente!");	
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
	}
	
	public static void adicionarContaCorrentePJ(){
		cliente	= pessoajuridica;
		
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
	}
	
	public static void adicionarContaPoupancaPJ(){
		cliente	= pessoajuridica;
		
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
	}
	
	public static void listarClientes(){
		// Mostrar na tela
		System.out.println("---");
    	System.out.println("Relacao de meus clientes:");
        System.out.println(agencia.getClientes());
	}
	
	public static void listarContasDoCliente(){
		// Mostrar na tela
		System.out.println("---");
    	System.out.println("Relacao das contas do cliente: "+ cliente.getNome());
        System.out.println(agencia.ListContasCliente(cliente));
	}
	
	
	public static void main(String[] args) {
		
		int comando = -1;
        while(comando != 0){
        	try{
        			
    	            System.out.println("1 - Cadastrar banco");
    	            System.out.println("2 - Cadastrar agencia");
    	            System.out.println("3 - Buscar agencia");
    	            System.out.println("4 - Adicionar cliente PF");
    	            System.out.println("5 - Adicionar cliente PJ");
    	            System.out.println("6 - PF Adicionar conta corrente");
    	            System.out.println("7 - PF Adicionar conta poupanca");
    	            System.out.println("8 - Listar contas");
    	            System.out.println("9 - Localizar conta");
    	            System.out.println("10 - Ver saldo");
    	            System.out.println("11 - Cancelar/reativar conta");
    	            System.out.println("12 - Alterar limite");
    	            System.out.println("13 - Depositar");
    	            System.out.println("14 - Sacar");
    	            System.out.println("15 - Transferir");
    	            System.out.println("16 - PJ Adicionar conta corrente");
    	            System.out.println("17 - PJ Adicionar conta poupanca");
    	            
    	            System.out.println("18 - Listar clientes");
    	            System.out.println("19 - Listar contas do cliente");
    	            System.out.println("0 - Sair");
        		
	            System.out.println("");
	            System.out.println("Escolha a operacao: ");
	            comando = sc.nextInt();
	 
	            switch(comando){

	            	case 0:		// SAIR
	                    System.out.println("Ate logo!");
	                    break;
	                    
	                case 1:		// INICIA BANCO

	                	cadastrarBanco();
	                    break;
	                case 2:		// CRIAR AGENCIA
	                	
	                	adicionarAgencia();
	                    break;
	                    
	                case 3:		// BUSCAR AGENCIA
	                	
	                	buscarAgencia();
	                    break;
	                                
	                case 4:		// CRIAR CLIENTE PF
	                	
	                	adicionarClientePF();
	                    break;
	                
	                case 5:		// CRIAR CLIENTE PJ
	                	
	                	adicionarClientePJ();
	                    break;
	                
					case 6:		// ADICIONAR CONTA CORRENTE PARA O CLIENTE PF
	                	
						adicionarContaCorrentePF();
	                    
	                    break;
	                    
					case 7:		// ADICIONAR CONTA POUPANCA PARA O CLIENTE PF
	                	
						adicionarContaPoupancaPF();
	                    break;
	                    
					case 8:		// RELACIONAR AS CONTAS CRIADAS

	                	listarContas();
	                    break;
	                    
					case 9:		// LOCALIZAR CONTA
	                	
	                	localizarConta();
	                    break;
	                    
					case 10:		// VER SALDO
	                	
	                	verSaldo();
	                	break;
	                
					case 11:		// CANCELAR CONTA

						toggleConta();
	                	break;
	                
					case 12:		// MUDAR LIMITE
	                	
	                	alterarLimite();
	                    break;
	                    
					case 13:		// DEPOSITAR
	                	
	                	depositar();
	                    break;
	                
					case 14:		// SACAR
	                	
	                	sacar();
	                    break;
	                
					case 15:		// TRANSFERIR
	                	
						transferir();
	                    break;
	                
					case 16:		// ADICIONAR CONTA CORRENTE PARA O CLIENTE PJ
	                	
						adicionarContaCorrentePJ();
	                    break;
	                    
					case 17:		// ADICIONAR CONTA POUPANCA PARA O CLIENTE PJ
	                	
						adicionarContaPoupancaPJ();
	                    
	                    break;
	                    
					case 18:		// RELACIONAR AS CONTAS CRIADAS
	                	
	                	listarClientes();
	                    break;

					case 19:		// LISTAR CONTAS DO CLIENTE

	                	listarContas();
	                    break;
	                   
					default:	// Opcao invalida
	                    System.out.println("Nao entendi o comando.");
	                    break;
	            }
        	}
        	//catch(SaldoInsuficienteException e){
        	//	System.out.println(e.getMessage());
        	//}
        	catch(Exception e){
        		System.out.println(e.getMessage());
        	}
            
        }

	}

}