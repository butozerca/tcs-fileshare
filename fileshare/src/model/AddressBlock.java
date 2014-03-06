package model;

import java.util.ArrayList;
import java.util.Random;

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
		if(this.size() == 0)
			return null;
		return this.get(random.nextInt(this.size()));
	}
	
}
