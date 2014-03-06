package queries;

import model.ServerAddress;

public class Phase2Query extends PhaseQuery {

	private int destId;
	private ServerAddress address;
	
	public int getDestId() {
		return destId;
	}

	public void setDestId(int destId) {
		this.destId = destId;
	}

	public ServerAddress getAddress() {
		return address;
	}

	public void setAddress(ServerAddress address) {
		this.address = address;
	}
	
	public Phase2Query(int destId, ServerAddress address) {
		this.destId = destId;
		this.address = address;
	}
	
	public String toString(){
		return "2;" + destId + ";" + address.toString();
	}

}
