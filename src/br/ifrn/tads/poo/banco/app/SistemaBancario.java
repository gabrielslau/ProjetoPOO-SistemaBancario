package br.ifrn.tads.poo.banco.app;

import java.util.Scanner;
import java.util.ArrayList;

import br.ifrn.tads.poo.banco.Banco;
import br.ifrn.tads.poo.banco.agencia.Agencia;
import br.ifrn.tads.poo.banco.agencia.Conta;
import br.ifrn.tads.poo.banco.agencia.ContaCorrente;
import br.ifrn.tads.poo.banco.agencia.SaldoInsuficienteException;
import br.ifrn.tads.poo.banco.cliente.*;



import java.sql.*;

public class SistemaBancario {

    private static ArrayList<Banco> bancos; // lista dos bancos cadastrados no sistema
    private static Banco banco = null; //(???)
    private static PessoaFisica pessoafisica = null;
    private static PessoaJuridica pessoajuridica = null;
    private static Cliente cliente = null;
    private static Agencia agencia = null;
    private static Conta conta = null;
    private static Scanner sc = new Scanner(System.in);

    // variaveis usadas nos metodos estaticos
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
        cliente = pessoafisica;
        
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
        cliente = pessoafisica;
        
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
                        //  System.out.println("Tranferencia realizada com sucesso!");
                        //else
                        //  System.out.println("Saldo insuficiente!");  
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
        cliente = pessoajuridica;
        
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
        cliente = pessoajuridica;
        
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
    
    public static boolean login(int banco_id, int agencia_id, String user_email){
    	boolean _isAuth = false;
    	for(Banco _banco: bancos){
    		if(banco_id == _banco.getNumero()){
    			Agencia _agencia =_banco.buscarAgencia(agencia_id); 
				if(_agencia != null){
					//Cliente _cliente = 
					if(_agencia.buscarCliente(user_email) != null){
						_isAuth = true;
					}
				}
			}
		}
    	
    	return _isAuth;
    }
    
    public static void run(){
    	int comando = -1;
        // level = 0 : Escolha do tipo do usuário
        // level = 1 : Administrador do Banco
        // level = 2 : Cliente
        int level = 0;
        boolean isAuth = false;
        
        while(comando != 0){
            try{
                
                if(level == 0){
                    // Menu de escolha de usuário 
                    System.out.println(
                        "\n"+
                        "Escolha o tipo de usuário\n\n" +
                        "1 - Administrador do sistema\n" +
                        "2 - Cliente\n"
                    );
                }else if(level == 1){
                    System.out.println(
                        "\n---\n" +
                        "USER: Admin\n" +
                        "---\n\n"
                    );
                    
                    System.out.println(
                        "1 - Cadastrar banco\n" +
                        "2 - Cadastrar agencia\n"+
                        "3 - Buscar agencia\n"+
                        "4 - Adicionar cliente PF\n"+
                        "5 - Adicionar cliente PJ\n"+
                        "6 - PF Adicionar conta corrente\n"+
                        "7 - PF Adicionar conta poupanca\n"+
                        "8 - Listar contas\n"+
                        "9 - Localizar conta\n"+
                        "16 - PJ Adicionar conta corrente\n"+
                        "17 - PJ Adicionar conta poupanca\n"+
                        
                        "18 - Listar clientes\n"+
                        "19 - Listar contas do cliente\n"+
                        "0 - Sair\n"+

                        "-1 - Voltar\n"
                    );
                }else if(level == 2){
                    // TODO: Fazer o "login" do cliente
                    if(isAuth){
                        // Menu de operações
                        System.out.println(
                            "10 - Ver saldo\n"+
                            "11 - Cancelar/reativar conta\n"+
                            "12 - Alterar limite\n"+
                            "13 - Depositar\n"+
                            "14 - Sacar\n"+
                            "15 - Transferir\n"+
                            "0 - Sair\n"+

                            "-1 - Voltar\n"
                        );
                    }else{
                        // Menu de login
                        int banco_id, agencia_id;
                        String user_email;

                        System.out.println("\n Entre com o id do Banco: ");
                        banco_id = sc.nextInt();

                        System.out.println("\n Entre com o id da sua Agencia: ");
                        agencia_id = sc.nextInt();

                        System.out.println("\n Entre com o seu identificador: ");
                        user_email = sc.next();
                        
                        if(!login(banco_id, agencia_id, user_email))
                        	throw new Exception("Os identificadores estão errados ou o usuário não está cadastrado");
                    }
                }

                if(isAuth){
                    System.out.println("\nEscolha a operacao: ");
                }
                comando = sc.nextInt();
                
                if(level == 0){
                    switch(comando){
                        case 0: // Sair
                            System.out.println("Ate logo!");
                            break;
                        case 1: // Admin
                            level = 1;
                            break;
                        case 2: // Client
                            level = 2;
                            break;
                        default:
                            throw new IllegalArgumentException("Comando inválido");
                    }
                }else if(level == 1){
                    switch(comando){
                        case -1:    // Voltar
                            level = 0;
                            break;
                        case 0:     // Sair
                            System.out.println("Ate logo!");
                            break;
                        case 1:     // INICIA BANCO
                            cadastrarBanco();
                            break;
                        case 2:     // CRIAR AGENCIA
                            adicionarAgencia();
                            break;
                        case 3:     // BUSCAR AGENCIA
                            buscarAgencia();
                            break;
                        case 4:     // CRIAR CLIENTE PF
                            adicionarClientePF();
                            break;
                        case 5:     // CRIAR CLIENTE PJ
                            adicionarClientePJ();
                            break;
                        case 6:     // ADICIONAR CONTA CORRENTE PARA O CLIENTE PF
                            adicionarContaCorrentePF();
                            break;
                        case 7:     // ADICIONAR CONTA POUPANCA PARA O CLIENTE PF
                            adicionarContaPoupancaPF();
                            break;
                        case 8:     // RELACIONAR AS CONTAS CRIADAS
                            listarContas();
                            break;
                        case 9:     // LOCALIZAR CONTA
                            localizarConta();
                            break;
                        case 16:        // ADICIONAR CONTA CORRENTE PARA O CLIENTE PJ
                            adicionarContaCorrentePJ();
                            break;
                        case 17:        // ADICIONAR CONTA POUPANCA PARA O CLIENTE PJ
                            adicionarContaPoupancaPJ();
                            break;
                        case 18:        // RELACIONAR AS CONTAS CRIADAS
                            listarClientes();
                            break;
                        case 19:        // LISTAR CONTAS DO CLIENTE
                            listarContas();
                            break;
                        default:
                            throw new IllegalArgumentException("Comando inválido");
                    }
                }else if(level == 2){
                    if(isAuth){
                        switch(comando){
                            case -1:        // Voltar
                                level = 0;
                                break;
                            case 0:         // Sair
                                System.out.println("Ate logo!");
                                break;
                            case 10:        // VER SALDO
                                verSaldo();
                                break;
                            case 11:        // CANCELAR CONTA
                                toggleConta();
                                break;
                            case 12:        // MUDAR LIMITE
                                alterarLimite();
                                break;
                            case 13:        // DEPOSITAR
                                depositar();
                                break;
                            case 14:        // SACAR
                                sacar();
                                break;
                            case 15:        // TRANSFERIR
                                transferir();
                                break;
                            default:
                                throw new IllegalArgumentException("Comando inválido");
                        }
                    }else{
                        // Verifica se o usuário existe
                        
                    }
                }
     
                
            }
            //catch(SaldoInsuficienteException e){
            //  System.out.println(e.getMessage());
            //}
            catch(IllegalArgumentException e){
                System.out.println(e.getMessage()); 
            }
            catch(Exception e){
                System.out.println(e.getMessage());
            }
            
        }
    }
    
    public static void main(String[] args)  throws SQLException {
    	try{
    		Class.forName("com.mysql.jdbc.Driver");
    		Connection connection = null;
    		connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/cake_db","root", "admin");
    		
    		if (connection != null) {
    			run();
        	} else {
        		throw new SQLException("Nao foi possivel estabelecer uma conexao com o banco de dados");
        	}
    	}catch (ClassNotFoundException e) {
    		System.out.println("O driver MySQL JDBC nao foi encontrado");
    		//e.printStackTrace();
    		return;
    	}catch (SQLException e) {
    		System.out.println(e.getMessage());
    		//e.printStackTrace();
    		return;
    	}
    }
}