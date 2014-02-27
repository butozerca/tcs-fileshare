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
	
	private ServerAddress myAddress = new ServerAddress("0.0.0.0", 20000, 21000, 22000);
	private AddressBlock parentBlock, myBlock;
	private AddressBlock[] childBlock;
	private User user;
	/**
	 * Creates a manager for the selected user.
	 */
	public NetworkManager(User user, AddressBlock parent, AddressBlock myblock, AddressBlock[] children){
		this.user = user;
		parentBlock = parent;
		myBlock = myblock;
		if(myBlock == null){
			myBlock = new AddressBlock(1);
			myBlock.add(myAddress);
		}
		childBlock = new AddressBlock[Constants.child_count];
		childBlock = children;
	}
	public ServerAddress getMyAdress() {
		return myAddress;
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
	public User getUser() {
		return user;
	}
	
	public AddressBlock[] getNeighbours(){
		AddressBlock[] neigh = new AddressBlock[Constants.child_count + 1];
		neigh[0] = parentBlock;
		for(int i = 0; i < Constants.child_count; ++i){
			neigh[i+1] = childBlock[i];
		}
		return neigh;
	}
	
	/**
	 * Starts servers associated with this manager.
	 */
	public void startServers(){
		try {
			FileSearchServer FSS = new FileSearchServer(myAddress.getDestPortSearch(), this);
			FSS.setDaemon(true);
			FSS.start();
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			FileTransferServer FTS = new FileTransferServer(myAddress.getDestPortFile());
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
		this.myAddress = sa;
	}
	/**
	 * Returns this manager's adress.
	 * @return
	 */
	public ServerAddress getAdress(){
		return myAddress;
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
