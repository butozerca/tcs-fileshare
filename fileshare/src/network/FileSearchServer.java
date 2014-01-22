package network;

import java.io.*;
import java.net.ServerSocket;
/**
 * Class provides server part of searching the network for a file protocol.
 * Aside from just responding to the client, it also relays the query further in the network.
 * @author michal2
 */
public class FileSearchServer extends Thread {
	private ServerSocket serverSocket;
	private AdressBlock neighbours;
	/**
	 * Constructor.
	 * @param port
	 * @param neighbours
	 * @throws IOException
	 */
	public FileSearchServer(int port, AdressBlock neighbours) throws IOException {
		this.serverSocket = new ServerSocket(port);
		this.neighbours = neighbours;
	}
	/**
	 * Starts the server.
	 */
	public void run() {
		while (true) {
			try {
				new FileSearchServerThread(serverSocket.accept(), neighbours).start();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
