package certificate;

import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

import javax.crypto.Cipher;

public class DSA {


	public KeyPair generateKeyPair() {
		
		try {
			  KeyPairGenerator generator = KeyPairGenerator.getInstance("DSA");
			  generator.initialize(1024, new java.security.SecureRandom());
			  KeyPair pair = generator.generateKeyPair();
			  return pair;
		}catch(Exception e) {
			System.out.print(e);
			return null;
		}
	  
	}
	
	/***
	 * Sign a message with a private key
	 * @param message to sign
	 * @param privateKey key to sign the message
	 * @return sgined data
	 */
	public byte[] sign(String message,PrivateKey privateKey) {
		try {
			Signature dsa = Signature.getInstance("SHA/DSA"); 
			dsa.initSign(privateKey);
			dsa.update(message.getBytes());

		    return dsa.sign();
		    
		}catch(Exception e) {
			 e.printStackTrace();
			System.out.println("Error signing message" );
			return null;
		}
		
	}
	
	/**
	 * Verify the signature of a message
	 * @param data
	 * @param publicKey
	 * @return
	 */
	public boolean verifySignature(byte[] data,byte[] signed, KeyPair keys) {
		
		try {

			Signature dsa = Signature.getInstance("SHA/DSA"); 
			dsa.initSign(keys.getPrivate());
			dsa.update(data);

	        /* Initialize the Signature object for verification */
	        PublicKey pub = keys.getPublic();
	        /* Encode the public key into a byte array */
	        byte[] encoded = pub.getEncoded();
	        /* Get the public key from the encoded byte array */
	        PublicKey fromEncoded = KeyFactory.getInstance("DSA", "SUN").generatePublic(new X509EncodedKeySpec(encoded));
	        dsa.initVerify(fromEncoded);

	        /* Update and verify the data */
	        dsa.update(data);
	        
	        return dsa.verify(signed);
	        
		}catch(Exception e) {
			e.printStackTrace();
			System.out.println(e);
		}
		
	    return false;
	}


	
}
