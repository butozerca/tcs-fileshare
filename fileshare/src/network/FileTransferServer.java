package network;

import java.net.*;
import java.io.*;
/**
 * Class provides server part of sending a file over the network.
 * @author michal2
 */
public class FileTransferServer extends Thread {
	
	private ServerSocket serverSocket;
	/**
	 * Constructor.
	 * @param port Port with which the server should be bound to. 
	 * @throws IOException
	 */
	public FileTransferServer(int port) throws IOException {
		this.serverSocket = new ServerSocket(port);
	}
	/**
	 * Starts the server.
	 */
	public void run() {
		while (true) {
			 try {
				new FileTransferServerThread(serverSocket.accept()).start();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
