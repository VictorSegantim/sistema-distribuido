package algunsprojetos;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;



public class Professor implements Runnable {
		//endereço ip local
		private static final String SERVER_ADDRESS = "127.0.0.1";
		private ClientSocket clientSocket;
		private Scanner scanner;
		
		public Professor() {
			scanner = new Scanner(System.in);
		}
		
		public void start() throws IOException {
			try {
			clientSocket = new ClientSocket(new Socket(SERVER_ADDRESS, ChatServer.PORT));
			
			System.out.println("Cliente conectado ao servidor em " + SERVER_ADDRESS + ":" + ChatServer.PORT);
			
			new Thread(this).start();
			messageLoop();
			} finally {
				clientSocket.close();
			}
		}
		
		
		@Override
		public void run() {
			String msg;
			while((msg = clientSocket.getMessage()) != null)
			System.out.println("--------------------------------\n");
			System.out.printf("ALUNO: %s\n", msg);
		}
		
		private void messageLoop() throws IOException {
			String RA, Nome;
			float Nota1, Nota2, Nota3;
			String Curso;
			int verifica = 0;
			int valRequi = 0;
			int escolhaRequi;
			
			System.out.println("--------------------------\n");
			System.out.println("Bem vindo a Universidade Anhembi Morumbi!");
			
			while (verifica != 1) {
				
				Pessoa aluno = new Pessoa();
				
				System.out.println("--------------------------------\n");
				System.out.println("\nDigite o Nome do seu aluno: ");
				Nome = scanner.nextLine();
                System.out.println("\nDigite o RA do seu aluno: ");
                RA = scanner.nextLine();
                System.out.println("\nDigite a Nota 1 do seu aluno: ");
                Nota1 = scanner.nextFloat();
                System.out.println("\nDigite a Nota 2 do seu aluno: ");
                Nota2 = scanner.nextFloat();
                System.out.println("\nDigite a Nota 3 do seu aluno: ");
                Nota3 = scanner.nextFloat();
                scanner.nextLine();
                System.out.println("\nDigite o curso do seu aluno: ");
                Curso = scanner.nextLine();
                
                System.out.println("--------------------------------\n");
                
                aluno.setAluno(Nome, RA, Nota1, Nota2, Nota3, Curso);
                aluno.mostraAluno();
                
                System.out.println("--------------------------------\n");
                clientSocket.sendMsg("--> CADASTRO --> " + Nome + " / RA: " + RA + " / MATÉRIA: "+ Curso + " / NOTA1: " + Nota1 + " / NOTA2: " + Nota2 + " / NOTA3: " + Nota3);
                
                do {
                	 System.out.println("--------------------------------\n");
                	 System.out.println("O que deseja que o sistema informe ao aluno? Digite o número:\n"
                     		+ "1 --> Média\n"
                     		+ "2 --> Boleto\n"
                     		+ "3 --> Matrículas atrasadas\n"
                     		+ "4 --> Disciplinas atuais\n"
                     		+ "5 --> Ambiente de estudo");
                	 escolhaRequi = scanner.nextInt();
                	 scanner.nextLine();
                	 
                	 if(escolhaRequi == 1) {
                		 float mediaToServer = aluno.mediaNotas();
                		 System.out.println(" / A média desse aluno é --> " + mediaToServer);
                		 clientSocket.sendMsg(" / A média desse aluno é --> " + mediaToServer);
                		 
                	 }else if(escolhaRequi == 2){
                		 Long boletoToServer = aluno.boletoAleatorio();
                		 System.out.println(" / O boleto gerado para a rematrícula é --> " + boletoToServer);
                		 clientSocket.sendMsg(" / O boleto gerado para a rematrícula é --> " + boletoToServer);
                		 
                	 }else if(escolhaRequi == 3) {
                		 System.out.println("Informe os mêses atrasados (valor/mês --> R$ 300,00)");
                		 int valorAtrasado = scanner.nextInt();
                		 scanner.nextLine();
                		 
                		 System.out.println(" / O valor restante a ser pago é de --> R$" + (valorAtrasado * 300) + ",00 Reais.");
                		 clientSocket.sendMsg(" / O valor restante a ser pago é de --> R$" + (valorAtrasado * 300) + ",00 Reais.");
                	 }else if(escolhaRequi == 4){
                		 System.out.println("Informe as disciplinas atuais desse aluno: ");
                		 String disciplinas = scanner.nextLine();
                		 
                		 System.out.println(" / As disciplinas cadastradas são ---> " + disciplinas);
                		 clientSocket.sendMsg(" / As disciplinas cadastradas são ---> " + disciplinas);
                	 }else if(escolhaRequi == 5){
                		 System.out.println("Informe período, endereço, o prédio e a sala nos itens a seguir:\n");
                		 
                		 System.out.println("Informe o período: ");
                		 String periodo = scanner.nextLine();
                		 
                		 System.out.println("Informe o endereço: ");
                		 String endereco = scanner.nextLine();
                		 
                		 System.out.println("Informe o prédio: ");
                		 String predio = scanner.nextLine();
                		 
                		 System.out.println("Informe a sala: ");
                		 String sala = scanner.nextLine();
                		 
                		 System.out.println(" / Período - " + periodo + " / Endereco - " + endereco + " / Prédio - " + predio + " / Sala - " + sala + " /");
                		 clientSocket.sendMsg(" / Período - " + periodo + " / Endereco - " + endereco + " / Prédio - " + predio + " / Sala - " + sala + " /");
                		 
                	 }else {
                		 System.out.println("--------------------------------\n");
                		 System.out.println("Nenhuma requisição foi feita!");
                	 }
                	 
                	 System.out.println("--------------------------------\n");
                	 System.out.println("Deseja realizar mais requisições? (0 - Não / 1 - Sim) --> ");
                	 valRequi = scanner.nextInt();
                	 scanner.nextLine();
                } while(valRequi != 0);
               
                System.out.println("--------------------------------\n");
                System.out.println("Deseja enviar uma mensagem para todos os alunos? (0 - Não / 1 - Sim) ---> ");
                int perguntaMsgTodos = scanner.nextInt();
                scanner.nextLine();
                
                if(perguntaMsgTodos == 1) {
                	int verificaMsgTodos = 1;
                	while (verificaMsgTodos == 1) {
                		System.out.println("Informe a mensagem:\n");
                    	String mensagemTodos = scanner.nextLine();
                    	
                    	System.out.println("\nA mensagem enviada foi:\n" + mensagemTodos);
                    	clientSocket.sendMsg(" / PROFESSOR: " + mensagemTodos);
                    	
                    	System.out.println("\nDeseja continuar enviando mensagens? (0 - Não / 1 - Sim)");
                    	verificaMsgTodos = scanner.nextInt();
                    	scanner.nextLine();
                	}
                	
                }else {
                	System.out.println("Nenhuma mensagem foi enviada!\n");
                }
                
                System.out.println("--------------------------------\n");
                System.out.println("Deseja finalizar o cadastro? (0 - Não / 1 - Sim) --> ");
                verifica = scanner.nextInt();
                scanner.nextLine();
				
			}
			System.out.println("--------------------------------\n");
			System.out.println("\nCadastro finalizado");
		}
	
		public static void main(String[] args) {
			
			try {
				Professor client = new Professor();
				client.start();
			} catch (IOException ex) {
				System.out.println("--------------------------------\n");
				System.out.println("Erro ao iniciar cliente: " + ex.getMessage());
			}
			System.out.println("--------------------------------\n");
			System.out.println("Professor finalizado!");
		}
}
