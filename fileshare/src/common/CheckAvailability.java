package common;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;

import model.ServerAddress;

public abstract class CheckAvailability {
	public static boolean available(ServerAddress sa){
		Socket client = new Socket();
		try {
			client.connect(new InetSocketAddress(sa.getIP(), sa.getDestPortAdd()), Constants.ping_timeout);
			client.close();
			return true;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}
}
