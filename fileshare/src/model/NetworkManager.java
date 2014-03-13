package model;

import java.io.IOException;
import java.io.Serializable;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

import network.AddNodeServer;
import network.FileSearchServer;
import network.FileTransferServer;

/**
 * Class consists of user's server's ports and his known neighbours in the network.
 * @author michal2
 *
 */
public class NetworkManager implements Serializable {
	
	private static final long serialVersionUID = 1220500879358120053L;
	
	private ServerAddress myAddress = new ServerAddress("0.0.0.0", 20000, 21000, 22000);
	private AddressBlock parentBlock, myBlock;
	private AddNodeServer ANS;
	private FileSearchServer FSS;
	private FileTransferServer FTS;

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
		childBlock = new AddressBlock[2];
		childBlock = children;
	}
	public ServerAddress getMyAddress() {
		return myAddress;
	}
	public void setMyAddress(ServerAddress myAddress) {
		this.myAddress = myAddress;
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
	
	public void setChildBlock(int i, AddressBlock ab){
		childBlock[i] = ab;
	}
	
	public AddressBlock[] getNeighbours(){
		AddressBlock[] neigh = new AddressBlock[3];
		neigh[0] = parentBlock;
		for(int i = 0; i < 2; ++i){
			neigh[i+1] = childBlock[i];
		}
		return neigh;
	}
	
	/**
	 * Starts servers associated with this manager.
	 */
	public void startServers(){
		try {
			FSS = new FileSearchServer(myAddress.getDestPortSearch(), this);
			FSS.setDaemon(true);
			FSS.start();
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			FTS = new FileTransferServer(myAddress.getDestPortFile());
			FTS.setDaemon(true);
			FTS.start();
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			ANS = new AddNodeServer(this);
			ANS.setDaemon(true);
			ANS.start();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@SuppressWarnings("deprecation")
	public void stopServers(){
		FSS.stop();
		FTS.stop();
		ANS.stop();
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
	/**
	 * @param parentBlock the parentBlock to set
	 */
	public void setParentBlock(AddressBlock parentBlock) {
		this.parentBlock = parentBlock;
	}
	/**
	 * @param myBlock the myBlock to set
	 */
	public void setMyBlock(AddressBlock myBlock) {
		this.myBlock = myBlock;
	}
	/**
	 * @param childBlock the childBlock to set
	 */
	public void setChildBlock(AddressBlock[] childBlock) {
		this.childBlock = childBlock;
	}
	
}
