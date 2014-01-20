package model;
import java.io.Serializable;

import network.NetworkManager;

/*
 * Klasa opisujaca uzytkownika aplikacji.
 */

public class User implements Serializable {
	
	private static final long serialVersionUID = -2326346525021577409L;
	private String username;
	private String localPath;
	private NetworkManager networkManager;
	
	public User(){
		username = "No username";
		localPath = "shared";
		if(System.getProperty("os.name").startsWith("Windows"))
			localPath += "\\";
		else
			localPath += "/";
		networkManager = new NetworkManager();
	}
	
	public void setManager(NetworkManager networkManager){
		this.networkManager = networkManager;
	}
	
	public NetworkManager getManager(){
		return networkManager;
	}
	
	public void setUsername(String username){
		this.username = username;
	}
	
	public String getUsername(){
		return username;
	}
	
	public void setPath(String path){
		this.localPath = path;
	}
	
	public String getPath(){
		return localPath;
	}
	

}
