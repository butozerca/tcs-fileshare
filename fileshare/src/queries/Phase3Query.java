package queries;

import model.NetworkManager;

public class Phase3Query extends PhaseQuery {

	private NetworkManager manager;
	
	public Phase3Query(NetworkManager manager) {
		this.manager = manager;
	}
	
	public NetworkManager getManager(){
		return manager;
	}
	
	public String toString(){
		//TODO: manager w formie dla ludzi
		return "";
	}

}
