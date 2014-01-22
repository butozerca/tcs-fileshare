package network;

import java.util.ArrayList;
import java.util.Random;
/**
 * Macro for a list of adresses.
 * @author michal2
 *
 */
public class AdressBlock extends ArrayList<ServerAdress> {

	private static final long serialVersionUID = 2461753585709147277L;
	private Random random = new Random();
	
	public AdressBlock(){
		super();
	}
	
	public AdressBlock(AdressBlock other){
		super(other);
	}
	/**
	 * Returns a random element from this container.
	 * @return
	 */
	public ServerAdress getRandom(){
		if(this.size() == 0)
			return null;
		
		return this.get(random.nextInt(this.size()));
	}
	
}
