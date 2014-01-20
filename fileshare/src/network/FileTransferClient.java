package network;

import java.io.*;
import java.net.*;
import javax.crypto.*;
import javax.crypto.spec.DESKeySpec;

public class FileTransferClient {
	
	private String address;
	private File file;
	
	public FileTransferClient(String query) throws MalformedURLException {
		String[] q = query.split(":");
		if(q.length != 2)
			throw new MalformedURLException();
		this.address = q[0];
		this.file = new File(q[1]);
		System.out.println(file);
	}
	
	public void download() {
		try {
			//TODO dodac do query odpowiedni port i podpiac
			Socket client = new Socket(address, 21000);
			
			PrintWriter out = new PrintWriter(client.getOutputStream(), true);
			out.println(file.getPath());
			
			byte[] desKeyData = "akpyzczs drawde".getBytes();
			DESKeySpec desKeySpec = new DESKeySpec(desKeyData);
			SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
			SecretKey desKey = keyFactory.generateSecret(desKeySpec);
			Cipher des = Cipher.getInstance("DES");
			des.init(Cipher.DECRYPT_MODE, desKey);
			CipherInputStream cin = new CipherInputStream(client.getInputStream(), des);
			
			FileOutputStream fout = new FileOutputStream("fileshare/" + file.getName());
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
		FileTransferClient cl = new FileTransferClient("0.0.0.0:fileshare/shared/File.txt");
		cl.download();
	}
}
