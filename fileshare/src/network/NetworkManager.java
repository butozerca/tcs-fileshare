package network;

import java.io.Serializable;

public class NetworkManager implements Serializable {
	
	private static final long serialVersionUID = 1220500879358120053L;
	
	private transient ServerAdress myAdress;
	
	public NetworkManager(){
		
	}
	
	public ServerAdress getAdress(){
		return myAdress;
	}
	
	
}
