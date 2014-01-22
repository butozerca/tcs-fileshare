package network;

import java.io.IOException;
import java.net.ServerSocket;

import model.User;

public class AddNodeServer extends Thread {
	
	private ServerSocket serverSocket;
	private User user;
	
	public AddNodeServer(User user) throws IOException {
		this.user = user;
		this.serverSocket = new ServerSocket(this.user.getManager().getAdress().destPortAdd);
	}
	
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
