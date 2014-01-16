import java.io.Serializable;



public class User implements Serializable {
	
	private static final long serialVersionUID = -2326346525021577409L;
	String username = "No Username";
	
	public User(){
	}
	
	public void setUsername(String username){
		this.username = username;
	}
	
	public String getUsername(){
		return username;
	}
	/*
	 * Klasa opisujaca uzytkownika aplikacji.
	 */

}
