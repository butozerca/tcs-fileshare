package network;

import java.net.*;
import java.io.*;

public class FileTransferServer extends Thread {
	
	private ServerSocket serverSocket;
	
	public FileTransferServer(int port) throws IOException {
		this.serverSocket = new ServerSocket(port);
	}
	
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
