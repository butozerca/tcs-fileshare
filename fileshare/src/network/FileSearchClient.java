package network;

import java.io.*;
import java.net.*;
import java.util.ArrayList;

/**
 * Class provides client part of searching the network for a file protocol.
 * Sender its neighbours if they have the needed file.
 * @author michal2
 */

public class FileSearchClient {

	private AddressBlock neighbours;
	private FileSearchQuery query;
	/**
	 * Creates the client.
	 * @param neighbours
	 * @param query
	 */
	public FileSearchClient(AddressBlock neighbours, FileSearchQuery query) {
		this.neighbours = neighbours;
		this.query = query;
	}
	/**
	 * Starts connection and waits for a response from the server.
	 * @return String describing links to files which match the query.
	 */
	public String getReply() {
		StringBuilder reply = new StringBuilder();
		for(ServerAddress dest : neighbours) {
			try {
				Socket client = new Socket(dest.address, dest.destPortSearch);
				
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

	public static void main(String[] args) throws IOException {
		AddressBlock n0 = new AddressBlock();
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
		}
	}
}
