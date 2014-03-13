package common;

import java.io.File;
import java.util.ArrayList;
/**
 * Class that provides a static method which searches for a filename in a selected directory.
 * @author michal2
 *
 */
public class SearchFile {

	/**
	 * Provided a name and a directory, it returns paths to all files which contain filename string in their name.
	 * @param filename Part of a filename
	 * @param directory Directory in which you search
	 * @return ArrayList of paths
	 */
	public static ArrayList<String> searchFile(String filename, String directory)
    {
		File file = new File(directory);
        File[] list = file.listFiles();
        ArrayList<String> res = new ArrayList<String>(), ret;
        if(list == null || list.length == 0) return res;
        
        for (File fil : list) {
        	if( fil.isDirectory() ){
        		ret = searchFile(filename, fil.getPath());
        		if(ret != null)
        			res.addAll(ret);
        	}
        	else if(fil.getName().contains(filename))
        		res.add(fil.getAbsolutePath());
        }
        return res;
    }

}
