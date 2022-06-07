package algunsprojetos;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.Iterator;
import java.util.LinkedList;



public class ChatServer {
	
	//número da porta utilizado pelo servidor
	public static final int PORT = 4000;
	//declarar um socket, esse socket vai ficar escutando a porta
	private ServerSocket serverSocket;
	private final List<ClientSocket> clients = new LinkedList<>();
	
	//criação de um método que não retorna nenhum valor
	public void start() throws IOException {
		System.out.println("--------------------------------\n");
		System.out.println("Servidor iniciado na porta " + PORT);
		//instancia server socket, solicita um número de porta
		serverSocket = new ServerSocket(PORT);
		clientConnectionLoop();
	}
	
	//responsavel por aguardar conexões dos clientes
	private void clientConnectionLoop() throws IOException {
		while(true) {
			//aguarda um cliente conectar, assim, criando um socket local
			ClientSocket clientSocket = new ClientSocket(serverSocket.accept());
			clients.add(clientSocket);
			//lambda
			new Thread(() -> clientMessageLoop(clientSocket)).start();
			
		}
	}
	
	private void clientMessageLoop(ClientSocket clientSocket) {
		String msg;
		try {
		while((msg = clientSocket.getMessage()) != null) {
			if("sair".equalsIgnoreCase(msg))
				return;
			System.out.println("--------------------------\n");
			System.out.printf("\nPROFESSOR: %s: %s\n", clientSocket.getRemoteSocketAddress(), msg);
			sendMsgToAll(clientSocket, msg);
		}
		} finally {
			clientSocket.close();
		}
	}
	
	private void sendMsgToAll(ClientSocket sender, String msg) {
		Iterator<ClientSocket> iterator = clients.iterator();
		while(iterator.hasNext()) {
			ClientSocket clientSocket = iterator.next();
			if(!sender.equals(clientSocket))
			if(!clientSocket.sendMsg(msg)) {
				iterator.remove();
			}
		}
	}
	
	public static void main(String[] args) {
		try {
			ChatServer server = new ChatServer();
			server.start();
		} catch (IOException ex) {
			System.out.println("--------------------------------\n");
			System.out.println("Erro ao iniciar o servidor: " + ex.getMessage());
		}
		
		System.out.println("--------------------------------\n");
		System.out.println("Servidor finalizado");
	}
	
}
