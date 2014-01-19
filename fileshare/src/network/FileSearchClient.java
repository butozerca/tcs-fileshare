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
	
	public ArrayList<URL> getContent() {
		ArrayList<URL> res = new ArrayList<>();
		String reply = this.getReply();
		String[] urls = reply.split("\n");
		for(String u : urls) {
			System.out.println(u.length());
			try {
				res.add(new URL(u));
			} catch (MalformedURLException e) {
				System.out.println("Malformed URL: \"" + u + "\"");
				e.printStackTrace();
			}
		}
		return res;
	}

	public static void main(String[] args) throws IOException {
		ArrayList<ServerAdress> n0 = new ArrayList<>();
		n0.add(new ServerAdress("0.0.0.0", 33000));
		n0.add(new ServerAdress("0.0.0.0", 35001));
		n0.add(new ServerAdress("0.0.0.0", 35002));
		ArrayList<ServerAdress> n1 = new ArrayList<>();
		n1.add(new ServerAdress("0.0.0.0", 35000));
		ArrayList<ServerAdress> n2 = new ArrayList<>();
		n2.add(new ServerAdress("0.0.0.0", 35000));
		n2.add(new ServerAdress("0.0.0.0", 35003));
		n2.add(new ServerAdress("0.0.0.0", 35004));
		ArrayList<ServerAdress> n3 = new ArrayList<>();
		n3.add(new ServerAdress("0.0.0.0", 35002));
		n3.add(new ServerAdress("0.0.0.0", 35005));
		ArrayList<ServerAdress> n4 = new ArrayList<>();
		n4.add(new ServerAdress("0.0.0.0", 35002));
		ArrayList<ServerAdress> n5 = new ArrayList<>();
		n5.add(new ServerAdress("0.0.0.0", 35003));
		FileSearchServer[] servers = {
			new FileSearchServer(35000, n0),
			new FileSearchServer(35001, n1),
			new FileSearchServer(35002, n2),
			new FileSearchServer(35003, n3),
			new FileSearchServer(35004, n4),
			new FileSearchServer(35005, n5)
		};
		for(FileSearchServer s : servers) {
			s.setDaemon(true);
			s.start();
		}
		FileSearchQuery que = new FileSearchQuery("0.0.0.0:33000:File:2");
		ArrayList<ServerAdress> hosts = new ArrayList<>();
		hosts.add(new ServerAdress("0.0.0.0", 35000));
		FileSearchClient cl = new FileSearchClient(hosts, que);
		ArrayList<URL> urls = cl.getContent();
		for(URL u : urls) {
			System.out.println(u);
		}
	}
}
