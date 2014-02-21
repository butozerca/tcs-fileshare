package network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;

import model.Fileshare;

import common.SearchFile;
/**
 * 
 */
public class FileSearchServerThread extends Thread {
	private Socket socket;
	private AddressBlock neighbours;

	public FileSearchServerThread(Socket socket, AddressBlock neighbours) throws IOException {
		this.socket = socket;
		this.neighbours = neighbours;
	}
	
	public void run() {
		try {
			BufferedReader in = new BufferedReader(
					new InputStreamReader(socket.getInputStream()));
			String line = in.readLine();
			FileSearchQuery query = new FileSearchQuery(line);
			System.out.println("Server " + socket.getLocalPort() + " received query: " + query);
			
			PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
			
			ArrayList<String> files = SearchFile.searchFile(query.getFilename(), Fileshare.getSharedPath());
			for(String file : files) {
				out.println(socket.getInetAddress().getHostAddress() + ":" + file.substring(file.indexOf(Fileshare.getSharedPath())));
			}
			if(query.getTtl() > 0) {
				AddressBlock dests = new AddressBlock(neighbours);
				dests.remove(query.getSender());
				ServerAddress newSender = new ServerAddress(socket.getInetAddress().getHostAddress(), socket.getLocalPort(), 0, 1);
				FileSearchQuery newQuery = new FileSearchQuery(newSender, query.getFilename(), query.getTtl()-1);
				FileSearchClient cl = new FileSearchClient(dests, newQuery);
				out.print(cl.getReply());
			}
			out.flush();
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
