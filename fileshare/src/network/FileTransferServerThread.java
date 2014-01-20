package network;

import java.io.*;
import java.net.*;

import javax.crypto.Cipher;
import javax.crypto.CipherOutputStream;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

public class FileTransferServerThread extends Thread {

	private Socket socket = null;
	
	public FileTransferServerThread(Socket socket) {
		this.socket = socket;
	}
	
	public void run() {
		try {
			BufferedReader in = new BufferedReader(
					new InputStreamReader(socket.getInputStream()));
			String file = in.readLine();
			FileInputStream fin = new FileInputStream(file);
			
			byte[] desKeyData = "akpyzczs drawde".getBytes();
			DESKeySpec desKeySpec = new DESKeySpec(desKeyData);
			SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
			SecretKey desKey = keyFactory.generateSecret(desKeySpec);
			Cipher des = Cipher.getInstance("DES");
			des.init(Cipher.ENCRYPT_MODE, desKey);
			CipherOutputStream cout = new CipherOutputStream(socket.getOutputStream(), des);
			
			int b;
			while((b = fin.read()) != -1)
				cout.write(b);
			
			fin.close();
			cout.close();
			socket.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
