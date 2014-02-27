package network;

import java.io.IOException;
import java.net.ServerSocket;

import model.User;
/**
 * Class provides the server part of exchanging an address. Every user is associated with one of these.
 * It awaits connection and agrees to exchange of addresses between the initiator and user.
 * @author michal2
 *
 */
public class AddNodeServer extends Thread {
	
	private ServerSocket serverSocket;
	private User user;
	/**
	 * 
	 */
	public AddNodeServer(User user) throws IOException {
		this.user = user;
		this.serverSocket = new ServerSocket(this.user.getManager().getMyAddress().getDestPortAdd());
	}
	/**
	 * Starts the server.
	 */
	public void run() {
		while (true) {
			 try {
				new AddNodeServerThread(serverSocket.accept(), user).start();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
