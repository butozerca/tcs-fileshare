import java.awt.*;
import java.net.InetAddress;
import java.net.ServerSocket;


public class Main {
    /**
     * Launches the application.
     */

    public static void main(String[] args) {
    	
        EventQueue.invokeLater(new Runnable() {
            public void run() {
            	
            	/**
            	 * 	Prevents application from being run more than once at a time
            	 */
            	
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
            }
        });
    }
}
