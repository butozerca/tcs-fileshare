package network;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class FileSearchClient extends Client {

	public FileSearchClient(String serverAddress, int destPort, String query) {
		super(serverAddress, destPort, query);
	}

	@Override
	public ArrayList<URL> getContent() {
		ArrayList<URL> res = new ArrayList<>();
		String reply = this.getReply();
		String[] urls = reply.split("\n");
		for(String u : urls) {
			try {
				res.add(new URL(u));
			} catch (MalformedURLException e) {
				System.out.println("Malformed URL: " + u);
				e.printStackTrace();
			}
		}
		return res;
	}
	
	public static void main(String[] args) {
		try {
			 Server serv = new Server(23443);
			 serv.setDaemon(true);
			 serv.start();
			 FileSearchClient cl = new FileSearchClient("localhost", 23443, "fsq://pli");
			 System.out.println(cl.getContent());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
