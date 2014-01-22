package network;

/**
 * Class describing a query in searching for a file.
 * @author michal2
 *
 */

public class FileSearchQuery {
	private ServerAdress sender;
	private String filename;
	private int ttl;
	
	/**
	 * Constructor. 
	 * @param sender Adress of the sender
	 * @param filename Part of the name of a file
	 * @param ttl How deep do you want this query to go in the network
	 */
	
	public FileSearchQuery(ServerAdress sender, String filename, int ttl) {
		this.sender = sender;
		this.filename = filename;
		this.ttl = ttl;
	}
	
	/**
	 * Constructor opposite to a toString method.
	 * @param query
	 */
	
	public FileSearchQuery(String query) {
		System.out.println(query);
		String[] q = query.split(":");
		this.sender = new ServerAdress(q[0], Integer.parseInt(q[1]), Integer.parseInt(q[2]), Integer.parseInt(q[3]));
		this.filename = q[4];
		this.ttl = Integer.parseInt(q[5]);
	}
	/**
	 * Returns the sender.
	 * @return
	 */
	public ServerAdress getSender() {
		return sender;
	}
	/**
	 * Returns the name of a file which is queried.
	 * @return
	 */
	public String getFilename() {
		return filename;
	}
	
	/**
	 * Returns queries time to live.
	 * @return
	 */
	public int getTtl() {
		return ttl;
	}
	
	/**
	 * Translates this query to a String.
	 */
	
	public String toString() {
		return sender.adress + ":"
				+ sender.destPortSearch + ":"
				+ sender.destPortFile + ":"
				+ sender.destPortAdd + ":"
				+ filename + ":"
				+ ttl;
	}
}
