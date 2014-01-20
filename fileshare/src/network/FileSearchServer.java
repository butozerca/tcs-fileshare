package network;

import java.io.*;
import java.net.ServerSocket;

public class FileSearchServer extends Thread {
	private ServerSocket serverSocket;
	private AdressBlock neighbours;

	public FileSearchServer(int port, AdressBlock neighbours) throws IOException {
		this.serverSocket = new ServerSocket(port);
		this.neighbours = neighbours;
	}
	
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
