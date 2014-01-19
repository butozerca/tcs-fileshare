package network;

import java.io.*;
import java.net.*;
import java.util.ArrayList;

public class FileSearchClient {

	private AdressBlock neighbours;
	private FileSearchQuery query;
	
	public FileSearchClient(AdressBlock neighbours, FileSearchQuery query) {
		this.neighbours = neighbours;
		this.query = query;
	}
	
	public String getReply() {
		StringBuilder reply = new StringBuilder();
		for(ServerAdress dest : neighbours) {
			try {
				Socket client = new Socket(dest.adress, dest.destPort);
				
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
		AdressBlock n0 = new AdressBlock();
		n0.add(new ServerAdress("0.0.0.0", 23300));
		n0.add(new ServerAdress("0.0.0.0", 25301));
		n0.add(new ServerAdress("0.0.0.0", 25302));
		AdressBlock n1 = new AdressBlock();
		n1.add(new ServerAdress("0.0.0.0", 25300));
		AdressBlock n2 = new AdressBlock();
		n2.add(new ServerAdress("0.0.0.0", 25300));
		n2.add(new ServerAdress("0.0.0.0", 25303));
		n2.add(new ServerAdress("0.0.0.0", 25304));
		AdressBlock n3 = new AdressBlock();
		n3.add(new ServerAdress("0.0.0.0", 25302));
		n3.add(new ServerAdress("0.0.0.0", 25305));
		AdressBlock n4 = new AdressBlock();
		n4.add(new ServerAdress("0.0.0.0", 25302));
		AdressBlock n5 = new AdressBlock();
		n5.add(new ServerAdress("0.0.0.0", 25303));
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
		FileSearchQuery que = new FileSearchQuery("0.0.0.0:23300:File:2");
		AdressBlock hosts = new AdressBlock();
		hosts.add(new ServerAdress("0.0.0.0", 25300));
		FileSearchClient cl = new FileSearchClient(hosts, que);
		ArrayList<String> strings = cl.getContent();
		for(String s : strings) {
			System.out.println(s);
		}
	}
}
