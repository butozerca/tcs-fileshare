package common;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

public class SearchFile {

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
	
    public static void main(String[] args) 
    {
        Scanner scan = new Scanner(System.in);
        System.out.println("Enter the directory where to search ");
        String directory = scan.next();
        ArrayList<String> res = SearchFile.searchFile("User.java", directory);
        for(String s : res)
        	System.out.println(s);
        scan.close();
    }

}
