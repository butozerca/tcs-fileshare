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
				//TODO Zapewnic obsluge protokolow
				DataOutputStream out = new DataOutputStream(server.getOutputStream());
				out.writeUTF("ftp://176.23.34.2:34533/plik.txt\n" + 
						"ftp://176.26.64.42:12553/tmp/plik.txt");
				server.close();
			} catch (IOException e) {
				e.printStackTrace();
				break;
			}
		}
	}
	
	public static void main(String args[]) {
		
	}
}
