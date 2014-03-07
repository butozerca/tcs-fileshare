package network;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.MalformedURLException;
import java.net.Socket;

import queries.PhaseQuery;

/**
 * Class provides client part of exchanging an adress in our network.
 * Sender adds the target to his neighbour list and reciever notes sender's adress.
 * @author michal2
 *
 */

public class AddNodeClient {
	
	private PhaseQuery query;
	private String IP;
	private int port;
	private int timeout = 0;
	/**
	 * Creates a client for this particular connection.
	 * @param destAddress Destination adress.
	 * @param destPort Destination port.
	 * @param user User sending the message.
	 * @throws MalformedURLException
	 */
	public AddNodeClient(PhaseQuery pquery, String IP, int port){
		this.query = pquery;
		this.IP = IP;
		this.port = port;
	}
	/**
	 * Opens the connection between sender and destination.
	 */
	public String sendQuery() {
		try {
			
			Socket client = new Socket();
			client.connect(new InetSocketAddress(IP, port), timeout);
			
			PrintWriter out = new PrintWriter(client.getOutputStream(), true);
			out.println(query.toString());
			
			BufferedReader in = new BufferedReader(
				new InputStreamReader(client.getInputStream()));
			
			String line = in.readLine();
			System.out.println("dostalem odpowiedz " + line);
			
			client.close();
			return line;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public void setConnectionTimeout(int timeout) {
		if(timeout > 0)
			this.timeout = timeout;
	}
}
