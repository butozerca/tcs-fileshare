package network;

import java.io.*;
import java.net.*;
import java.util.ArrayList;

import model.AddressBlock;
import model.NetworkManager;
import model.ServerAddress;

import queries.FileSearchQuery;

/**
 * Class provides client part of searching the network for a file protocol.
 * Sender its neighbours if they have the needed file.
 * @author michal2
 */

public class FileSearchClient {

	private NetworkManager manager;
	private FileSearchQuery query;
	private int forbiddenDest;
	/**
	 * Creates the client.
	 * @param neighbours
	 * @param query
	 */
	public FileSearchClient(NetworkManager manager, FileSearchQuery query, int forbiddenDest) {
		this.manager = manager;
		this.query = query;
		this.forbiddenDest = forbiddenDest;
	}
	/**
	 * Starts connection and waits for a response from the server.
	 * @return String describing links to files which match the query.
	 */
	public String getReply() {
		if(query.getTtl() < 1)
			return "";
		StringBuilder reply = new StringBuilder();
		for(ServerAddress addr : manager.getMyBlock()) {
			if(addr.equals(manager.getMyAddress()))
				continue;
			FileSearchQuery instantQuery = new FileSearchQuery(query.getId(), query.getFilename(), 0);
			askAddress(addr.getIP(), addr.getDestPortSearch(), instantQuery, reply);
		}
		for(AddressBlock mesh : manager.getNeighbours()) {
			if(mesh == null || mesh.getId() == forbiddenDest)
				continue;
			ServerAddress addr = mesh.getRandom();
			askAddress(addr.getIP(), addr.getDestPortSearch(), query, reply);
		}
		return reply.toString();
	}
	/**
	 * Starts connection and waits for a response from the server.
	 * @return List of links to files which match the query. 
	 */
	public ArrayList<String> getContent() {
		ArrayList<String> res = new ArrayList<>();
		String reply = this.getReply();
		if(reply.length() == 0)
			return res;
		String[] strings = reply.split("\n");
		for(String s : strings) {
			res.add(s);
		} 
		return res;
	}
	
	private void askAddress(String address, int port, FileSearchQuery query, StringBuilder reply) {
		try {
			Socket client = new Socket(address, port);
			
			PrintWriter out = new PrintWriter(client.getOutputStream(), true);
			out.println(query.toString());
			
			BufferedReader in = new BufferedReader(
					new InputStreamReader(client.getInputStream()));
			String line;
			while((line = in.readLine()) != null)
				reply.append(line + "\n");
			client.close();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) throws IOException {
		/*AddressBlock n0 = new AddressBlock();
		n0.add(new ServerAddress("0.0.0.0", 23300, 0, 1));
		n0.add(new ServerAddress("0.0.0.0", 25301, 0, 1));
		n0.add(new ServerAddress("0.0.0.0", 25302, 0, 1));
		AddressBlock n1 = new AddressBlock();
		n1.add(new ServerAddress("0.0.0.0", 25300, 0, 1));
		AddressBlock n2 = new AddressBlock();
		n2.add(new ServerAddress("0.0.0.0", 25300, 0, 1));
		n2.add(new ServerAddress("0.0.0.0", 25303, 0, 1));
		n2.add(new ServerAddress("0.0.0.0", 25304, 0, 1));
		AddressBlock n3 = new AddressBlock();
		n3.add(new ServerAddress("0.0.0.0", 25302, 0, 1));
		n3.add(new ServerAddress("0.0.0.0", 25305, 0, 1));
		AddressBlock n4 = new AddressBlock();
		n4.add(new ServerAddress("0.0.0.0", 25302, 0, 1));
		AddressBlock n5 = new AddressBlock();
		n5.add(new ServerAddress("0.0.0.0", 25303, 0, 1));
		FileSearchServer[] servers = {
			new FileSearchServer(25300, n0),
			new FileSearchServer(25301, n1),
			new FileSearchServer(25302, n2),
			new FileSearchServer(25303, n3),
			new FileSearchServer(25304, n4),
			new FileSearchServer(25305, n5)
		};
		for(FileSearchServer s : servers) {
			s.setDaemon(true);
			s.start();
		}
		FileSearchQuery que = new FileSearchQuery("0.0.0.0:23300:0:1:File:2");
		AddressBlock hosts = new AddressBlock();
		hosts.add(new ServerAddress("0.0.0.0", 25300, 0, 1));
		FileSearchClient cl = new FileSearchClient(hosts, que);
		ArrayList<String> strings = cl.getContent();
		for(String s : strings) {
			System.out.println(s);
		}*/
	}
}
