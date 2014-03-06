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
	
	public Phase0Query(String s){
		String[] q = s.split(":");
		this.address = new ServerAddress(q[0], Integer.parseInt(q[1]), Integer.parseInt(q[2]), Integer.parseInt(q[3]));
	}
	
	public String toString(){
		return "0;" + address.toString();
	}

}
