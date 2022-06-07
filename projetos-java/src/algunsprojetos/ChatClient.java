package algunsprojetos;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;


public class ChatClient implements Runnable {
		//endereço ip local
		private static final String SERVER_ADDRESS = "127.0.0.1";
		private ClientSocket clientSocket;
		private Scanner scanner;
		
		public ChatClient() {
			scanner = new Scanner(System.in);
		}
		
		
		public void start() throws IOException {
			try {
			clientSocket = new ClientSocket(new Socket(SERVER_ADDRESS, ChatServer.PORT));
			
			System.out.println("--------------------------------\n");
			System.out.println("\nCliente conectado ao servidor em " + SERVER_ADDRESS + ":" + ChatServer.PORT);
			
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
			System.out.printf(msg);
		}
		
		
		private void messageLoop() throws IOException {
            String msg;
            
         
            
            
            do {
            	System.out.println(clientSocket);
            	
            	do {
            	 System.out.println("--------------------------------\n");
               	 System.out.println("Aguarde o professor digitar suas informações!\n"
               	 		+ "Se desejar sair do grupo a qualquer momento, digite 'sair'\n");
               	 msg = scanner.nextLine();
               	 
               	 
               	
               } while(!msg.equalsIgnoreCase("sair"));
            	
            	
                clientSocket.sendMsg(msg);

            }while(!msg.equalsIgnoreCase("sair"));
            
        }
	
		public static void main(String[] args) {
			
			try {
				ChatClient client = new ChatClient();
				client.start();
			} catch (IOException ex) {
				System.out.println("--------------------------------\n");
				System.out.println("Erro ao iniciar cliente: " + ex.getMessage());
			}
			System.out.println("--------------------------------\n");
			System.out.println("Cliente finalizado!");
		}
}
