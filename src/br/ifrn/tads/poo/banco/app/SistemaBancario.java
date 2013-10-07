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
    
    private static ArrayList<PessoaFisica> pessoasfisicas = null;
    private static PessoaFisica ultimapessoafisica = null;
    private static ArrayList<PessoaJuridica> pessoasjuridicas = null;
    private static PessoaJuridica ultimapessoajuridica = null;
    
    private static ArrayList<Conta> contas = null;
    private static Conta ultimaconta = null;
    
    // Outros dados
    //private static Conta conta = null;
    private static Scanner input = new Scanner(System.in);

    // variaveis usadas nos metodos estaticos (GAMBIARRA???)
    private static String nome = "", endereco = "", nomeGerente = "", telefone = "", email = "", cpf = "", cnpj = "", nome_fantasia = "";
    private static int numero = 0;
    private static double limite = 0, valor = 0;
    private static boolean inputValid = true;
   
    // TODO: remover detalhes das exceptions geradas pelos métodos chamados dinamicamente

    // Utils
    public static void out(String msg){
        System.out.print(msg);
        System.out.flush();
    }
    public static void outln(String msg){
        System.out.println(msg);
        System.out.flush();
        /*if(input.hasNextLine()){
            input.nextLine(); // Elimina uma linha "fantasma" que é gerada pelo println
        }*/
    }
    public static void err(String msg){
        System.err.print(msg);
        System.err.flush();
    }
    public static void errln(String msg){
        System.err.println(msg);
        System.err.flush();
        /*if(input.hasNextLine()){
            input.nextLine(); // Elimina uma linha "fantasma" que é gerada pelo println
        }*/
    }
    public static void consumeGhostLine(){
    	// gambiarra para eliminar uma linha fantasma
    	if(input.hasNextLine()){
        	input.nextLine();
        }
    }
    /**
     * Metodo auxiliar para validação de entrada de dados
     * @param question Mensagem auxiliar em caso de erro
     * @return String
     */
    private static String strin(String question){
    	String out = "";
    	boolean _inputValid = false;
        do{
        	try{
        		consumeGhostLine();
        		
        		out = input.nextLine();
                if(out.toString().trim().equalsIgnoreCase("")){
                	err("invalido");
                	_inputValid  = false;
                	consumeGhostLine();
                	if(!question.trim().equalsIgnoreCase("")){
                		err(question);
                	}
                }else{
                	_inputValid  = true;
                }
        	}
            catch(InputMismatchException e){
            	_inputValid  = false;
            	consumeGhostLine();
        		errln("Entrada de dados inválida");
        	}
        }while(!_inputValid || out.trim().equalsIgnoreCase(""));
        return out.toString();
    }
    private static int intin(){
    	int out = 0;
    	inputValid = false;
        do{
        	try{
        		out = input.nextInt();
        		inputValid = true;
        	}catch(InputMismatchException e){
        		inputValid = false;
        		consumeGhostLine();
        		err("Não banque o engraçadinho: ");
        	}
        }while(!inputValid);
        return out;
    }
    private static double doublein(){
    	double out = 0;
    	inputValid = false;
        do{
        	try{
        		out = input.nextDouble();
        		inputValid = true;
        	}catch(InputMismatchException e){
        		inputValid = false;
        		consumeGhostLine();
        		err("Não banque o engraçadinho: ");
        	}
        }while(!inputValid);
        return out;
    }

    /*
     * Estrutura básica dos métodos (admin)
     * 
     * syncX -> sincroniza o banco de dados "local" de X com o database
     * getX -> retorna o objeto X (se houver)
     * buscarX -> faz uma busca (individual) por X e armazena na memória (se achar)
     * listarX -> exibe uma relação dos elementos X (se houver)
     * cadastrarX -> formulário para inserção de X no database e na memória 
     */
    
    
    /*
     * Bancos
     */
    private static void syncBanco() throws SQLException{
    	// usa try-catch para suprimir algumas informações do erro
    	try{
	    	ResultSet result = stm.executeQuery("SELECT * FROM bancos");
	        while(result.next()){
	            ultimobanco = new Banco(result.getInt("id"), result.getInt("numero"), result.getString("nome"));
	            bancos.add(ultimobanco);
	        }
    	}
    	catch(SQLException e){
    		throw new SQLException(e.getMessage());
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
    public static void buscarBanco() throws SQLException, DataNotFoundException{
        // Mostrar na tela                  
        outln("---\nLocalizar banco:\n---\n");
        out("Informe o ID do banco: ");
        
        int id = intin();
        
        ultimobanco = getBanco(id);
        if (ultimobanco == null){
            throw new DataNotFoundException("Este banco não existe");
        }
        outln("---\nBanco localizado com sucesso (ID, numero, nome)\n---\n" + ultimobanco);
        out("Registro carregado na memória\n");
    }
    public static void listarBancos() throws SQLException, DataNotFoundException{
        // TODO: cachear a ultima consulta e dar um jeito de verificar se e necessario realizar a consulta no banco
        String out = "";
        
        // Atualiza as informações dos bancos na primeira execução
        if(bancos == null || bancos.isEmpty()){
            syncBanco();
        }
        
        if(bancos != null && !bancos.isEmpty()){
            for(Banco _banco: bancos){
                if(_banco != null) out += _banco+"\n";
            }
        }
        
        if(out.equalsIgnoreCase("")) {
        	consumeGhostLine();
            throw new DataNotFoundException("Nenhum banco foi cadastrado");
        }
        
        outln("---\nBancos Cadastrados (ID, numero, nome)\n---\n");
        outln(out);
    }
    public static void cadastrarBanco() throws SQLException{
    	out("\n---\nCadastrar novo banco\n---\n");
    	out("\nNome do banco: ");
        consumeGhostLine();
        
        nome = strin("");
        ultimobanco = new Banco(nome);
        bancos.add(ultimobanco);
        outln("O Banco foi cadastrado com sucesso. (ID, numero, nome)\n" + ultimobanco);
    }
    public static void apagarBanco() throws SQLException, DataNotFoundException{
    	out("\n---\nApagar banco\n---\n");
    	out("\nID do banco: ");
        consumeGhostLine();
        
        int id = intin();
        
        Banco _banco = getBanco(id);
        if (_banco == null){
        	consumeGhostLine();
            throw new DataNotFoundException("Este banco não existe");
        }
        
        bancos.remove(_banco);
        if (ultimobanco == _banco){
        	ultimobanco = null;
        }
        _banco.delete();

        outln("O Banco foi deletado com sucesso.\n");
    }
    

    /*
    * Agencias
    */
    private static void syncAgencia(boolean useSessionBank) throws SQLException, DataNotFoundException{
    	if(ultimobanco == null || useSessionBank == true){
    		buscarBanco();
    	}
    	agencias = ultimobanco.getAgencias();
    }
    private static void syncAgencia() throws SQLException, DataNotFoundException{
    	if(ultimobanco == null){
    		buscarBanco();
    	}
    	agencias = ultimobanco.getAgencias();
    }
    private static Agencia getAgencia(int id) throws SQLException, DataNotFoundException{
        if(agencias == null || agencias.isEmpty()){
            syncAgencia(true);
        }
        
        if(agencias != null && !agencias.isEmpty()){
            for(Agencia _a: agencias){
                if(_a.getId() == id) return _a;
            }
        }

        return null;
    }
    public static void buscarAgencia() throws SQLException, DataNotFoundException{
        // Verifica se já tem um banco na memória, 
        if(ultimobanco == null){
        	buscarBanco();
        }
        
        // Mostrar na tela                  
        outln("---\nLocalizar agencia:\n---\n");
        outln("Informe o numero da agencia: ");
        int numero = intin();
        
        ultimaagencia = ultimobanco.buscarAgencia(numero);
        
        // buscar agencia
        if (ultimaagencia == null){
            throw new DataNotFoundException("Agencia informada nao foi localizada.");
        }

        outln("Agencia localizada com sucesso.\n" + ultimaagencia);
    }
    public static void listarAgencias() throws SQLException, DataNotFoundException{
        // TODO: cachear a ultima consulta e dar um jeito de verificar se e necessario realizar a consulta no banco

        // Atualiza as informações dos bancos na primeira execução
        if(agencias == null || agencias.isEmpty()){
        	syncAgencia();
        }
        
        String out = "";
        if(agencias != null || !agencias.isEmpty()){
            for(Agencia _agencia: agencias){
                if(_agencia != null) out += _agencia+"\n";
            }
        }
        
        if(out.equalsIgnoreCase("")) {
            throw new DataNotFoundException("Nenhuma agencia foi cadastrada para o banco " + ultimobanco.getNome());
        }
        
        outln("---\nAgencias Cadastradas (id, numero, nome)\n---\n" + out);
    }
    public static void cadastrarAgencia() throws SQLException, DataNotFoundException{
    	out("---\nCADASTRAR NOVA AGENCIA\n---\n");

    	if(ultimobanco == null){
    		buscarBanco();
    	}

        out("Nome da agencia: ");
        nome = strin("");

        out("Endereco: ");
        endereco = strin("");

        out("Nome do gerente: ");
        nomeGerente = strin("");

        // Adicionar agencia ao banco 
        ultimaagencia = ultimobanco.cadastrarAgencia(nome, endereco, nomeGerente);
        agencias.add(ultimaagencia);

        outln(String.format("\nA agência #%s (%s) foi cadastrada com sucesso.", ultimaagencia.getNumero(), nome));
    }
    public static void apagarAgencia() throws SQLException, DataNotFoundException{
    	out("\n---\nAPAGAR AGÊNCIA\n---\n");
    	out("\nID da agência: ");
        consumeGhostLine();

        int id = intin();

        Agencia _agencia = getAgencia(id);
        if (_agencia == null){
        	consumeGhostLine();
            throw new DataNotFoundException("Esta agência não existe");
        }

        agencias.remove(_agencia);
        if (ultimaagencia == _agencia){
        	ultimaagencia = null;
        }
        _agencia.delete();

        outln("A agência foi deletada com sucesso.\n");
    }
    

    /*
     * Clientes
     */
    private static void syncCliente() throws SQLException, DataNotFoundException{
        // carrega os clientes de uma determinada agencia
    	if(ultimaagencia == null){
    		buscarAgencia();
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
        outln("---\nLocalizar cliente:---\n");
        outln("Informe o id do cliente: ");
        try{
        	id = input.nextInt();
        }catch(InputMismatchException e){
        	input.next(); // gambiarra para eliminar uma linha fantasma
        	errln("Entrada de dados inválida");
        	return;
        }
        
        ultimocliente = ultimaagencia.buscarCliente(id);

        if (ultimocliente == null){
            // throw new DataNotFoundException("O cliente informada nao foi localizada.");
            errln("O cliente informada nao foi localizado.");
            return;
        }

        outln("Cliente localizado com sucesso.\n" + ultimocliente);
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
            errln("Nenhum cliente foi cadastrado");
            return;
        }
        
        outln(
        		"---\nClientes Cadastrados\n---\n\n"+
        		out
        );
    }
    
    /*
     * Pessoas (Fisica/Jurídica)
     */
    private static void syncPF() throws SQLException{
    	// usa try-catch para suprimir algumas informações do erro
    	try{
	    	ResultSet result = stm.executeQuery("SELECT * FROM clientes WHERE tiposcliente_id = 3");
	    	while(result.next()){
	            ultimapessoafisica = new PessoaFisica(result.getInt("id"), result.getInt("tiposcliente_id"), result.getString("nome"), result.getString("telefone"), result.getString("email"), result.getString("cpf"));
	            pessoasfisicas.add(ultimapessoafisica);
	        }
    	}
    	catch(SQLException e){
    		throw new SQLException(e.getMessage());
    	}
    }
    private static void syncPJ() throws SQLException{
    	// usa try-catch para suprimir algumas informações do erro
    	try{
	    	ResultSet result = stm.executeQuery("SELECT * FROM clientes WHERE tiposcliente_id = 2");
	        while(result.next()){
	            ultimapessoajuridica = new PessoaJuridica(result.getInt("id"), result.getInt("tiposcliente_id"), result.getString("nome"), result.getString("telefone"), result.getString("email"), result.getString("cnpj"), result.getString("nome_fantasia"));
	            pessoasjuridicas.add(ultimapessoajuridica);
	        }
    	}
    	catch(SQLException e){
    		throw new SQLException(e.getMessage());
    	}
    }
    public static void cadastrarClientePF() throws SQLException{
        outln("\n---\nAdicionar nova pessoa fisica:\n---\n");
        
        consumeGhostLine();
        
        out("CPF: ");
        boolean isValidCpf = false;
        do{
        	cpf = input.nextLine();
        	if (!Utils.isCPF(cpf)){
        		err("Digite um cpf válido: ");
        		consumeGhostLine();
        	}else{
        		isValidCpf = true;
        	}
        }while(!isValidCpf);
        
        out("Nome do cliente: ");
        nome = strin("");
       
        boolean isValidEmail = false;
        do{
        	out("Email: ");
        	email = input.nextLine();
        	if (!Utils.isEmail(email)){
        		err("Digite um email válido\n");
        		consumeGhostLine();
        	}else{
        		isValidEmail = true;
        	}
        }while(!isValidEmail);
        
        // TODO: adicionar validacao para o telefone ??
        out("Telefone: ");
        telefone = strin("");
        
        // TODO: sincronizar os dados do cliente para poder fazer a verificação de campos existentes (por cpf ou email), para evitar de cadastrar mais de uma vez o mesmo cliente
        
        // Adicionar pessoa fisica
        ultimapessoafisica = new PessoaFisica(nome, telefone, email, cpf);
        pessoasfisicas.add(ultimapessoafisica);
       
        outln("O cliente foi cadastrado com sucesso (ID, nome, tipo)\n" + ultimapessoafisica);
    }
    public static void cadastrarClientePJ() throws SQLException{
    	outln("---\nAdicionar nova pessoa jurídica:\n---\n");
    	
    	out("CNPJ: ");
        cnpj = strin("");
        
        out("Razão social: ");
        nome = strin("");
        
        out("Nome Fantasia: ");
        nome_fantasia = strin("");
       
        boolean isValidEmail = false;
        do{
        	out("Email: ");
        	email = strin("");
        	if (!Utils.isEmail(email)){
        		err("Digite um email válido\n");
        	}else{
        		isValidEmail = true;
        	}
        }while(!isValidEmail);
        
        // TODO: adicionar validacao para o telefone ??
        out("Telefone: ");
        telefone = strin("");
        
        // Adicionar pessoa fisica
        ultimapessoajuridica = new PessoaJuridica(nome, telefone, email, cnpj, nome_fantasia);
        
        outln("O cliente foi cadastrado com sucesso (ID, nome, tipo)\n" + ultimapessoajuridica);
    }
    public static void listarClientePF() throws SQLException, DataNotFoundException{
    	if(pessoasfisicas == null || pessoasfisicas.isEmpty()){
            syncPF();
        }
        String out = "";
        if(pessoasfisicas != null && !pessoasfisicas.isEmpty()){
            for(PessoaFisica _pf: pessoasfisicas){
                if(_pf != null) out += _pf+"\n";
            }
        }
        
        if(out.equalsIgnoreCase("")) {
        	consumeGhostLine();
            throw new DataNotFoundException("Nenhuma pessoa física foi cadastrada");
        }
        
        outln("---\nPessoas Físicas Cadastradas (ID, nome, cpf)\n---\n" + out);
    }
    public static void listarClientePJ() throws SQLException, DataNotFoundException{
        if(pessoasjuridicas == null || pessoasjuridicas.isEmpty()){
            syncPJ();
        }
        String out = "";
        if(pessoasjuridicas != null || !pessoasjuridicas.isEmpty()){
            for(PessoaJuridica _pj: pessoasjuridicas){
                if(_pj != null) out += _pj+"\n";
            }
        }
        
        if(out.equalsIgnoreCase("")) {
        	consumeGhostLine();
            throw new DataNotFoundException("Nenhuma pessoa jurídica foi cadastrada");
        }
        
        outln("---\nPessoas Jurídicas Cadastradas (ID, nome, cnpj)\n---\n" + out);
    }
    // TODO: incluir metodos para deletar clientes/pessoas
    
    /*
     * Contas
     */
    public static void buscarConta() throws SQLException, DataNotFoundException{
    	// Verifica se já tem uma agencia na memória, 
        if(ultimocliente == null){
        	buscarCliente();
        }
        outln("---\nLocalizar Conta\n---\n");
        
        // TODO: add try-catch
        out("---\nInforme o numero da conta: ");
        numero = intin();

        ultimaconta = ultimocliente.buscarConta(numero);
        if (ultimaconta == null){
            throw new DataNotFoundException("O cliente informado nao foi localizado.");
        }
        outln("Conta localizada com sucesso.\n" + ultimaconta);
    }
    public static void listarContas() throws SQLException, DataNotFoundException{
    	// Listar contas de uma agência ou cliente
        String out = "";
        int choise = 0;
        outln("---\nListar contas para\n---\n");
    	outln("(1) Agencias");
    	outln("(2) Clientes");
    	out(": ");
        do{
    		choise = intin();
    		if(choise < 1 || choise > 2){
    			//throw new InputMismatchException();
    			err("Escolha a opção (1) ou (2): ");
    		}
        }while(choise < 1 || choise > 2);
        
        if(choise == 1){
        	// lista contas da ultima agencia registrada
        	
        	if( ultimaagencia == null ){
        		buscarAgencia();
        	}
        	
        	contas = ultimaagencia.getContas();
        }else{
        	// lista as contas do ultimo cliente registrado
        	if( ultimocliente == null ){
        		buscarCliente();
        	}
        	
        	contas = ultimocliente.getContas();
        }
        
        if(contas != null && !contas.isEmpty()){
            for(Conta _conta: contas){
                if(_conta != null) out += _conta+"\n";
            }
        }

        if(out == "") {
            throw new DataNotFoundException("Nenhuma conta foi cadastrada");
        }
        
        outln( "---\nContas Cadastradas\n---\n\n"+ out );
    }
    public static void cadastrarContaCorrentePF() throws SQLException, DataNotFoundException{
    	ultimocliente = ultimapessoafisica;
        
        // Mostrar na tela                  
        outln("---\nAdicionar nova conta corrente (Pessoa Física)\n---\n");
        
        if(ultimocliente == null){
        	buscarCliente();
        }
        outln("---");
        
        //System.out.println("Número: ");
        //numero = input.nextInt();
        
        out("Limite: ");
        limite = doublein();
        
        // TODO:validar o cadastro

        ultimaagencia.criarContaCorrente(ultimocliente, limite);
        ultimaconta = ultimaagencia.buscarConta(numero);
        outln("Conta corrente criada com sucesso para o cliente " + ultimocliente.getNome());
    }
    public static void cadastrarContaPoupancaPF() throws SQLException, DataNotFoundException{
        // Mostrar na tela                  
    	outln("---\nAdicionar nova conta poupança (Pessoa Física)\n---\n");
    	
    	ultimocliente = ultimapessoafisica;
    	if(ultimocliente == null){
        	buscarCliente();
        }
                            
        ultimaagencia.criarContaPoupanca(ultimocliente);
        ultimaconta = ultimaagencia.buscarConta(numero);
        outln("Conta poupanca criada com sucesso para o cliente " + ultimocliente.getNome());
    }
    public static void cadastrarContaCorrentePJ() throws SQLException, DataNotFoundException{
        // TODO: mostrar dados que estão na memória e perguntar se deseja utilizá-los ou trocar
          
        outln("---\nAdicionar nova conta corrente (Pessoa Jurídica)\n---\n");
        ultimocliente = ultimapessoajuridica;
        if(ultimocliente == null){
        	buscarCliente();
        }
        outln("---");
        
        out("Limite: ");
        limite = doublein();
                            
        ultimaagencia.criarContaCorrente(ultimocliente, limite);
        ultimaconta = ultimaagencia.buscarConta(numero);
        outln("Conta corrente criada com sucesso para o cliente " + ultimocliente.getNome());
    }
    public static void cadastrarContaPoupancaPJ() throws SQLException, DataNotFoundException{
    	outln("---\nAdicionar nova conta poupança (Pessoa Jurídica)\n---\n");
    	
        ultimocliente = ultimapessoajuridica;
        if(ultimocliente == null){
        	buscarCliente();
        }
                            
        ultimaagencia.criarContaPoupanca(ultimocliente);
        ultimaconta = ultimaagencia.buscarConta(numero);
        outln("Conta poupanca criada com sucesso para o cliente " + ultimocliente.getNome());
    }
    
    
    
    
    
    /*
     * LEVEL 2 - Área do Cliente
     */
    public static void login() throws UserNotFoundException{
        
        int banco_id, agencia_id;
        String user_email;
        
        outln("\n*** AUTENTICAÇÃO ***\n");

        outln("\n Entre com o id do Banco: ");
        banco_id = intin();

        outln("\n Entre com o id da sua Agencia: ");
        agencia_id = intin();

        outln("\n Entre com o seu email: ");
        user_email = strin("");

        boolean _isAuth = false;
        for(Banco _banco: bancos){
            if(banco_id == _banco.getId()){
            	ultimobanco = _banco;
                ultimaagencia =_banco.buscarAgencia(agencia_id); 
                if(ultimaagencia != null){
                    ultimocliente = ultimaagencia.buscarCliente(user_email);
                	if(ultimocliente != null){
                        _isAuth = true;
                    }
                }
            }
        }
        
        if(!_isAuth){
            throw new UserNotFoundException("Os identificadores estão errados ou o usuário não está cadastrado");
        }
    }
    
    public static void verSaldo(){
    	// TODO: limitar o cadastro para uma conta por agência para cada cliente
    	if(ultimocliente == null){
        	login();
        }

    	ArrayList<Conta> _contas = ultimocliente.getContas();
    	if(_contas == null){
    		consumeGhostLine();
            throw new DataNotFoundException("Nenhuma conta cadastrada");
    	}
    	/*for(Conta _c: _contas){
    		if( _c. )
    	}
    	
    	
    	ultimaagencia.*
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
        // TODO
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
        // TODO
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
        // TODO
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
        // TODO
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
        // TODO
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
    		outln("(" + i + ") " + menu.getJSONObject(i+"").get("text") );
		}
    }
    
    /*
     * Executa o código principal da aplicação
     */
    public static void run() throws LimboException{
        // level = 0  : Escolha do tipo do usuário (default)
        // level = 1 : Administrador do Banco
        // level = 2 : Cliente
        // 
        // level = 3 : Admin Banco
        // level = 4 : Admin Agência
        // level = 5 : Admin Cliente
        // level = 6 : Admin Pessoas
        // level = 7 : Admin Contas
        
        int level        = 0;
        int comando_menu = -1;
        boolean isAuth   = false;
        bancos           = new ArrayList<Banco>();
        agencias         = new ArrayList<Agencia>();
        clientes         = new ArrayList<Cliente>();
        contas           = new ArrayList<Conta>();
        pessoasfisicas   = new ArrayList<PessoaFisica>();
        pessoasjuridicas = new ArrayList<PessoaJuridica>();
        
        JSONObject menu_login, menu_admin, menu_admin_banco, menu_admin_agencia, menu_admin_cliente, menu_admin_pessoa, menu_admin_conta, menu_cliente;
        
        // Opções de usuário
        menu_login = new JSONObject();
        populateMenu(menu_login, "Sair");
        populateMenu(menu_login, "Administrador do sistema");
        populateMenu(menu_login, "Cliente");
        
        
        // Opções de ação para o administrador (pseudo-gerente)
        menu_admin = new JSONObject();
        populateMenu(menu_admin, "Sair");
        populateMenu(menu_admin, "Voltar");
        populateMenu(menu_admin, "Administrar Bancos");
        populateMenu(menu_admin, "Administrar Agências");
        populateMenu(menu_admin, "Administrar Clientes");
        populateMenu(menu_admin, "Administrar Pessoas");
        populateMenu(menu_admin, "Administrar Contas");
        
        menu_admin_banco = new JSONObject();
        populateMenu(menu_admin_banco, "Sair");
        populateMenu(menu_admin_banco, "Voltar");
        populateMenu(menu_admin_banco, "Buscar Banco", "buscarBanco");
        populateMenu(menu_admin_banco, "Listar bancos", "listarBancos");
        populateMenu(menu_admin_banco, "Cadastrar Banco", "cadastrarBanco");
        populateMenu(menu_admin_banco, "Apagar Banco", "apagarBanco");
        
        menu_admin_agencia = new JSONObject();
        populateMenu(menu_admin_agencia, "Sair");
        populateMenu(menu_admin_agencia, "Voltar");
        populateMenu(menu_admin_agencia, "Buscar agencia", "buscarAgencia");
        populateMenu(menu_admin_agencia, "Listar agencias", "listarAgencias");
        populateMenu(menu_admin_agencia, "Cadastrar agencia", "cadastrarAgencia");
        populateMenu(menu_admin_agencia, "Apagar agência", "apagarAgencia");
        
        menu_admin_cliente = new JSONObject();
        populateMenu(menu_admin_cliente, "Sair");
        populateMenu(menu_admin_cliente, "Voltar");
        populateMenu(menu_admin_cliente, "Buscar cliente", "buscarCliente");
        populateMenu(menu_admin_cliente, "Listar clientes", "listarClientes");
        
        menu_admin_pessoa = new JSONObject();
        populateMenu(menu_admin_pessoa, "Sair");
        populateMenu(menu_admin_pessoa, "Voltar");
        populateMenu(menu_admin_pessoa, "Cadastrar Pessoa Física", "cadastrarClientePF");
        populateMenu(menu_admin_pessoa, "Cadastrar Pessoa Jurídica", "cadastrarClientePJ");
        populateMenu(menu_admin_pessoa, "Listar Pessoa Física", "listarClientePF");
        populateMenu(menu_admin_pessoa, "Listar Pessoa Jurídica", "listarClientePJ");
        
        menu_admin_conta = new JSONObject();
        populateMenu(menu_admin_conta, "Sair");
        populateMenu(menu_admin_conta, "Voltar");
        populateMenu(menu_admin_conta, "Buscar conta", "buscarConta");
        populateMenu(menu_admin_conta, "Listar contas", "listarContas");
        populateMenu(menu_admin_conta, "Listar contas do cliente", "listarContasDoCliente");
        populateMenu(menu_admin_conta, "Cadastrar conta corrente (Pessoa Física)", "cadastrarContaCorrentePF");
        populateMenu(menu_admin_conta, "Cadastrar conta poupanca (Pessoa Física)", "cadastrarContaPoupancaPF");
        populateMenu(menu_admin_conta, "Cadastrar conta corrente (Pessoa Jurídica)", "cadastrarContaCorrentePJ");
        populateMenu(menu_admin_conta, "Cadastrar conta poupanca (Pessoa Jurídica)", "cadastrarContaPoupancaPJ");
        
        
        // Opções de ação para o cliente
        menu_cliente = new JSONObject();
        populateMenu(menu_cliente, "Sair");
        populateMenu(menu_cliente, "Voltar");
        populateMenu(menu_cliente, "Ver Saldo", "verSaldo");
        populateMenu(menu_cliente, "Depositar", "depositar");
        populateMenu(menu_cliente, "Sacar", "sacar");
        populateMenu(menu_cliente, "Trasnferir", "transferir");
        populateMenu(menu_cliente, "Alterar Limite", "alterarLimite");
        populateMenu(menu_cliente, "Cancelar/Reativar conta", "toggleConta");
     
        
       
        while(comando_menu != 0){
            try{
            	switch(level){
	                case 0: // PseudoLogin
	                	// Menu de escolha de usuário 
	                	outln("\n*** Pseudo LOGIN System ***");
	                	showOptionsMenu(menu_login);
	                	out("\n"+"Escolha o tipo de usuário: " );
	                    break;
	                case 1: // Admin
                        outln("\n*** ADMINISTRADOR do Sistema ***");
                        showOptionsMenu(menu_admin);
                        out("\n"+"Escolha a operação: " );
                        break;
                    case 3: // Admin
                        outln("\n*** ADMINISTRAR BANCO ***");
                        showOptionsMenu(menu_admin_banco);
                        out("\n"+"Escolha a operação: " );
                        break;
                    case 4: // Admin
                        outln("\n*** ADMINISTRAR AGÊNCIA ***");
                        showOptionsMenu(menu_admin_agencia);
                        out("\n"+"Escolha a operação: " );
                        break;
                    case 5: // Admin
                        outln("\n*** ADMINISTRAR CLIENTE ***");
                        showOptionsMenu(menu_admin_cliente);
                        out("\n"+"Escolha a operação: " );
                        break;
                    case 6: // Admin
                        outln("\n*** ADMINISTRAR PESSOAS ***");
                        showOptionsMenu(menu_admin_pessoa);
                        out("\n"+"Escolha a operação: " );
                        break;
                    case 7: // Admin
	                	outln("\n*** ADMINISTRAR CONTAS ***");
	                    showOptionsMenu(menu_admin_conta);
	                    out("\n"+"Escolha a operação: " );
	                    break;
                    case 2: // Cliente
	                	// TODO: Fazer o "login" do cliente
	                	outln("\n*** ÁREA DO CLIENTE ***");
	                	if(isAuth){
	                        // Menu de operações
	                    	showOptionsMenu(menu_cliente);
	                    	out("\n"+"Escolha a operação: " );
	                    }else{
	                    	// TODO: checar se o cliente é valido
	                        // Menu de login
	                    	login();
	                    }
	                    break;
	                default:
	                    throw new LimboException();// Se chegou até aqui, Socorro!!
	            }

                if(isAuth){
                    outln("\nEscolha a operacao: ");
                }
                
                inputValid = false;
                do{
                	try{
                		comando_menu = input.nextInt();
                        // Verifica se a entrada existe nas opções do menu atual
                        switch(level){
                            case 0: // PseudoLogin
                                if(comando_menu >= menu_login.length()){
                                    throw new InvalidInputException();
                                }
                                break;
                            case 1: // Admin
                                if(comando_menu >= menu_admin.length()){
                                    throw new InvalidInputException();
                                }
                                break;
                            case 3: // Admin Bancos
                                if(comando_menu >= menu_admin_banco.length()){
                                    throw new InvalidInputException();
                                }
                                break;
                            case 4: // Admin Agências
                                if(comando_menu >= menu_admin_agencia.length()){
                                    throw new InvalidInputException();
                                }
                                break;
                            case 5: // Admin Clientes
                                if(comando_menu >= menu_admin_cliente.length()){
                                    throw new InvalidInputException();
                                }
                                break;
                            case 6: // Admin Pessoas
                                if(comando_menu >= menu_admin_pessoa.length()){
                                    throw new InvalidInputException();
                                }
                                break;
                            case 7: // Admin Contas
                                if(comando_menu >= menu_admin_conta.length()){
                                    throw new InvalidInputException();
                                }
                                break;

                            case 2: // Cliente
                                if(comando_menu >= menu_cliente.length()){
                                    throw new InvalidInputException();
                                }
                                break;
                            default:
                                throw new LimboException();// Se chegou até aqui, Socorro!!
                        }
                		inputValid = true;
                	}
                    catch(InputMismatchException e){
                    	consumeGhostLine();
                		inputValid = false;
                		err("Não banque o engraçadinho: ");
                	}
                    catch(InvalidInputException e){
                        inputValid = false;
                        err(e.getMessage() + ": ");
                        consumeGhostLine();
                    }
                }while(!inputValid);
                
                // Executa a operação relacionada ao comando informado
                if(comando_menu == 0){
                	errln("Sistema encerrado!");
                }
                else if(comando_menu == 1 && level > 0){
                    level = level >= 3 ? 1 : 0;
                }
                else{
                	if(level == 0){
                        level = comando_menu;
                    }
                	else if(level == 1){
                        level = comando_menu + 1;
                    }
                    else if(level == 3){
                        invokeMethod(menu_admin_banco, comando_menu);
                    }
                    else if(level == 4){
                        invokeMethod(menu_admin_agencia, comando_menu);
                    }
                    else if(level == 5){
                        invokeMethod(menu_admin_cliente, comando_menu);
                    }
                    else if(level == 6){
                        invokeMethod(menu_admin_pessoa, comando_menu);
                    }
                	else if(level == 7){
                		invokeMethod(menu_admin_conta, comando_menu);
                	}
                	else if(level == 2){
                        if(isAuth){
                        	invokeMethod(menu_cliente, comando_menu);
                        }else{
                            // TODO: Verifica se o usuário existe
                        }
                	}
                }
            }
            catch (InvalidInputException e){
            	consumeGhostLine();
                errln("Comando invalido! \n");
                errln(e.getMessage());
            }
            catch (InvocationTargetException e) {
    			err(e.getCause().toString());
    		}
            catch(UserNotFoundException e){
                errln(e.getMessage()); 
            }
            /*catch(DataNotFoundException e){
                errln(e.getMessage()); 
            }*/
        }
    }
    
    /**
     * Verifica se o comando_menu está "cadastrado" no JSON e chama (dinamicamente) o método associado
     * 
     * @param menu_options JSONObject list
     * @param comando_menu Comando selecionado no menu
     * @throws InvalidInputException
     */
    public static void invokeMethod(JSONObject menu_options, int comando_menu) throws InvalidInputException, InvocationTargetException{
    	if( !menu_options.has(comando_menu+"") ){
			throw new InvalidInputException();
		}
		
		try {
			SistemaBancario.class.getMethod(menu_options.getJSONObject(comando_menu+"").get("function")+"").invoke("");
		//} catch (InvocationTargetException e) {
			//throw new InvocationTargetException(e);
			//errln(e.getCause().toString());
		} catch (IllegalAccessException e) {
			errln("Erro interno: Acesso Ilegal\n" + e.getMessage());
		} catch (NoSuchMethodException e) {
			errln("Erro interno: Método inexistente\n" + e.getMessage());
		}
    }
    
    /*
     * Verifica as dependências (Banco de dados presente, etc...) e inicia o sistema
     */
    public static void main(String[] args)  throws SQLException {
    	// 1º nivel do Try-Catch
        // Captura exceptions de conexão com banco (essencial para o funcionamento da aplicação)
    	
    	//try(Connection connection = new ConnectionFactory().getConnection()){
        try{
            Class.forName("com.mysql.jdbc.Driver");
            connection = new ConnectionFactory().getConnection();
            stm = connection.createStatement();
            
            run();// Executa o menu principal
        }
        catch (ClassNotFoundException e) {
            errln("O driver MySQL JDBC nao foi encontrado");
            //e.printStackTrace();
        }
        catch (SQLException e) {
            // "Nao foi possivel estabelecer uma conexao com o banco de dados"
            errln(e.getMessage());
            //e.printStackTrace();
        }
        catch (LimboException e) {
        	errln(e.getMessage());
        }
        finally{
            connection.close();
        }
    }
}