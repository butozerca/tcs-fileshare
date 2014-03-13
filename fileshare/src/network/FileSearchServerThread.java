package network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;

import queries.FileSearchQuery;

import model.Fileshare;
import model.NetworkManager;

import common.SearchFile;
/**
 * 
 */
public class FileSearchServerThread extends Thread {
	private Socket socket;
	private NetworkManager manager;

	public FileSearchServerThread(Socket socket, NetworkManager manager) throws IOException {
		this.socket = socket;
		this.manager = manager;
	}
	
	public void run() {
		try {
			BufferedReader in = new BufferedReader(
					new InputStreamReader(socket.getInputStream()));
			String line = in.readLine();
			FileSearchQuery query = new FileSearchQuery(line);
			
			PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
			ArrayList<String> files = SearchFile.searchFile(query.getFilename(), Fileshare.getSharedPath());
			for(String file : files) {
				out.println(manager.getMyAddress().getIP() + ":" + manager.getMyAddress().getDestPortFile() +
						":" + file.substring(file.indexOf(Fileshare.getSharedPath())));
			}
			if(query.getTtl() > 0) {
				FileSearchQuery newQuery = new FileSearchQuery(manager.getMyBlock().getId(), query.getFilename(), query.getTtl()-1);
				FileSearchClient cl = new FileSearchClient(manager, newQuery, query.getId());
				out.print(cl.getReply());
			}
			out.flush();
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
