package network;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.security.KeyStore;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;

public class SSLUtils {
	
	private static String[] strongSuites = {"SSL_DHE_DSS_WITH_3DES_EDE_CBC_SHA",
			"SSL_RSA_WITH_RC4_128_MD5", "SSL_RSA_WITH_RC4_128_SHA",
			"SSL_RSA_WITH_3DES_EDE_CBC_SHA"};
	
	public static SSLContext getSSLContext(char[] keyStorePass) throws Exception {
		SSLContext context = SSLContext.getInstance("SSL");
		
		KeyManagerFactory kmf = KeyManagerFactory.getInstance("SunX509");
		
		KeyStore ks = KeyStore.getInstance("JKS");
		if(keyStorePass == null)
			keyStorePass = "changeit".toCharArray();
		ks.load(new FileInputStream("keystore.jks"), keyStorePass);
		
		kmf.init(ks, keyStorePass);
		context.init(kmf.getKeyManagers(), null, null);
		return context;
	}
	
	public static SSLSocket getClientSocket(String host, int port, char[] keyStorePass) throws Exception {
		SSLContext context = SSLUtils.getSSLContext(keyStorePass);
		SSLSocketFactory factory = context.getSocketFactory();
		SSLSocket socket = (SSLSocket) factory.createSocket(host, port);
		socket.setEnabledCipherSuites(strongSuites);
		return socket;
	}
	
	public static SSLServerSocket getServerSocket(int port, char[] keyStorePass) throws Exception {
		SSLContext context = SSLUtils.getSSLContext(keyStorePass);
		SSLServerSocketFactory factory = context.getServerSocketFactory();
		SSLServerSocket server = (SSLServerSocket) factory.createServerSocket(12345);
		server.setEnabledCipherSuites(strongSuites);
		server.setNeedClientAuth(true);
		return server;
	}
	
	public static void addCert(String name, char[] keyStorePass) throws Exception {
		char sep = File.separatorChar;
		String cacertsPath = System.getProperty("java.home") + sep + "lib" + sep + "security" + sep + "cacerts";
		
		KeyStore ks = KeyStore.getInstance("JKS");
		if(keyStorePass == null)
			keyStorePass = "changeit".toCharArray();
		ks.load(new FileInputStream(cacertsPath), keyStorePass);
		
		CertificateFactory factory = CertificateFactory.getInstance("X.509");
		X509Certificate cert = (X509Certificate) factory.generateCertificate(new FileInputStream(name + ".pem"));
		
		ks.setCertificateEntry(name, cert);
		ks.store(new FileOutputStream(cacertsPath), keyStorePass);
	}
	
}
