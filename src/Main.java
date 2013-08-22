import java.util.Scanner;

import br.ifrn.tads.poo.banco.*;

public class Main {

	public static void main(String[] args) {
		
		int comando = -1;
        Scanner sc = new Scanner(System.in);
        Banco banco = null;
 
        while(comando != 0){
            System.out.println("");
            System.out.println("1 - Iniciar banco");
            System.out.println("2 - Adicionar agencia");
            System.out.println("3 - Adicionar cliente");
            System.out.println("4 - Adicionar conta");
            System.out.println("0 - Sair");
            System.out.println("");
 
            System.out.println("Escolha a operacao: ");
            comando = sc.nextInt();
 
            switch(comando){
                case 0:
                    System.out.println("Ate logo!");
                    break;
                case 1: 
                	String nome = "";
                	int numero;
                	System.out.println("---");
                    System.out.println("Inicializar...");
                    System.out.println("---");
                	System.out.println("Nome do banco: ");
                    nome = sc.next();
                    System.out.println("codigo do banco: ");
                    numero = sc.nextInt();
                    
                    banco = new Banco(numero, nome);
                    break;
                case 2:
                	System.out.println("---");
                    System.out.println("Adicionar agencia...");
                    System.out.println("---");
                	
                    System.out.println(banco.getNumero()+" - "+banco.getNome());                	
                    break;
                case 3:
                    break;
                case 4:
                    break;
                default:
                    System.out.println("Nao entendi o comando.");
                    break;
            }
            
        }

	}

}
