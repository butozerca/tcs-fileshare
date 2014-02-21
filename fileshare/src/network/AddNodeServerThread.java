package network;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import model.User;

public class AddNodeServerThread extends Thread {

	private Socket socket = null;
	private User user;
	
	public AddNodeServerThread(Socket socket, User user) {
		this.socket = socket;
		this.user = user;
	}
	
	public void run() {
		try {
			BufferedReader in = new BufferedReader(
					new InputStreamReader(socket.getInputStream()));
			String line = in.readLine();
			System.out.println(line);
			user.getManager().getNeighbours().add(new ServerAddress(line));
			
			PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
			out.println(user.getManager().getAdress());
			
			socket.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
