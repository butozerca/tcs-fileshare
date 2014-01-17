package network;
import java.io.IOException;
import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {

	/**
	 * Klasa odpowiedzialna za wysylanie requestow i odbieranie danych
	 */

	private String serverAddress;
	private int destPort;
	private String query;

	public Client(String serverAddress, int destPort, String query) {
		this.serverAddress = serverAddress;
		this.destPort = destPort;
		this.query = query;
	}

	public String getReply() {
		String reply = null;
		try {
			Socket client = new Socket(serverAddress, destPort);
			DataOutputStream out = new DataOutputStream(client.getOutputStream());
			out.writeUTF(query);
			DataInputStream in = new DataInputStream(client.getInputStream());
			reply = in.readUTF();
			client.close();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return reply;
	}
	
	public static void main(String args[]){
		
	//	Client cli = new Client();
		
	}
	
}
