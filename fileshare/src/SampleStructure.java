

import network.AddNodeClient;
import network.FileSearchClient;
import model.AddressBlock;
import model.NetworkManager;
import model.ServerAddress;
import queries.FileSearchQuery;
import queries.Phase0Query;

public class SampleStructure {
	public static void main(String[] args) throws InterruptedException {
		ServerAddress s10 = new ServerAddress("0.0.0.0:21000:21001:21002");
		ServerAddress s11 = new ServerAddress("0.0.0.0:21010:21011:21012");
		
		ServerAddress s20 = new ServerAddress("0.0.0.0:22000:22001:22002");
		ServerAddress s21 = new ServerAddress("0.0.0.0:22010:22011:22012");
		
		ServerAddress s30 = new ServerAddress("0.0.0.0:23000:23001:23002");
		ServerAddress s31 = new ServerAddress("0.0.0.0:23010:23011:23012");
		
		ServerAddress s40 = new ServerAddress("0.0.0.0:24000:24001:24002");
		ServerAddress s41 = new ServerAddress("0.0.0.0:24010:24011:24012");
		
		ServerAddress s51 = new ServerAddress("0.0.0.0:25000:25001:25002");
		
		AddressBlock b1 = new AddressBlock(1);
		b1.add(s10); b1.add(s11);
		
		AddressBlock b2 = new AddressBlock(2);
		b2.add(s20); b2.add(s21);
		
		AddressBlock b3 = new AddressBlock(3);
		b3.add(s30); b3.add(s31);
		
		AddressBlock b4 = new AddressBlock(4);
		b4.add(s40); b4.add(s41);
		
		NetworkManager m10 = new NetworkManager(null, null, b1, new AddressBlock[] {new AddressBlock(b2), new AddressBlock(b3)});
		NetworkManager m11 = new NetworkManager(null, null, new AddressBlock(b1), new AddressBlock[] {new AddressBlock(b2), new AddressBlock(b3)});
		m10.setMyAddress(s10); m11.setMyAddress(s11);
	
		NetworkManager m20 = new NetworkManager(null, new AddressBlock(b1), b2, new AddressBlock[] {new AddressBlock(b4), null});
		NetworkManager m21 = new NetworkManager(null, new AddressBlock(b1), new AddressBlock(b2), new AddressBlock[] {new AddressBlock(b4), null});
		m20.setMyAddress(s20); m21.setMyAddress(s21);
		
		NetworkManager m30 = new NetworkManager(null, new AddressBlock(b1), new AddressBlock(b3), new AddressBlock[] {null, null});
		NetworkManager m31 = new NetworkManager(null, new AddressBlock(b1), new AddressBlock(b3), new AddressBlock[] {null, null});
		m30.setMyAddress(s30); m31.setMyAddress(s31);
		
		NetworkManager m40 = new NetworkManager(null, new AddressBlock(b2), new AddressBlock(b4), new AddressBlock[] {null, null});
		NetworkManager m41 = new NetworkManager(null, new AddressBlock(b2), new AddressBlock(b4), new AddressBlock[] {null, null});
		m40.setMyAddress(s40); m41.setMyAddress(s41);
		
		NetworkManager m51 = new NetworkManager(null, null, null, new AddressBlock[] {null, null});
		m51.setMyAddress(s51);
		
		m10.startServers();
		m11.startServers();
		m20.startServers();
		m21.startServers();
		m30.startServers();
		m31.startServers();
		m40.startServers();
		m41.startServers();
		m51.startServers();
		
		FileSearchQuery query2 = new FileSearchQuery("1:File:3");
		FileSearchClient client2 = new FileSearchClient(m10, query2, 0);
		System.out.println(client2.getReply());
		
		Phase0Query query = new Phase0Query(s51);
		AddNodeClient client = new AddNodeClient(query, s30.getIP(), s30.getDestPortAdd());
		System.out.println("final resp: " + client.sendQuery());
		
		Thread.sleep(2000);
		
		System.out.println("m20:");
		for(AddressBlock ab : m20.getNeighbours())
			System.out.println(ab);
		
		System.out.println("m21:");
		for(AddressBlock ab : m21.getNeighbours())
			System.out.println(ab);
		
		System.out.println("m30:");
		for(AddressBlock ab : m30.getNeighbours())
			System.out.println(ab);
		
		System.out.println("m31:");
		for(AddressBlock ab : m31.getNeighbours())
			System.out.println(ab);
		
		System.out.println("m10:");
			System.out.println(m10.getMyBlock());
		
		System.out.println("m11:");
			System.out.println(m11.getMyBlock());
	} 
}
