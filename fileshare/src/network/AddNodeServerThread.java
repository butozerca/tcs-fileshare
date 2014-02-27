package network;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import queries.Phase0Query;
import queries.Phase1Query;
import queries.Phase2Query;

import common.Constants;

import model.AddressBlock;
import model.NetworkManager;
import model.ServerAddress;
import model.User;

public class AddNodeServerThread extends Thread {

	private Socket socket = null;
	private NetworkManager manager;
	
	public AddNodeServerThread(Socket socket, NetworkManager manager) {
		this.socket = socket;
		this.manager = manager;
	}
	
	private void forwardUP(String query){
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
		String info = query.substring(2);
		faza1(info);
	}
	
	private boolean notfull(){
		return manager.getMyBlock().size() < Constants.mesh_size;
	}
	
	private void faza1(String info){
		
		
		//TODO: faza 1
		
		
		
		int result = 1;
		faza2(result, info);
	}
	
	private void faza2(int destid, String info){
		if(manager.getMyBlock().getId() == destid){
			// lol
		}
		int temp = destid;
		while(temp > manager.getMyBlock().getId()*Constants.child_count+Constants.child_count-1)
			temp /= 2;
		for(int i = 0; i < Constants.child_count; ++i){
			if(manager.getMyBlock().getId()*Constants.child_count + i == temp){
				if(manager.getChildBlock()[i] != null){
					ServerAddress addr = manager.getChildBlock()[i].getRandom();
					if(addr != null){
						AddNodeClient anc = new AddNodeClient(new Phase2Query(destid, new ServerAddress(info)),
								addr.getIP(), addr.getDestPortAdd());
						anc.sendQuery();
						return;
					}
					else{
						//wtf
					}
				}
				else{
					//stworz mesha
				}
			}
		}
	}
	
	public void run() {
		try {
			BufferedReader in = new BufferedReader(
					new InputStreamReader(socket.getInputStream()));
			String line = in.readLine();
			System.out.println("dostalem query " + line);
			
			
			PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
			
			String[] q = line.split(":");
			switch(q[0]){
			case "0": 
				out.println("OK super dostalem"); 
				forwardUP(line); 
				break;
			case "1": 
				if(notfull())
					out.println(manager.getMyBlock().getId());
				else{
					int res = 1000000000;
					for(int i = 0; i < Constants.child_count; ++i){
						if(manager.getChildBlock()[i] == null){
							res = Math.min(res, manager.getMyBlock().getId() * Constants.child_count + i);
							continue;
						}
						ServerAddress sa = manager.getChildBlock()[i].getRandom();
						AddNodeClient anc = new AddNodeClient(new Phase1Query(), sa.getIP(), sa.getDestPortAdd());
						String rep = anc.sendQuery();
						res = Math.min(res, Integer.parseInt(rep));
					}
					out.println(res);
				}
				break;
			case "2": break;
			case "3": break;
			case "4": break;
			}
			
			socket.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
