package model;
import java.io.Serializable;



public class User implements Serializable {
	
	private static final long serialVersionUID = -2326346525021577409L;
	private String username = "No Username";
	private String localPath = "shared/";
	
	public User(){
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
	/*
	 * Klasa opisujaca uzytkownika aplikacji.
	 */

}
