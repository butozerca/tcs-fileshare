package network;

import java.io.IOException;
import java.net.ServerSocket;

import model.NetworkManager;
/**
 * Class provides the server part of exchanging an address. Every user is associated with one of these.
 * It awaits connection and agrees to exchange of addresses between the initiator and user.
 * @author michal2
 *
 */
public class AddNodeServer extends Thread {
	
	private ServerSocket serverSocket;
	private NetworkManager manager;
	/**
	 * 
	 */
	public AddNodeServer(NetworkManager manager) throws IOException {
		this.manager = manager;
		this.serverSocket = new ServerSocket(this.manager.getMyAddress().getDestPortAdd());
	}
	/**
	 * Starts the server.
	 */
	public void run() {
		while (true) {
			 try {
				new AddNodeServerThread(serverSocket.accept(), manager).start();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
