package network;

import java.io.Serializable;

/**
 * Class describing a user's address in the network. It has his ip address and port numbers on which his servers are set on.
 * @author michal2
 *
 */
public class ServerAddress implements Serializable {
	
	private static final long serialVersionUID = -7204732772252817997L;
	public String address;
	public int destPortSearch;
	public int destPortFile;
	public int destPortAdd;
	
	/**
	 * Constructor. 
	 * @param adress Ip address
	 * @param destPortSearch FileSearchServer port
	 * @param destPortFile FileTransferServer port
	 * @param destPortAdd AddNodeServer port
	 */
	public ServerAddress(String address, int destPortSearch, int destPortFile, int destPortAdd) {
		this.address = address;
		this.destPortSearch = destPortSearch;
		this.destPortFile = destPortFile;
		this.destPortAdd = destPortAdd;
	}
	/**
	 * Constructor opposite to toString method.
	 * @param line
	 */
	public ServerAddress(String line) {
		String[] ad = line.split(":");
		this.address = ad[0];
		this.destPortSearch = Integer.parseInt(ad[1]);
		this.destPortFile = Integer.parseInt(ad[2]);
		this.destPortAdd = Integer.parseInt(ad[3]);
	}

	/**
	 * Calculates hash code of this instance.
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((address == null) ? 0 : address.hashCode());
		result = prime * result + destPortAdd;
		result = prime * result + destPortFile;
		result = prime * result + destPortSearch;
		return result;
	}

	/**
	 * Returns true if two addresses are equal. False otherwise.
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof ServerAddress))
			return false;
		ServerAddress other = (ServerAddress) obj;
		if (address == null) {
			if (other.address != null)
				return false;
		} else if (!address.equals(other.address))
			return false;
		if (destPortAdd != other.destPortAdd)
			return false;
		if (destPortFile != other.destPortFile)
			return false;
		if (destPortSearch != other.destPortSearch)
			return false;
		return true;
	}
	
	/**
	 * Parses this class into a string.
	 */
	public String toString() {
		return address + ":" 
				+ destPortSearch + ":"
				+ destPortFile + ":"
				+ destPortAdd;
	}
}
