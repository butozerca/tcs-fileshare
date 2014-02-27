package queries;

import model.ServerAddress;

public class Phase0Query extends PhaseQuery {

	private ServerAddress address;
	
	public ServerAddress getAddress(){
		return address;
	}
	
	public Phase0Query(ServerAddress address){
		this.address = address;
	}
	
	public String toString(){
		return "0:" + address.toString();
	}

}
