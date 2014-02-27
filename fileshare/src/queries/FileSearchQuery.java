package queries;

/**
 * Class describing a query used in searching for a file protocol. The query stores two values:
 * filename, which is a partial name of a file to be searched for and ttl, which indicates how deep 
 * this query is forwarded into the network.
 * @author patryk
 */

public class FileSearchQuery {

	private int id;
	private String filename;
	private int ttl;
	
	/**
	 * Constructs a query with given filename and ttl.
	 * @param filename Part of the name of a file to be searched for.
	 * @param ttl How deep you want this query to go into the network
	 */
	
	public FileSearchQuery(int id, String filename, int ttl) {
		this.id = id;
		this.filename = filename;
		this.ttl = ttl;
	}
	
	/**
	 * Constructor opposite to a toString method.
	 * @param query Query given as one String with filename and ttl separated with a colon.
	 */
	public FileSearchQuery(String query) {
		String[] q = query.split(":");
		this.id = Integer.parseInt(q[0]);
		this.filename = q[1];
		this.ttl = Integer.parseInt(q[2]);
	}

	/**
	 * Returns the name of a file which is queried.
	 * @return the name of a file which is queried.
	 */
	public String getFilename() {
		return filename;
	}
	
	/**
	 * Returns time to live of this query.
	 * @return time to live of this query.
	 */
	public int getTtl() {
		return ttl;
	}
	
	/**
	 * Returns id of the client's AddressBlock.
	 * @return id of the client's AddressBlock.
	 */
	public int getId() {
		return id;
	}
	
	/**
	 * Translates this query to a String.
	 * @return String representation of this query.
	 */
	public String toString() {
		return id + ":" + filename + ":" + ttl;
	}
}
