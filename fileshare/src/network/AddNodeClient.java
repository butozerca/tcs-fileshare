package network;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.Socket;

import model.User;

public class AddNodeClient {
	
	private String destAddress;
	private int destPort;
	private User user;
	
	public AddNodeClient(String destAddress, int destPort, User user) throws MalformedURLException {
		this.destAddress = destAddress;
		this.destPort = destPort;
		this.user = user;
	}
	
	public void addNode() {
		try {
			Socket client = new Socket(destAddress, destPort);
			
			PrintWriter out = new PrintWriter(client.getOutputStream(), true);
			out.println(user.getManager().getAdress().toString());
			
			BufferedReader in = new BufferedReader(
				new InputStreamReader(client.getInputStream()));
			
			String line = in.readLine();
			System.out.println(line);
			user.getManager().getNeighbours().add(new ServerAdress(line));
			
			client.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
