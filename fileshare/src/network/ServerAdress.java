package network;

import java.io.Serializable;

public class ServerAdress implements Serializable {
	
	private static final long serialVersionUID = -7204732772252817997L;
	public String adress;
	public int destPort;
	
	public ServerAdress(String adress, int destPort){
		this.adress = adress;
		this.destPort = destPort;
	}

	public String toString(){
		return new String(adress + " " + destPort);
	}
}
