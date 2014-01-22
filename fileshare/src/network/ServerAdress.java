package network;

import java.io.Serializable;

/**
 * Class describing a user's adress in the network. It has his ip adress and port numbers on which his servers are set on.
 * @author michal2
 *
 */
public class ServerAdress implements Serializable {
	
	private static final long serialVersionUID = -7204732772252817997L;
	public String adress;
	public int destPortSearch;
	public int destPortFile;
	public int destPortAdd;
	
	/**
	 * Constructor. 
	 * @param adress Ip adress
	 * @param destPortSearch FileSearchServer port
	 * @param destPortFile FileTransferServer port
	 * @param destPortAdd AddNodeServer port
	 */
	public ServerAdress(String adress, int destPortSearch, int destPortFile, int destPortAdd){
		this.adress = adress;
		this.destPortSearch = destPortSearch;
		this.destPortFile = destPortFile;
		this.destPortAdd = destPortAdd;
	}
	/**
	 * Constructor opposite to toString method.
	 * @param line
	 */
	public ServerAdress(String line) {
		String[] ad = line.split(":");
		this.adress = ad[0];
		this.destPortSearch = Integer.parseInt(ad[1]);
		this.destPortFile = Integer.parseInt(ad[2]);
		this.destPortAdd = Integer.parseInt(ad[3]);
	}
	/**
	 * Returns true if two addresses are equal. False otherwise.
	 */
	public boolean equals(Object o){
		ServerAdress other = (ServerAdress)o;
		if(this.adress.equals(other.adress) && this.destPortSearch == other.destPortSearch)
			return true;
		else 
			return false;
	}
	/**
	 * Parses this class into a string.
	 * @param line
	 */
	public String toString(){
		return adress + ":" 
				+ destPortSearch + ":"
				+ destPortFile + ":"
				+ destPortAdd;
	}
}
