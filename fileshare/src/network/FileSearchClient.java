package network;

import java.io.*;
import java.net.*;
import java.util.ArrayList;

public class FileSearchClient {

	private ArrayList<ServerAdress> neighbours;
	private FileSearchQuery query;
	
	public FileSearchClient(ArrayList<ServerAdress> neighbours, FileSearchQuery query) {
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
	
	public static void main(String[] args) throws IOException {
		ArrayList<ServerAdress> n0 = new ArrayList<>();
		n0.add(new ServerAdress("0.0.0.0", 23000));
		n0.add(new ServerAdress("0.0.0.0", 25001));
		n0.add(new ServerAdress("0.0.0.0", 25002));
		ArrayList<ServerAdress> n1 = new ArrayList<>();
		n1.add(new ServerAdress("0.0.0.0", 25000));
		ArrayList<ServerAdress> n2 = new ArrayList<>();
		n2.add(new ServerAdress("0.0.0.0", 25000));
		n2.add(new ServerAdress("0.0.0.0", 25003));
		n2.add(new ServerAdress("0.0.0.0", 25004));
		ArrayList<ServerAdress> n3 = new ArrayList<>();
		n3.add(new ServerAdress("0.0.0.0", 25002));
		n3.add(new ServerAdress("0.0.0.0", 25005));
		ArrayList<ServerAdress> n4 = new ArrayList<>();
		n4.add(new ServerAdress("0.0.0.0", 25002));
		ArrayList<ServerAdress> n5 = new ArrayList<>();
		n5.add(new ServerAdress("0.0.0.0", 25003));
		FileSearchServer[] servers = {
			new FileSearchServer(25000, n0),
			new FileSearchServer(25001, n1),
			new FileSearchServer(25002, n2),
			new FileSearchServer(25003, n3),
			new FileSearchServer(25004, n4),
			new FileSearchServer(25005, n5)
		};
		for(FileSearchServer s : servers) {
			s.setDaemon(true);
			s.start();
		}
		FileSearchQuery que = new FileSearchQuery("0.0.0.0:23000:user.txt:3");
		ArrayList<ServerAdress> hosts = new ArrayList<>();
		hosts.add(new ServerAdress("0.0.0.0", 25000));
		FileSearchClient cl = new FileSearchClient(hosts, que);
		System.out.println(cl.getReply());
	}
}
