package common;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

public class SearchFile {

	public static ArrayList<String> searchFile(String name, File file)
    {
        File[] list = file.listFiles();
        if(list.length == 0) return null;
        ArrayList<String> res = new ArrayList<String>(), ret;
        
        for (File fil : list) {
        	if( fil.isDirectory() ){
        		ret = searchFile(name, fil);
        		if(ret != null)
        			res.addAll(ret);
        	}
        	else if(fil.getName().contains(name))
        		res.add(fil.getAbsolutePath());
        }
        return res;
    }
	
    public static void main(String[] args) 
    {
        Scanner scan = new Scanner(System.in);
        System.out.println("Enter the directory where to search ");
        String directory = scan.next();
        ArrayList<String> res = SearchFile.searchFile("User.java", new File(directory));
        for(String s : res)
        	System.out.println(s);
        scan.close();
    }

}
