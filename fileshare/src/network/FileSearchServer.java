package network;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import model.Fileshare;

import common.SearchFile;

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
				Socket server = serverSocket.accept();
				
				BufferedReader in = new BufferedReader(
						new InputStreamReader(server.getInputStream()));
				String line = in.readLine();
				FileSearchQuery query = new FileSearchQuery(line);
				System.out.println("Server " + serverSocket.getLocalPort() + " received query: " + query);
				
				PrintWriter out = new PrintWriter(server.getOutputStream(), true);
				
				ArrayList<String> files = SearchFile.searchFile(query.getFilename(), Fileshare.getSharedPath());
				for(String file : files) {
					out.println(serverSocket.getInetAddress().getHostAddress() + ":" + file.substring(file.indexOf(Fileshare.getSharedPath())));
				}
				if(query.getTtl() > 0) {
					AdressBlock dests = new AdressBlock(neighbours);
					dests.remove(query.getSender());
					ServerAdress newSender = new ServerAdress(serverSocket.getInetAddress().getHostAddress(), serverSocket.getLocalPort());
					FileSearchQuery newQuery = new FileSearchQuery(newSender, query.getFilename(), query.getTtl()-1);
					FileSearchClient cl = new FileSearchClient(dests, newQuery);
					out.print(cl.getReply());
				}
				out.flush();
				server.close();
			} catch (IOException e) {
				e.printStackTrace();
				break;
			}
		}
	}
}
