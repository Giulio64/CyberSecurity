package cipher;

import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

import javax.crypto.Cipher;



/***
 * Class to implement the RSA 
 * @author giulioserra
 *
 */
public class RSA extends AbstractAsymetric {

	PrivateKey privateKey;

	@Override
	public KeyPair generateKeyPair() {
		
		try {
			  KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA");
			  generator.initialize(2048, new java.security.SecureRandom());
			  KeyPair pair = generator.generateKeyPair();
			  return pair;
		}catch(Exception e) {
			System.out.println(e);
			return null;
		}
	  
	}

	@Override
	public String encrypt(String plainText, PublicKey publicKey) {
		
		try {
			 Cipher encryptCipher = Cipher.getInstance("RSA");
		     encryptCipher.init(Cipher.ENCRYPT_MODE, publicKey);

		     byte[] cipherText = encryptCipher.doFinal(plainText.getBytes());

		     return Base64.getEncoder().encodeToString(cipherText);
		}catch(Exception e) {
			System.out.print("Error encrypting using RSA " + e);
			return null;
		}
		
	}

	@Override
	public String decrypt(String cipherText, PrivateKey privateKey) {
		
		try {
			  byte[] bytes = Base64.getDecoder().decode(cipherText);

		      Cipher decriptCipher = Cipher.getInstance("RSA");
		      decriptCipher.init(Cipher.DECRYPT_MODE, privateKey);

		      return new String(decriptCipher.doFinal(bytes));
		}catch(Exception e) {
			System.out.print("Error decrypting using RSA " + e);
			return null;
		}
		
	}

	

}
