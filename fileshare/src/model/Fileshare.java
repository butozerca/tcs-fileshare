package model;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Observable;



/**
 * Fileshare singleton class
 */
public class Fileshare extends Observable {

	private static Fileshare instance = null;
	/**
	 *  User associated with this instance
	 */
	private User user = null;
	private String savePath = "save/filesharestate.save";
	
	
	private Fileshare(){
		if(!loadFromFile(savePath))
			user = new User();
	}
	
	/**
	 * Returns a singleton of this class.
	 * @return
	 */
	
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
	
	/**
	 * Saves this instance to savepath.
	 */
    public void saveToFile(){
    	try {
    		Files.deleteIfExists(Paths.get(savePath));
    		new File("save/").mkdirs();
    		ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(savePath));
    		oos.writeObject(user);
    		oos.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
    
    /**
     * Tries to load a saved version of a user.
     * @param loadPath
     * @return True, if there was a loadable file under the loadpath, false otherwise.
     */
    
    public boolean loadFromFile(String loadPath){
    	try{
    		if(Files.exists(Paths.get(loadPath))){
    			ObjectInputStream ois = new ObjectInputStream(new FileInputStream(loadPath));
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
    
    /**
     * Shortcut to user's shared folder path.
     */  
    public static String getSharedPath(){
    	return getInstance().getUser().getPath();
    }
    
}
