package network;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.Socket;

import model.User;

/**
 * Class provides client part of exchanging an adress in our network.
 * Sender adds the target to his neighbour list and reciever notes sender's adress.
 * @author michal2
 *
 */

public class AddNodeClient {
	
	private String destAddress;
	private int destPort;
	private User user;
	/**
	 * Creates a client for this particular connection.
	 * @param destAddress Destination adress.
	 * @param destPort Destination port.
	 * @param user User sending the message.
	 * @throws MalformedURLException
	 */
	public AddNodeClient(String destAddress, int destPort, User user) throws MalformedURLException {
		this.destAddress = destAddress;
		this.destPort = destPort;
		this.user = user;
	}
	/**
	 * Opens the connection between sender and destination.
	 */
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
