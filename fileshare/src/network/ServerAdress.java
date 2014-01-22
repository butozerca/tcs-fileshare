package network;

import java.io.Serializable;

public class ServerAdress implements Serializable {
	
	private static final long serialVersionUID = -7204732772252817997L;
	public String adress;
	public int destPortSearch;
	public int destPortFile;
	public int destPortAdd;
	
	public ServerAdress(String adress, int destPortSearch, int destPortFile, int destPortAdd){
		this.adress = adress;
		this.destPortSearch = destPortSearch;
		this.destPortFile = destPortFile;
		this.destPortAdd = destPortAdd;
	}
	
	public ServerAdress(String line) {
		String[] ad = line.split(":");
		this.adress = ad[0];
		this.destPortSearch = Integer.parseInt(ad[1]);
		this.destPortFile = Integer.parseInt(ad[2]);
		this.destPortAdd = Integer.parseInt(ad[3]);
	}

	public boolean equals(Object o){
		ServerAdress other = (ServerAdress)o;
		if(this.adress.equals(other.adress) && this.destPortSearch == other.destPortSearch)
			return true;
		else 
			return false;
	}
	
	public String toString(){
		return adress + ":" 
				+ destPortSearch + ":"
				+ destPortFile + ":"
				+ destPortAdd;
	}
}
