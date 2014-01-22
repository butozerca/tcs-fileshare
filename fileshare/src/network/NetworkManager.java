package network;

import java.io.IOException;
import java.io.Serializable;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

import model.User;
/**
 * Class consists of user's server's ports and his known neighbours in the network.
 * @author michal2
 *
 */
public class NetworkManager implements Serializable {
	
	private static final long serialVersionUID = 1220500879358120053L;
	
	private ServerAdress myAdress = new ServerAdress("0.0.0.0", 20000, 21000, 22000);
	private AdressBlock neighbours = new AdressBlock();
	private User user;
	/**
	 * Creates a manager for the selected user.
	 */
	public NetworkManager(User user){
		this.user = user;
	}
	/**
	 * Starts servers associated with this manager.
	 */
	public void startServers(){
		try {
			FileSearchServer FSS = new FileSearchServer(myAdress.destPortSearch, neighbours);
			FSS.setDaemon(true);
			FSS.start();
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			FileTransferServer FTS = new FileTransferServer(myAdress.destPortFile);
			FTS.setDaemon(true);
			FTS.start();
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			AddNodeServer ANS = new AddNodeServer(user);
			ANS.setDaemon(true);
			ANS.start();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 * Allows to change adress of the user.
	 * @param sa
	 */
	public void setAdress(ServerAdress sa){
		this.myAdress = sa;
	}
	/**
	 * Returns this manager's adress.
	 * @return
	 */
	public ServerAdress getAdress(){
		return myAdress;
	}
	/**
	 * Returns this manager's neighbour list.
	 * @return
	 */
	public AdressBlock getNeighbours() {
		return neighbours;
	}
	/**
	 * Allows to change neighbour list of the user.
	 * @param sa
	 */
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
			e.printStackTrace();
		}
	}
	
}
