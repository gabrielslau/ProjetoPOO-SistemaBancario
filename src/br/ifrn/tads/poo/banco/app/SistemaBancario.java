package br.ifrn.tads.poo.banco.app;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Scanner;

import br.ifrn.tads.poo.banco.Banco;
import br.ifrn.tads.poo.banco.agencia.Agencia;
import br.ifrn.tads.poo.banco.agencia.Conta;
import br.ifrn.tads.poo.banco.agencia.ContaCorrente;
import br.ifrn.tads.poo.banco.agencia.SaldoInsuficienteException;
import br.ifrn.tads.poo.banco.app.tools.Utils;
import br.ifrn.tads.poo.banco.cliente.Cliente;
import br.ifrn.tads.poo.banco.cliente.PessoaFisica;
import br.ifrn.tads.poo.banco.cliente.PessoaJuridica;



public class SistemaBancario {
    private static Connection connection = null;
    private static Statement stm = null;
    
    // Database local
    private static ArrayList<Banco> bancos = null; // lista dos bancos cadastrados no sistema
    private static Banco ultimobanco = null; // registra o ultimo banco cadastrado ("memória")
    
    private static ArrayList<Agencia> agencias = null;
    private static Agencia ultimaagencia = null;
    
    private static ArrayList<Cliente> clientes = null;
    private static Cliente ultimocliente = null;
    
    private static ArrayList<Conta> contas = null;
    private static Conta ultimaconta = null;
    
    // Outros dados
    private static PessoaFisica pessoafisica = null;
    private static PessoaJuridica pessoajuridica = null;
    private static Cliente cliente = null;
    
    private static Conta conta = null;
    private static Scanner input = new Scanner(System.in);

    // variaveis usadas nos metodos estaticos (GAMBIARRA???)
    private static String nome, endereco, nomeGerente, telefone, email, cpf, cnpj, nome_fantasia = "";
    private static int numero;
    private static double limite, valor;
    
    // TODO: corrigir retornos nulos nos blocos Try-Catch
    
    /*
     * Bancos
     */

    /*
     * Atualiza os dados do banco com os dados locais
     */
    private static void syncBanco() throws SQLException{
        ResultSet result = stm.executeQuery("SELECT * FROM bancos");
        while(result.next()){
            ultimobanco = new Banco(result.getInt("id"), result.getInt("numero"), result.getString("nome"));
            bancos.add(ultimobanco);
        }
    }
    private static Banco getBanco(int id) throws SQLException{
        if(bancos == null || bancos.isEmpty()){
            syncBanco();
        }
        
        if(bancos != null && !bancos.isEmpty()){
            for(Banco _b: bancos){
                if(_b.getId() == id) return _b;
            }
        }

        return null;
    }
    // Tenta achar um banco no sistema e adiciona na memória
    public static void buscarBanco() throws SQLException, InputMismatchException{
    	// TODO: aqui deve usar exception para tratar o retorno null ???
        int id;
        // Mostrar na tela                  
        System.out.println("---");
        System.out.println("Localizar banco:");
        System.out.println("---");
        System.out.print("Informe o ID do banco: ");
        try{
        	id = input.nextInt();
        }catch(InputMismatchException e){
        	input.next(); // gambiarra para eliminar uma linha fantasma
        	System.out.println("Entrada de dados inválida");
        	return;
        }
        
        Banco _banco = getBanco(id);
        if (_banco == null){
        	System.out.println("Este banco não existe");
            //throw new DataNotFoundException("Este banco não existe");
        }else{
        	System.out.println("---\nBanco localizado com sucesso (ID, numero, nome)\n---\n");
        	System.out.println(_banco);
        }
    }
    public static void listarBancos() throws SQLException{
        // TODO: cachear a ultima consulta e dar um jeito de verificar se e necessario realizar a consulta no banco
        String out = "";
        
        // Atualiza as informações dos bancos na primeira execução
        if(bancos == null || bancos.isEmpty()){
            syncBanco();
        }
        
        if(bancos != null || !bancos.isEmpty()){
            for(Banco _banco: bancos){
                if(_banco != null) out += _banco+"\n";
            }
        }
        
        if(out == "") {
            throw new RuntimeException("Nenhum banco foi cadastrado");
        }
        
        System.out.println("---\nBancos Cadastrados (ID, numero, nome)\n---\n");
        System.out.println(out);
    }
    public static void cadastrarBanco() throws SQLException, InputMismatchException{
        //int numero;
        String nome;
        try{
            //System.out.print("\nNúmero do banco: ");
            //numero = input.nextInt();
            System.out.print("\nNome do banco: ");
            input = new Scanner(System.in);
            nome = input.next();

            ultimobanco = new Banco(nome);
            bancos.add(ultimobanco);
            System.out.println("O Banco \""+ nome +"\" foi cadastrado com sucesso.");
        }catch(InputMismatchException e){
            // TODO: incluir opção de poder continuar executando o programa, mesmo em caso de erro
        	input.next(); // gambiarra para eliminar uma linha fantasma
        	System.out.println("Entrada de dados inválida");
            //throw new InputMismatchException("Nao banque o engraçadinho...");
        	return;
        }
    }
    

    /*
    * Agencias
    */
    private static void syncAgencia() throws SQLException{
    	if(ultimobanco == null){
    		//throw new DataNotFoundException("Não é possível listar agencias sem definir o banco");
    		System.out.println("Não é possível listar agencias sem definir o banco");
    		return;
    	}
    	agencias = ultimobanco.getAgencias();
    }
    public static void buscarAgencia() throws SQLException, DataNotFoundException{
        int numero;
       
        // Verifica se já tem um banco na memória, 
        if(ultimobanco == null){
        	buscarBanco();
        }
        
        // Mostrar na tela                  
        System.out.println("---");
        System.out.println("Localizar agencia:");
        System.out.println("---");
        System.out.println("Informe o numero da agencia: ");
        try{
        	numero = input.nextInt();
        }catch(InputMismatchException e){
        	input.next(); // gambiarra para eliminar uma linha fantasma
        	System.out.println("Entrada de dados inválida");
        	return;
        }
        
        Agencia _agencia = ultimobanco.buscarAgencia(numero);
        
        // buscar agencia
        if (_agencia == null){
            // throw new DataNotFoundException("Agencia informada nao foi localizada.");
            System.out.println("Agencia informada nao foi localizada.");
            return;
        }

        System.out.println("Agencia localizada com sucesso.\n" + _agencia);
    }
    public static void listarAgencias() throws SQLException{
        // TODO: cachear a ultima consulta e dar um jeito de verificar se e necessario realizar a consulta no banco
        String out = "";
        
        // Atualiza as informações dos bancos na primeira execução
        if(agencias == null || agencias.isEmpty()){
        	syncAgencia();
        }
        
        if(agencias != null || !agencias.isEmpty()){
            for(Agencia _agencia: agencias){
                if(_agencia != null) out += _agencia+"\n";
            }
        }
        
        if(out == "") {
            //throw new RuntimeException("Nenhuma agencia foi cadastrada");
        	System.out.println("Nenhuma agencia foi cadastrada");
        	return;
        }
        
        System.out.println("---\nAgencias Cadastradas (id, numero, nome)\n---\n");
        System.out.println(out);
    }
    public static void cadastrarAgencia() throws Exception, SQLException, InputMismatchException{
                   
        // Lista dos bancos cadastrados
        //listarBancos();
    	System.out.print("---\nCADASTRAR NOVA AGENCIA\n---\n");
    	
    	if(ultimobanco == null){
    		int banco_id;
    		System.out.print("\nID do banco:");
    		try{
    			banco_id = input.nextInt();
    		}catch(InputMismatchException e){
    			input.next(); // gambiarra para eliminar uma linha fantasma
    			System.out.println("Entrada de dados inválida");
    			//	throw new InputMismatchException(e.getMessage()); // Retorna null
    			return;
    		}
    		Banco ultimobanco = getBanco(banco_id);
    		if(ultimobanco == null) {
    			//	throw new Exception("O banco informado nao existe", new Throwable());  // Retorna null
    			System.out.println("O banco informado nao existe");
    			return;
    		}
    	}

        System.out.print("Nome da agencia: ");
        input.nextLine(); // BUGFIX: o metodo nextLine não se dá muito bem com o print (bug do JAVA ???)
        nome = input.nextLine();

        System.out.print("Endereco: ");
        endereco = input.nextLine();
        
        System.out.print("Nome do gerente: ");
        nomeGerente = input.nextLine();

        // Adicionar agencia ao banco 
        try{
        	ultimaagencia = ultimobanco.cadastrarAgencia(nome, endereco, nomeGerente);
        	agencias.add(ultimaagencia);
        }catch(SQLException e){
        	//throw new SQLException(e.getMessage());
        	System.out.println(e.getMessage());
			return;
        }
        
        System.out.println(String.format("\nA agência #%s (%s) foi cadastrada com sucesso.", ultimaagencia.getNumero(), nome));
    }
    
    
    
    
    /*
     * Clientes
     */
    private static void syncCliente() throws SQLException, DataNotFoundException{
        // carrega os clientes de uma determinada agencia
    	if(ultimaagencia == null){
    		//throw new DataNotFoundException("Não é possível listar clientes sem definir a agência");
    		System.out.println("Não é possível listar clientes sem definir a agência");
    		return;
    	}
    	
    	clientes = ultimaagencia.getClientes();
    }
    public static void buscarCliente() throws SQLException, DataNotFoundException{
        int id;
       
        // Verifica se já tem uma agencia na memória, 
        if(ultimaagencia == null){
        	buscarAgencia();
        }
        
        // Mostrar na tela                  
        System.out.println("---");
        System.out.println("Localizar cliente:");
        System.out.println("---");
        System.out.println("Informe o id do cliente: ");
        try{
        	id = input.nextInt();
        }catch(InputMismatchException e){
        	input.next(); // gambiarra para eliminar uma linha fantasma
        	System.out.println("Entrada de dados inválida");
        	return;
        }
        
        Cliente _cliente = ultimaagencia.buscarCliente(id);
        
        // buscar agencia
        if (_cliente == null){
            // throw new DataNotFoundException("O cliente informada nao foi localizada.");
            System.out.println("O cliente informada nao foi localizado.");
            return;
        }

        System.out.println("Cliente localizado com sucesso.\n" + _cliente);
    }
    public static void listarClientes() throws SQLException, DataNotFoundException{
        // TODO: cachear a ultima consulta e dar um jeito de verificar se e necessario realizar a consulta no banco
        String out = "";
        
        // Atualiza as informações dos bancos na primeira execução
        if(clientes == null || clientes.isEmpty()){
            syncCliente();
        }
        
        if(clientes != null && !clientes.isEmpty()){
            for(Cliente _cliente: clientes){
                if(_cliente != null) out += _cliente+"\n";
            }
        }
        
        if(out == "") {
            //throw new DataNotFoundException("Nenhum cliente foi cadastrado");
            System.out.println("Nenhum cliente foi cadastrado");
            return;
        }
        
        System.out.println(
        		"---\nClientes Cadastrados\n---\n\n"+
        		out
        );
    }
    
    public static void cadastrarClientePF() throws Exception{
        System.out.println("---\nAdicionar nova pessoa fisica:\n---\n");
        
        // TODO: sincronizar os dados do cliente para poder fazer a verificação de campos existentes (cpf, email)
        
        boolean isValidCpf = false;
        do{
        	System.out.print("CPF: ");
        	input.nextLine(); // BUGFIX
        	cpf = input.nextLine();
        	if (!Utils.isCPF(cpf)){
        		System.out.printf("Digite um cpf válido\n");
        	}else{
        		isValidCpf = true;
        	}
        }while(!isValidCpf);
        
        System.out.print("Nome do cliente: ");
        //input.nextLine(); // BUGFIX
        nome = input.nextLine();
       
        boolean isValidEmail = false;
        do{
        	System.out.print("Email: ");
        	email = input.nextLine();
        	if (!Utils.isEmail(email)){
        		System.out.printf("Digite um email válido\n");
        	}else{
        		isValidEmail = true;
        	}
        }while(!isValidEmail);
        
        // TODO: adicionar validacao para o telefone ??
        System.out.printf("Telefone: ");
        telefone = input.nextLine();
        
        // Adicionar pessoa fisica
        try{
        	//TODO: armazenar o cpf sem formatação
        	pessoafisica = new PessoaFisica(nome, telefone, email, Utils.formatCPF(cpf));
        }catch(Exception e){
        	//throw new Exception(e);
        	System.out.println(e.getMessage());
        	return;
        }
        System.out.println("O cliente foi cadastrado com sucesso (ID, nome, tipo)\n" + pessoafisica);
    }
    
    public static void cadastrarClientePJ() throws SQLException{
    	System.out.println("---\nAdicionar nova pessoa jurídica:\n---\n");
    	
    	System.out.print("CNPJ: ");
    	input.nextLine(); // BUGFIX
        cnpj = input.nextLine();
        
        System.out.println("Razão social: ");
        nome = input.nextLine();
        
        System.out.println("Nome Fantasia: ");
        nome_fantasia = input.nextLine();
       
        boolean isValidEmail = false;
        do{
        	System.out.print("Email: ");
        	email = input.nextLine();
        	if (!Utils.isEmail(email)){
        		System.out.printf("Digite um email válido\n");
        	}else{
        		isValidEmail = true;
        	}
        }while(!isValidEmail);
        
        // TODO: adicionar validacao para o telefone ??
        System.out.printf("Telefone: ");
        telefone = input.nextLine();
        
        // Adicionar pessoa fisica
        try{
        	pessoajuridica = new PessoaJuridica(nome, telefone, email, cnpj, nome_fantasia);
        }catch(Exception e){
        	//throw new Exception(e);
        	System.out.println(e.getMessage());
        	return;
        }
        System.out.println("O cliente foi cadastrado com sucesso (ID, nome, tipo)\n" + pessoajuridica);
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
        numero = input.nextInt();
        System.out.println("Limite: ");
        limite = input.nextDouble();
                            
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
        numero = input.nextInt();                  
                            
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
        numero = input.nextInt();                   
        
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
                    limite = input.nextDouble();                   
                    
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
                valor = input.nextDouble();                   
                
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
                valor = input.nextDouble();
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
                numero = input.nextInt();                   
                
                Conta contadestino = null;
                contadestino = agencia.buscarConta(numero);
                
                if (contadestino != null){
                    
                    if (contadestino.isAtiva()){
                        System.out.println("---");
                        System.out.println("Informe o valor da transferencia:");
                        valor = input.nextDouble();
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
        numero = input.nextInt();
        System.out.println("Limite: ");
        limite = input.nextDouble();
                            
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
        numero = input.nextInt();                  
                            
        agencia.criarConta(cliente, numero, 0, "poupanca");
        conta = agencia.buscarConta(numero);
        System.out.println("Conta poupanca criada com sucesso");
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
    
    /*
     * Metodo auxiliar para popular o menu com os elementos em formato JSON
     */
    private static void populateMenu(JSONObject menu, String text, String function_name){
    	menu.put((menu.length()) + "", new JSONObject().put("text", text).put("function", function_name));
    }
    private static void populateMenu(JSONObject menu, String text){
    	menu.put((menu.length()) + "", new JSONObject().put("text", text).put("function", ""));
    }
    
    public static void showOptionsMenu(JSONObject menu){
    	for (int i = 0; i < menu.length(); i++) {
			System.out.println("(" + i + ") " + menu.getJSONObject(i+"").get("text") );
		}
    }
    
    /*
     * Executa o código principal da aplicação
     */
    public static void run() throws InputMismatchException{
        int comando = -1;
        // level = 0 : Escolha do tipo do usuário
        // level = 1 : Administrador do Banco
        // level = 2 : Cliente
        int level      = 0;
        boolean isAuth = false;
        bancos         = new ArrayList<Banco>();
        agencias       = new ArrayList<Agencia>();
        clientes       = new ArrayList<Cliente>();
        contas         = new ArrayList<Conta>();
        
        
        JSONObject menu_login, menu_admin, menu_cliente;
        
        // Opções de usuário
        menu_login = new JSONObject();
        populateMenu(menu_login, "Sair");
        populateMenu(menu_login, "Administrador do sistema");
        populateMenu(menu_login, "Cliente");
        
        // Opções de ação para o administrador (pseudo-gerente)
        menu_admin = new JSONObject();
        populateMenu(menu_admin, "Sair");
        populateMenu(menu_admin, "Voltar");
        
        populateMenu(menu_admin, "Buscar Banco", "buscarBanco");
        populateMenu(menu_admin, "Listar bancos", "listarBancos");
        populateMenu(menu_admin, "Cadastrar Banco", "cadastrarBanco");
        
        populateMenu(menu_admin, "Buscar agencia", "buscarAgencia");
        populateMenu(menu_admin, "Listar agencias", "listarAgencias");
        populateMenu(menu_admin, "Cadastrar agencia", "cadastrarAgencia");
        
        populateMenu(menu_admin, "Buscar cliente", "buscarCliente");
        populateMenu(menu_admin, "Listar clientes", "listarClientes");
        populateMenu(menu_admin, "Cadastrar Pessoa Física", "cadastrarClientePF");
        populateMenu(menu_admin, "Cadastrar Pessoa Jurídica", "cadastrarClientePJ");
        
        populateMenu(menu_admin, "Buscar conta", "buscarConta");
        populateMenu(menu_admin, "Listar contas", "listarContas");
        populateMenu(menu_admin, "Listar contas do cliente", "listarContasDoCLiente");
        
        populateMenu(menu_admin, "Cadastrar conta corrente (Pessoa Física)", "cadastrarContaCorrentePF");
        populateMenu(menu_admin, "Cadastrar conta poupanca (Pessoa Física)", "cadastrarContaPoupancaPF");
        
        populateMenu(menu_admin, "Cadastrar conta corrente (Pessoa Jurídica)", "cadastrarContaCorrentePJ");
        populateMenu(menu_admin, "Cadastrar conta poupanca (Pessoa Jurídica)", "cadastrarContaPoupancaPJ");
        
        // Opções de ação para o cliente
        menu_cliente = new JSONObject();
        populateMenu(menu_cliente, "Sair");
        populateMenu(menu_cliente, "Voltar");
        populateMenu(menu_cliente, "Ver Saldo", "verSaldo");
        populateMenu(menu_cliente, "Cancelar/Reativar conta", "toggleConta");
        populateMenu(menu_cliente, "Alterar Limite", "alterarLimite");
        populateMenu(menu_cliente, "Depositar", "depositar");
        populateMenu(menu_cliente, "Sacar", "sacar");
        populateMenu(menu_cliente, "Trasnferir", "transferir");
     
        
       
        while(comando != 0){
            try{
                if(level == 0){
                    // Menu de escolha de usuário 
                	System.out.println("\n*** Pseudo LOGIN System ***");
                	
                	showOptionsMenu(menu_login);
                	
                	System.out.print("\n"+"Escolha o tipo de usuário: " );
                }else if(level == 1){
                	System.out.println("\n*** ADMINISTRADOR do Sistema ***");
                    showOptionsMenu(menu_admin);
                    System.out.print("\n"+"Escolha a operação: " );
                }else if(level == 2){
                    // TODO: Fazer o "login" do cliente
                	System.out.println("\n*** CLIENTE ***");
                	if(isAuth){
                        // Menu de operações
                    	showOptionsMenu(menu_cliente);
                    	System.out.print("\n"+"Escolha a operação: " );
                    }else{
                    	// TODO: checar se o cliente é valido
                        // Menu de login
                    	int banco_id, agencia_id;
                        String user_email;
                        
                        System.out.println("\n*** AUTENTICAÇÃO ***\n");

                        System.out.println("\n Entre com o id do Banco: ");
                        banco_id = input.nextInt();

                        System.out.println("\n Entre com o id da sua Agencia: ");
                        agencia_id = input.nextInt();

                        System.out.println("\n Entre com o seu identificador: ");
                        user_email = input.next();
                        
                        if(!login(banco_id, agencia_id, user_email)){
                            throw new UserNotFoundException("Os identificadores estão errados ou o usuário não está cadastrado");
                        }
                    }
                }

                if(isAuth){
                    System.out.println("\nEscolha a operacao: ");
                }
                comando = input.nextInt();
                
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
                	if(comando == 0){
                		System.out.println("Ate logo!");
                	}else if(comando == 1){
                		level = 0;
                	}else{
                		// Verifica se o comando está "cadastrado" no JSON e chama (dinamicamente) o método associado
                		if( !menu_admin.has(comando+"") ){
                			throw new IllegalArgumentException("Comando inválido");
                		}
                		SistemaBancario.class.getMethod(menu_admin.getJSONObject(comando+"").get("function")+"").invoke("");
                	}
                }else if(level == 2){
                    if(isAuth){
                    	if(comando == 0){
                    		System.out.println("Ate logo!");
                    	}else if(comando == 1){
                    		level = 0;
                    	}else{
                    		// TODO: checar se o comando existe e chama a funcao
                    		if( !menu_cliente.has(comando+"") ){
                    			throw new IllegalArgumentException("Comando inválido");
                    		}
                    		SistemaBancario.class.getMethod(menu_cliente.getJSONObject(comando+"").get("function")+"").invoke("");
                    	}
                    }else{
                        // TODO: Verifica se o usuário existe
                    }
                }
            }
            //catch(SaldoInsuficienteException e){
            //  System.out.println(e.getMessage());
            //}
            catch (InputMismatchException e){
            	input.next(); // gambiarra para eliminar uma linha fantasma
                System.out.println("Comando invalido! \n");
                if(e != null){
                	System.out.println(e.getMessage());
                }
                return;
            }
            catch(IllegalArgumentException e){
                System.out.println(e.getMessage()); 
            }
            catch(Exception e){
                System.out.println(e.getMessage());
            }
            
        }
    }
    
    
    
    /*
     * Verifica as dependências (Banco de dados presente, etc...) e inicia o sistema
     */
    public static void main(String[] args)  throws SQLException {
        //try(Connection connection = new ConnectionFactory().getConnection()){
        try{
            Class.forName("com.mysql.jdbc.Driver");
            connection = new ConnectionFactory().getConnection();
            stm = connection.createStatement();
            // Executa o menu principal
            run();
            
            //HashMap<Integer, HashMap<String, String>> menulist = new HashMap<Integer, HashMap<String, String>>();
            //SistemaBancario.class.getMethod("test").invoke("");
        }
        catch (ClassNotFoundException e) {
            System.out.println("O driver MySQL JDBC nao foi encontrado");
            //e.printStackTrace();
        }
        /*catch (SQLException e) {
            System.out.println(e.getMessage());
            // "Nao foi possivel estabelecer uma conexao com o banco de dados"
            //e.printStackTrace();
            return;
        }*/
        catch (InputMismatchException e){
            System.out.println("Comando invalido! \n"+e.getMessage());
        } 
        //catch (DataNotFoundException e){
        //	System.out.println(e.getMessage());
        //}
        catch(NullPointerException e){
            System.out.println("Caught in NullPointerException block");
        }
        
        // TODO: organizar as exceptions
       /* catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        catch (NoSuchMethodException | SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
        
        
        
        finally{
            connection.close();
        }
    }
}