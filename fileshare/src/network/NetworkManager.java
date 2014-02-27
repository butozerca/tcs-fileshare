package network;

import java.io.IOException;
import java.io.Serializable;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

import common.Constants;

import model.User;
/**
 * Class consists of user's server's ports and his known neighbours in the network.
 * @author michal2
 *
 */
public class NetworkManager implements Serializable {
	
	private static final long serialVersionUID = 1220500879358120053L;
	
	private ServerAddress myAdress = new ServerAddress("0.0.0.0", 20000, 21000, 22000);
	private AddressBlock parentBlock, myBlock;
	private AddressBlock[] childBlock;
	private long mesh_id;
	private User user;
	/**
	 * Creates a manager for the selected user.
	 */
	public NetworkManager(User user, AddressBlock parent, AddressBlock myblock, AddressBlock c1, AddressBlock c2){
		this.user = user;
		parentBlock = parent;
		myBlock = myblock;
		childBlock = new AddressBlock[Constants.child_count];
		childBlock[0] = c1;
		childBlock[1] = c2;
	}
	public ServerAddress getMyAdress() {
		return myAdress;
	}
	public AddressBlock getParentBlock() {
		return parentBlock;
	}
	public AddressBlock getMyBlock() {
		return myBlock;
	}
	public AddressBlock[] getChildBlock() {
		return childBlock;
	}
	public long getMesh_id() {
		return mesh_id;
	}
	public User getUser() {
		return user;
	}
	/**
	 * Starts servers associated with this manager.
	 */
	public void startServers(){
		try {
			FileSearchServer FSS = new FileSearchServer(myAdress.getDestPortSearch(), this);
			FSS.setDaemon(true);
			FSS.start();
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			FileTransferServer FTS = new FileTransferServer(myAdress.getDestPortFile());
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
	public void setAdress(ServerAddress sa){
		this.myAdress = sa;
	}
	/**
	 * Returns this manager's adress.
	 * @return
	 */
	public ServerAddress getAdress(){
		return myAdress;
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
