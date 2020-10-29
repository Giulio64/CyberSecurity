
import java.security.KeyPair;

import certificate.DSA;
import certificate.X509;
import cipher.RSA;


public class main {

	
	public static void main(String[] args) {
		
		testRSASign();
		testDSASign();
		testX509();
	}
	
	/****
	 * Test the RSA asy sign method
	 */
	public static void testRSASign() {
		
		
		RSA rsa = new RSA();
		
		KeyPair keys = rsa.generateKeyPair();
		
		String message = "messageTest";
		String encrypted = rsa.encrypt(message, keys.getPublic());
		
		System.out.println("RSA Decryption: " +  rsa.decrypt(encrypted, keys.getPrivate()).equals(message));
		
	}
	
	/****
	 * Test the DSA asy sign method
	 */
	public static void testDSASign() {
		
		 DSA dsa = new DSA();
		 KeyPair keys = dsa.generateKeyPair();
		 
		 String message = "messageTest";
		 
		 byte[] signed = dsa.sign(message, keys.getPrivate());
		 System.out.println("DSA sign verification: " + dsa.verifySignature(message.getBytes(), signed, keys));
		    
	}
	
	/****
	 * Test the 509 certificate
	 */
	public static void testX509() {
		
		RSA rsa = new RSA();
		KeyPair keys = rsa.generateKeyPair();
		
		X509 x = new X509();

		x.writeCerCertificate(keys);
		x.writePemCertificate(keys);
		
	}
	
	
}
