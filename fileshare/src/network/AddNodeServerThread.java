package network;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import model.AddressBlock;
import model.NetworkManager;
import model.ServerAddress;
import queries.Phase0Query;
import queries.Phase1Query;
import queries.Phase2Query;
import queries.Phase3Query;
import queries.Phase4Query;
import queries.PhaseQuery;

import common.Constants;

public class AddNodeServerThread extends Thread {

	private Socket socket = null;
	private NetworkManager manager;
	
	public AddNodeServerThread(Socket socket, NetworkManager manager) {
		this.socket = socket;
		this.manager = manager;
	}
	
	private void forwardUP(PrintWriter out, String query){
		if(manager.getMyBlock().getId() != 1){
			ServerAddress addr = manager.getParentBlock().getRandom();
			if(addr != null){
				Phase0Query pquery = new Phase0Query(query);
				AddNodeClient anc = new AddNodeClient(pquery, addr.getIP(), addr.getDestPortAdd());
				String rep = anc.sendQuery();
				System.out.println("forward UP: " + rep);
				if(rep != null)
					return;
			}
		}
		faza1(out, query);
	}
	
	private boolean notfull(){
		return manager.getMyBlock().size() < Constants.mesh_size;
	}
	
	private void tellNeighbours(int id, String info){
		System.out.println("moj block1:" + manager.getMyBlock());
		for(AddressBlock ab : manager.getNeighbours()){
			if(ab == null)
				continue;
			for(ServerAddress sa : ab){
				System.out.println("moj bloczek: " + manager.getMyBlock());
				System.out.println("tell neighbours: " + sa);
				if(sa.equals(new ServerAddress(info)))
					continue;
				PhaseQuery pq = new Phase4Query(id, new ServerAddress(info));
				AddNodeClient anc = new AddNodeClient(pq, sa.getIP(), sa.getDestPortAdd());
				anc.sendQuery();
			}
		}
		System.out.println("moj block2:" + manager.getMyBlock());
		for(ServerAddress sa : manager.getMyBlock()){
			if(sa.equals(new ServerAddress(info)) || sa.equals(manager.getMyAddress()))
				continue;
			System.out.println("sa: " + sa);
			PhaseQuery pq = new Phase4Query(id, new ServerAddress(info));
			AddNodeClient anc = new AddNodeClient(pq, sa.getIP(), sa.getDestPortAdd());
			anc.sendQuery();
		}
	}
	
	private void faza1(PrintWriter out, String info){
		System.out.println("ODPALAM FAZE 1: " + manager.getMyAddress());
		PhaseQuery pq = new Phase1Query();
		AddNodeClient anc = new AddNodeClient(pq, manager.getMyAddress().getIP(), manager.getMyAddress().getDestPortAdd());
		String rep = anc.sendQuery();

		int result = Integer.parseInt(rep);
		faza2(out, result, info);
	}
	
	private void faza2(PrintWriter out, int destid, String info){
		Phase2Handler(out, destid, info);
	}
	
	private void Phase0Handler(PrintWriter out, String line){
		out.println("OK super dostalem"); 
		forwardUP(out, line.substring(2));
	}
	
	private void Phase1Handler(PrintWriter out){
		if(notfull())
			out.println(manager.getMyBlock().getId());
		else{
			int res = Integer.MAX_VALUE;
			for(int i = 0; i < 2; ++i){
				if(manager.getChildBlock()[i] == null){
					res = Math.min(res, manager.getMyBlock().getId() * 2 + i);
					continue;
				}
				ServerAddress sa = manager.getChildBlock()[i].getRandom();
				AddNodeClient anc = new AddNodeClient(new Phase1Query(), sa.getIP(), sa.getDestPortAdd());
				String rep = anc.sendQuery();
				res = Math.min(res, Integer.parseInt(rep));
			}
			out.println(res);
		}
	}
	
	private void Phase2Handler(PrintWriter out, int destid, String info){
		if(manager.getMyBlock().getId() == destid){
			System.out.println("my block p2h1: " + manager.getMyBlock());
			AddressBlock[] newNodesBlocks = new AddressBlock[4];
			newNodesBlocks[0] = manager.getParentBlock();
			newNodesBlocks[1] = manager.getMyBlock();
			newNodesBlocks[2] = manager.getChildBlock()[0];
			newNodesBlocks[3] = manager.getChildBlock()[1];
			System.out.println("my block p2h2: " + manager.getMyBlock());
			ServerAddress sa = new ServerAddress(info);
			newNodesBlocks[1].add(sa);			
			PhaseQuery pq = new Phase3Query(manager.getMyBlock().getId(), newNodesBlocks);
			AddNodeClient anc = new AddNodeClient(pq, sa.getIP(), sa.getDestPortAdd());
			anc.sendQuery();
			System.out.println("my block p2h3: " + manager.getMyBlock());
			tellNeighbours(manager.getMyBlock().getId(), info);
			out.println("OK p2h");
			return;
		}
		int temp = destid;
		while(temp > manager.getMyBlock().getId()*2+1)
			temp /= 2;
		for(int i = 0; i < 2; ++i){
			if(manager.getMyBlock().getId()*2 + i == temp){
				if(manager.getChildBlock()[i] != null){
					ServerAddress addr = manager.getChildBlock()[i].getRandom();
					if(addr != null){
						AddNodeClient anc = new AddNodeClient(new Phase2Query(destid, new ServerAddress(info)),
								addr.getIP(), addr.getDestPortAdd());
						anc.sendQuery();
						return;
					}
					else{
						//wtf WTF nie ma polaczenia
						return;
					}
				}
				else{
					AddressBlock newMesh = new AddressBlock(manager.getMyBlock().getId()*2 + i);
					manager.setChildBlock(i, newMesh);
					
					AddressBlock[] newNodesBlocks = new AddressBlock[4];
					newNodesBlocks[0] = manager.getMyBlock();
					newNodesBlocks[1] = newMesh;
					newNodesBlocks[2] = newNodesBlocks[3] = null;

					ServerAddress sa = new ServerAddress(info);
					
					newNodesBlocks[1].add(sa);			
					PhaseQuery pq = new Phase3Query(newMesh.getId(), newNodesBlocks);
					AddNodeClient anc = new AddNodeClient(pq, sa.getIP(), sa.getDestPortAdd());
					anc.sendQuery();
					
					tellNeighbours(manager.getMyBlock().getId()*2 + i, info);
					out.println("OK p2h stowrzylem mesha");
					return;
				}
			}
		}
	}
	
	private void Phase3Handler(PrintWriter out, String[] q){
		int myid = Integer.parseInt(q[1]);
		AddressBlock parentBlock = null;
		AddressBlock c1Block = null, c2Block = null;		
		AddressBlock myBlock = new AddressBlock(myid, q[3]);
		
		if(q[2].length() > 0)
			parentBlock = new AddressBlock(myid/2, q[2]);
		if(q[4].length() > 0)
			c1Block = new AddressBlock(myid*2, q[4]);
		if(q[5].length() > 0)
			c2Block = new AddressBlock(myid*2 + 1, q[5]);
		
		manager.setMyBlock(myBlock);
		manager.setChildBlock(0, c1Block);
		manager.setChildBlock(1, c2Block);
		manager.setParentBlock(parentBlock);
		
		out.println("OK");
		return;
	}
	
	private void Phase4Handler(PrintWriter out, int id, String info){
		int myid = manager.getMyBlock().getId();
		ServerAddress sa = new ServerAddress(info);
		if(2*myid == id) {
			if(manager.getChildBlock()[0] == null)
				manager.setChildBlock(0, new AddressBlock(id));
			manager.getChildBlock()[0].add(sa);
			System.out.println(manager.getMyAddress().getDestPortAdd() + " dodalem jako lewe dziecko " + sa);
		} else if (2*myid + 1 == id) {
			if(manager.getChildBlock()[1] == null)
				manager.setChildBlock(1, new AddressBlock(id));
			manager.getChildBlock()[1].add(sa);
			System.out.println(manager.getMyAddress().getDestPortAdd() + " dodalem jako prawe dziecko " + sa);
		}
		else if(myid == id) {
			manager.getMyBlock().add(sa);
			System.out.println(manager.getMyAddress().getDestPortAdd() + " dodalem do siebie " + sa);
		}
		else if(myid/2 == id) {
			manager.getParentBlock().add(sa);
			System.out.println(manager.getMyAddress().getDestPortAdd() + " dodalem jako rodzica " + sa);
		}
		out.println("DODALEM2" + manager.getMyAddress());
	}
	
	public void run() {
		try {
			BufferedReader in = new BufferedReader(
					new InputStreamReader(socket.getInputStream()));
			String line = in.readLine();
			System.out.println("dostalem query " + line);
			PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
			String[] q = line.split(";");
			switch(q[0]){
			case "0": 
				Phase0Handler(out, line);
				break;
			case "1": 
				Phase1Handler(out);
				break;
			case "2": 
				Phase2Handler(out, Integer.parseInt(q[1]), q[2]);
				break;
			case "3": 
				Phase3Handler(out, q);
				break;
			case "4": 
				Phase4Handler(out, Integer.parseInt(q[1]), q[2]);
				break;
			}
			
			socket.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
