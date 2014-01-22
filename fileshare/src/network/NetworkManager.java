package network;

import java.io.IOException;
import java.io.Serializable;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

public class NetworkManager implements Serializable {
	
	private static final long serialVersionUID = 1220500879358120053L;
	
	private ServerAdress myAdress = new ServerAdress("0.0.0.0", 20000, 21000, 22000);
	private AdressBlock neighbours = new AdressBlock();
	
	public NetworkManager(){
		
	}
	
	public void startServers(){
		try {
			FileSearchServer FSS = new FileSearchServer(myAdress.destPortSearch, neighbours);
			FSS.setDaemon(true);
			FSS.start();
		} catch (IOException e) {
			e.printStackTrace();
		}
		FileTransferServer FTS;
		try {
			FTS = new FileTransferServer(myAdress.destPortFile);
			FTS.setDaemon(true);
			FTS.start();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void setAdress(ServerAdress sa){
		this.myAdress = sa;
	}
	
	public ServerAdress getAdress(){
		return myAdress;
	}
	
	public AdressBlock getNeighbours() {
		return neighbours;
	};
	
	public void setNeighbours(AdressBlock ab){
		this.neighbours = ab;
	}
	
	public static void main(String args[]){
		try {
			Enumeration<NetworkInterface> n = NetworkInterface.getNetworkInterfaces();
			for (; n.hasMoreElements();)
		    {
		        NetworkInterface e = n.nextElement();

		        Enumeration<InetAddress> a = e.getInetAddresses();
		        for (; a.hasMoreElements();)
		        {
		            InetAddress addr = a.nextElement();
		            System.out.println("  " + addr.getHostAddress());
		        }
		    }
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
