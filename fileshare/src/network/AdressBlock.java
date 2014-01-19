package network;

import java.util.ArrayList;
import java.util.Random;

public class AdressBlock extends ArrayList<ServerAdress> {

	private static final long serialVersionUID = 2461753585709147277L;
	private Random random = new Random();
	
	public AdressBlock(){
		super();
	}
	
	public AdressBlock(AdressBlock other){
		super(other);
	}
	
	public ServerAdress getRandom(){
		if(this.size() == 0)
			return null;
		
		return this.get(random.nextInt(this.size()));
	}
	
}
