package network;

public class FileSearchQuery {
	private ServerAdress sender;
	private String filename;
	private int ttl;
	
	public FileSearchQuery(ServerAdress sender, String filename, int ttl) {
		this.sender = sender;
		this.filename = filename;
		this.ttl = ttl;
	}
	
	public FileSearchQuery(String query) {
		String[] q = query.split(":");
		this.sender = new ServerAdress(q[0], Integer.parseInt(q[1]), Integer.parseInt(q[2]));
		this.filename = q[3];
		this.ttl = Integer.parseInt(q[4]);
	}

	public ServerAdress getSender() {
		return sender;
	}

	public String getFilename() {
		return filename;
	}

	public int getTtl() {
		return ttl;
	}
	
	public String toString() {
		return sender.adress + ":"
				+ sender.destPortSearch + ":"
				+ sender.destPortFile + ":"
				+ filename + ":"
				+ ttl;
	}
}
