package network;
import java.io.*;
import java.net.*;

public class Server extends Thread {

	/**
	 * Klasa odpowiedzialna za przyjmowanie requestow i wykonywanie czynnosci z
	 * tym zwiazanych.
	 */

	private ServerSocket serverSocket;

	public Server(int port) throws IOException {
		this.serverSocket = new ServerSocket(port);
	}

	public void run() {
		while (true) {
			try {
				Socket server = serverSocket.accept();
				DataInputStream in = new DataInputStream(server.getInputStream());
				String query = in.readUTF();
				System.out.println("Server received query: " + query);
				DataOutputStream out = new DataOutputStream(server.getOutputStream());
				out.writeUTF("This is my 1 response\n" + "And 2 response\n");
				server.close();
			} catch (IOException e) {
				e.printStackTrace();
				break;
			}
		}
	}
	
	public static void main(String args[]) {
		try {
			 Server serv = new Server(23443);
			 serv.setDaemon(true);
			 serv.start();
			 Client cl = new Client("localhost", 23443, "what's up?");
			 System.out.println("Client got:\n" + cl.getReply());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
