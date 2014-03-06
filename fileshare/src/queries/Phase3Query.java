package queries;

import model.AddressBlock;

public class Phase3Query extends PhaseQuery {

	private int id;
	private AddressBlock[] blocks;
	
	public Phase3Query(int id, AddressBlock[] blocks) {
		this.id = id;
		this.blocks = blocks;
	}
	
	public AddressBlock[] getBlocks(){
		return blocks;
	}
	
	public String toString(){
		StringBuilder str = new StringBuilder("3;");
		str.append(id);
		for(AddressBlock ab : blocks){
			str.append(";");
			if(ab == null) {
				str.append("[]");
				continue;
			}
			str.append(ab.toString());
		}
		return str.toString();
	}

}
