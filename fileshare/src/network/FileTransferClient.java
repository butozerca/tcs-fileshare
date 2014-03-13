package network;

import java.io.*;
import java.net.*;
import javax.crypto.*;
import javax.crypto.spec.DESKeySpec;

import model.Fileshare;
/**
 * Class provides client part of sending a file over the network.
 * @author michal2
 */
public class FileTransferClient {
	
	private String address;
	private int port;
	private String file;
	/**
	 * Constructor.
	 * @param query String consisting adress of the file and its name.
	 * @throws MalformedURLException
	 */
	public FileTransferClient(String query) throws MalformedURLException {
		String[] q = query.split(":");
		if(q.length != 3)
			throw new MalformedURLException();
		this.address = q[0];
		this.port = Integer.parseInt(q[1]);
		this.file = q[2];
	}
	
	public FileTransferClient(String ip, int port, String file) {
		this.address = ip;
		this.port = port;
		this.file = file;
	}
	/**
	 * Starts download of a file.
	 */
	public void download() {
		try {
			Socket client = new Socket(address, port);
			
			PrintWriter out = new PrintWriter(client.getOutputStream(), true);
			out.println(file);
			
			byte[] desKeyData = "akpyzczs drawde".getBytes();
			DESKeySpec desKeySpec = new DESKeySpec(desKeyData);
			SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
			SecretKey desKey = keyFactory.generateSecret(desKeySpec);
			Cipher des = Cipher.getInstance("DES");
			des.init(Cipher.DECRYPT_MODE, desKey);
			CipherInputStream cin = new CipherInputStream(client.getInputStream(), des);
			FileOutputStream fout = new FileOutputStream(Fileshare.getSharedPath() + new File(file).getName());
			int b;
			while((b = cin.read()) != -1)
				fout.write(b);
			
			
			cin.close();
			fout.close();
			client.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) throws IOException {
		FileTransferServer server = new FileTransferServer(21000);
		server.setDaemon(true);
		server.start();
		FileTransferClient cl = new FileTransferClient("0.0.0.0:21000:fileshare/shared/File.txt");
		cl.download();
	}
}
