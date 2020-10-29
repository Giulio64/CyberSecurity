package key;

import java.security.NoSuchAlgorithmException;

import javax.crypto.KeyGenerator;

public class RC2CipherKey extends AbstractCipherKey {

	public RC2CipherKey(String key) {
		super(key);
	
	}

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
		
		if(super.key.length() > 128) {
			System.out.println("Invalid RC2 key.");
			return false;
		}
		
		return true;
	}

	@Override
	public void generateSecureKey() {
		KeyGenerator keyGen;
		try {
			keyGen = KeyGenerator.getInstance("RC2");
			keyGen.init(128);
			super.safeKey = keyGen.generateKey();
			super.isKeySafe = true;
		} catch (NoSuchAlgorithmException e) {
			System.out.println("Error generating RC2 safe key");
			e.printStackTrace();
		}
		
	}

}
