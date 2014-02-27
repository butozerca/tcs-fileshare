package network;

import java.io.*;
import java.net.ServerSocket;

import model.NetworkManager;
/**
 * Class provides server part of searching the network for a file protocol.
 * Aside from just responding to the client, it also relays the query further in the network.
 * @author michal2
 */
public class FileSearchServer extends Thread {
	private ServerSocket serverSocket;
	private NetworkManager manager;
	/**
	 * Constructor.
	 * @param port
	 * @param neighbours
	 * @throws IOException
	 */
	public FileSearchServer(int port, NetworkManager manager) throws IOException {
		this.serverSocket = new ServerSocket(port);
		this.manager = manager;
	}
	/**
	 * Starts the server.
	 */
	public void run() {
		while (true) {
			try {
				new FileSearchServerThread(serverSocket.accept(), manager).start();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
