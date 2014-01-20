package network;

import java.io.Serializable;

public class ServerAdress implements Serializable {
	
	private static final long serialVersionUID = -7204732772252817997L;
	public String adress;
	public int destPortSearch;
	public int destPortFile;
	
	public ServerAdress(String adress, int destPortSearch, int destPortFile){
		this.adress = adress;
		this.destPortSearch = destPortSearch;
		this.destPortFile = destPortFile;
	}

	public boolean equals(Object o){
		ServerAdress other = (ServerAdress)o;
		if(this.adress.equals(other.adress) && this.destPortSearch == other.destPortSearch)
			return true;
		else 
			return false;
	}
	
	public String toString(){
		return new String(adress + " " + destPortSearch);
	}
}
