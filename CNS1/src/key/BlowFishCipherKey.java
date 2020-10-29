package key;

import java.security.NoSuchAlgorithmException;

import javax.crypto.KeyGenerator;

public class BlowFishCipherKey extends AbstractCipherKey {

	public BlowFishCipherKey(String key) {
		super(key);
		
	}

	@Override
	public boolean validate() {
		if(super.isKeySafe) {
			return true;
		}
		if(key.length() > 32) {
			System.out.println("Invalid Blowfish key.");
			return false;
		}
		
		return true;
	}

	@Override
	public String getKeySafenessDescription() {
		if(super.isKeySafe) {
			return "(safe key)";
		}else
			return "(unsafe key)";
	}

	@Override
	public void generateSecureKey() {
		KeyGenerator keyGen;
		try {
			keyGen = KeyGenerator.getInstance("BlowFish");
			keyGen.init(32);
			super.safeKey = keyGen.generateKey();
			super.isKeySafe = true;
		} catch (NoSuchAlgorithmException e) {
			System.out.println("Error generating Blowfish safe key");
			e.printStackTrace();
		}
	}
	

}
