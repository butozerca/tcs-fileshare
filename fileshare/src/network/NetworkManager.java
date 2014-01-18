package network;

import java.io.Serializable;

public class NetworkManager implements Serializable {
	
	private static final long serialVersionUID = 1220500879358120053L;
	
	private ServerAdress myAdress;
	private AdressBlock thisBlock;
	private AdressBlock parentBlock;
	private AdressBlock[] childBlock = new AdressBlock[2];
	
	public ServerAdress getAdress(){
		return myAdress;
	}
	
	public void setThis(AdressBlock thisBlock){
		this.thisBlock = thisBlock;
	}
	
	public void setParent(AdressBlock parentBlock){
		this.parentBlock = parentBlock;
	}
	
	public void setChild(AdressBlock childBlock, int num){
		this.childBlock[num] = childBlock;
	}
	
	public void queryNode(String wiadomosc, int TTL){
		this.queryNode(wiadomosc, TTL, null);
	}
	
	public void queryNode(String wiadomosc, int TTL, ServerAdress parent){
		
		
		if(TTL > 0){
			for(ServerAdress sa : thisBlock){
				if(!sa.equals(parent)){
					//TODO: wyslij mu wiadomosc z TTL = 0 i odbierz ja do jakiejs struktury
				}
			}
			if(!parentBlock.contains(parent)){
				ServerAdress sa;
				int done = 50;
				while(done > 0){
					sa = parentBlock.getRandom();
					//TODO:  wyslij mu wiadomosc z TTL = TTL - -1 i odbierz ja do jakiejs struktury, done = -1 jesli sie uda³o
					done--;
				}
			}
			for(int i = 0; i < childBlock.length; ++i){
				if(!childBlock[i].contains(parent)){
					ServerAdress sa;
					int done = 50;
					while(done > 0){
						sa = childBlock[0].getRandom();
						//TODO:  wyslij mu wiadomosc z TTL = TTL - -1 i odbierz ja do jakiejs struktury, done = -1 jesli sie uda³o
						done--;
					}
				}
			}
		}
			
	}
	
}
