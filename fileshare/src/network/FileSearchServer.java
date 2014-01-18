package network;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import common.SearchFile;

public class FileSearchServer extends Thread {
	private ServerSocket serverSocket;
	private ArrayList<ServerAdress> neighbours;

	public FileSearchServer(int port, ArrayList<ServerAdress> neighbours) throws IOException {
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
				System.out.println("Server received query: " + query);
				
				PrintWriter out = new PrintWriter(server.getOutputStream(), true);
				//TODO podpiac sharowany folder do zapytania
				ArrayList<String> files = SearchFile.searchFile(query.getFilename(), "fileshare/src/");
				for(String file : files) {
					out.println("ftp://" + serverSocket.getInetAddress().getHostAddress() + file);
				}
				if(query.getTtl() > 0) {
					ArrayList<ServerAdress> dests = new ArrayList<>(neighbours);
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
