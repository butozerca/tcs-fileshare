package network;

public class SampleStructure {
	public static void main(String[] args) {
		ServerAddress s10 = new ServerAddress("0.0.0.0:21000:21001:21002");
		ServerAddress s11 = new ServerAddress("0.0.0.0:21010:21011:21012");
		
		ServerAddress s20 = new ServerAddress("0.0.0.0:22000:22001:22002");
		ServerAddress s21 = new ServerAddress("0.0.0.0:22010:22011:22012");
		
		ServerAddress s30 = new ServerAddress("0.0.0.0:23000:23001:23002");
		ServerAddress s31 = new ServerAddress("0.0.0.0:23010:23011:23012");
		
		ServerAddress s40 = new ServerAddress("0.0.0.0:24000:24001:24002");
		ServerAddress s41 = new ServerAddress("0.0.0.0:24010:24011:24012");
		
		AddressBlock b1 = new AddressBlock(1);
		b1.add(s10); b1.add(s11);
		
		AddressBlock b2 = new AddressBlock(2);
		b2.add(s20); b2.add(s21);
		
		AddressBlock b3 = new AddressBlock(3);
		b3.add(s30); b3.add(s31);
		
		AddressBlock b4 = new AddressBlock(4);
		b4.add(s40); b4.add(s41);
		
		NetworkManager m10 = new NetworkManager(null, null, b1, new AddressBlock[] {b2, b3});
		NetworkManager m11 = new NetworkManager(null, null, b1, new AddressBlock[] {b2, b3});
		m10.setMyAddress(s10); m11.setMyAddress(s11);
	
		NetworkManager m20 = new NetworkManager(null, b1, b2, new AddressBlock[] {b4, null});
		NetworkManager m21 = new NetworkManager(null, b1, b2, new AddressBlock[] {b4, null});
		m20.setMyAddress(s20); m21.setMyAddress(s21);
		
		NetworkManager m30 = new NetworkManager(null, b1, b3, new AddressBlock[] {null, null});
		NetworkManager m31 = new NetworkManager(null, b1, b3, new AddressBlock[] {null, null});
		m30.setMyAddress(s30); m31.setMyAddress(s31);
		
		NetworkManager m40 = new NetworkManager(null, b2, b4, new AddressBlock[] {null, null});
		NetworkManager m41 = new NetworkManager(null, b2, b4, new AddressBlock[] {null, null});
		m40.setMyAddress(s40); m41.setMyAddress(s41);
		
		m10.startServers();
		m11.startServers();
		m20.startServers();
		m21.startServers();
		m30.startServers();
		m31.startServers();
		m40.startServers();
		m41.startServers();
		
		FileSearchQuery query = new FileSearchQuery("1:File:3");
		FileSearchClient client = new FileSearchClient(m10, query, 0);
		System.out.println(client.getReply());
	} 
}
