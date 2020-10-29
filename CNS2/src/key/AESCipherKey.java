package key;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Arrays;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class AESCipherKey extends AbstractCipherKey{

	
	private String SHA_TEC = "SHA-1";
	
	
	public AESCipherKey(String key) {
		super(key);
	}
	
	/**
	 *Return the desription of the safeness of the key(unsafe is user generated)
	 */
	@Override
	public String getKeySafenessDescription() {
		if(isKeySafe) {
			return "(safe key)";
		}else
			return "(unsafe key)";
	}
	
	

	@Override
	public boolean validate() {
		
		if(super.isKeySafe)
			return true;
		
		if(super.getByteArray().length != 16) {
			System.out.println("Invalid AES key: " + super.key);
			return false;
		}
		return true;
	}

	@Override
	public void generateSecureKey() {
		KeyGenerator keyGen;
		try {
			keyGen = KeyGenerator.getInstance("AES");
			keyGen.init(256);
			super.safeKey = keyGen.generateKey();
			super.isKeySafe = true;
		} catch (NoSuchAlgorithmException e) {
			System.out.println("Error generating AES safe key");
			e.printStackTrace();
		}
	}

	public String getPassword() {
		return super.key;
	}

}
