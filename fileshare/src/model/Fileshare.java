package model;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Observable;



/*
 * Fileshare singleton
 */
public class Fileshare extends Observable {

	private static Fileshare instance = null;
	private User user = null;
	private String savePath = "filesharestate.save";
	
	
	private Fileshare(){
		if(!loadFromFile(savePath))
			user = new User();
	}
	
	public static Fileshare getInstance(){
		if(instance == null)
			instance = new Fileshare();
		return instance;
	}
	
	public User getUser(){
		return user;
	}
	
	public String getPath(){
		return savePath;
	}
	
    public void saveToFile(){
    	try {
    		Files.deleteIfExists(Paths.get("save/" + savePath));
    		new File("save/").mkdirs();
    		ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("save/" + savePath));
    		oos.writeObject(user);
    		oos.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
    
    public boolean loadFromFile(String loadPath){
    	try{
    		if(Files.exists(Paths.get("save/" + loadPath))){
    			ObjectInputStream ois = new ObjectInputStream(new FileInputStream("save/" + loadPath));
    			user = (User)ois.readObject();
    			ois.close();
    			return true;
    		}
    	}
    	catch(Exception e){
    		e.printStackTrace();
    	}
    	return false;
    }
    
    public static String getSharedPath(){
    	return getInstance().getUser().getPath();
    }
    
}
