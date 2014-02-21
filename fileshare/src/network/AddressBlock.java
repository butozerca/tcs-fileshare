package network;

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
	
	public AddressBlock() {
		super();
	}
	
	public AddressBlock(AddressBlock other) {
		super(other);
	}
	/**
	 * Returns a random element from this container.
	 * @return
	 */
	public ServerAddress getRandom() {
		if(this.size() == 0)
			return null;
		return this.get(random.nextInt(this.size()));
	}
	
}
