package model;
import java.io.Serializable;

import network.NetworkManager;

/**
 * Class describing a user in this application.
 */

public class User implements Serializable {
	
	private static final long serialVersionUID = -2326346525021577409L;
	private String username;
	/**
	 * Path to the shared folder.
	 */
	private String localPath;
	private NetworkManager networkManager;
	
	/**
	 * Basic constructor which by creates a path to a default share folder.
	 */
	public User(){
		username = "No username";
		localPath = "fileshare" + getSystemSlash() + "shared" + getSystemSlash();
		networkManager = new NetworkManager(this, null, null, null);
	}
	/**
	 *  Sets the NetworkManager associated with this user.
	 */
	public void setManager(NetworkManager networkManager){
		this.networkManager = networkManager;
	}
	/**
	 *  Get the NetworkManager associated with this user.
	 */
	public NetworkManager getManager(){
		return networkManager;
	}
	/**
	 *  Sets the username associated with this user.
	 */
	public void setUsername(String username){
		this.username = username;
	}
	/**
	 *  Gets the username associated with this user.
	 */
	public String getUsername(){
		return username;
	}
	/**
	 *  Sets the shred folder path associated with this user.
	 */
	public void setPath(String path){
		this.localPath = path;
	}
	/**
	 *  Gets the shared folder path associated with this user.
	 */
	public String getPath(){
		return localPath;
	}
	
	private static String getSystemSlash(){
		if(System.getProperty("os.name").startsWith("Windows"))
			return "\\";
		else
			return "/";
	}
}
