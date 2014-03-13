package model;

import java.util.ArrayList;
import java.util.Random;

import common.CheckAvailability;

/**
 * Macro for a list of addresses.
 * @author michal2
 *
 */
public class AddressBlock extends ArrayList<ServerAddress> {

	private static final long serialVersionUID = 2461753585709147277L;
	private Random random = new Random();
	private int meshId;
	
	public AddressBlock(int id) {
		super();
		meshId = id;
	}
	
	public AddressBlock(AddressBlock other) {
		super(other);
		meshId = other.getId();
	}
	
	public AddressBlock(int id, String line){
		this(id);
		if(line.length() == 2)
			return;
		line = line.substring(1, line.length()-1);
		String[] q = line.split(",");
		for(String s : q){
			String r = s.trim();
			ServerAddress sa = new ServerAddress(r);
			this.add(sa);
		}
	}
	/**
	 * Returns a random element from this container.
	 * @return
	 */
	
	public int getId(){
		return meshId;
	}
	
	public ServerAddress getRandom() {
		AddressBlock active = listActive();
		if(active.size() == 0)
			return null;
		return active.get(random.nextInt(active.size()));
	}
	
	private AddressBlock listActive() {
		AddressBlock res = new AddressBlock(this.meshId);
		for(ServerAddress sa : this) {
			if(CheckAvailability.available(sa))
				res.add(sa);
		}
		return res;
	}
	
}
