import java.awt.*;
import java.net.InetAddress;
import java.net.ServerSocket;

import model.Fileshare;

import visual.Application;

/**
 * Launches the application.
 */
public class Main {
    

    public static void main(String[] args) {
    	
        EventQueue.invokeLater(new Runnable() {
            public void run() {
            	
            	try {
            		
            		new ServerSocket(55425, 10, InetAddress.getByName(null));
            		
            	} catch(java.net.BindException b) {
            		
            		System.out.println("Already running.");
            		System.exit(-1);
            		
            	}	catch( Exception e ) {
            		e.printStackTrace();
            		System.exit(-1);
            	}
            	
            	new Application().setVisible(true);
            	Fileshare.getInstance().getUser().getManager().startServers();
            }
        });
    }
}
