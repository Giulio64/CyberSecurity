package key;

import java.security.NoSuchAlgorithmException;

import javax.crypto.KeyGenerator;

public class DESCipherKey extends AbstractCipherKey {
	

	public DESCipherKey(String key) {
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
		
		if(super.getByteArray().length != 8) {
			System.out.println("Invalid DES key: " + super.key);
			return false;
		}
		return true;
	}

	@Override
	public void generateSecureKey() {
		KeyGenerator keyGen;
		try {
			keyGen = KeyGenerator.getInstance("DES");
			keyGen.init(56);
			super.safeKey = keyGen.generateKey();
			super.isKeySafe = true;
		} catch (NoSuchAlgorithmException e) {
			System.out.println("Error generating DES safe key");
			e.printStackTrace();
		}
	}
}
