package key;

import java.nio.charset.Charset;
import java.security.Key;


/**
 *Rappresent a key for using in a cipher
 */
public abstract class AbstractCipherKey {
	
	protected String key;
	public boolean isKeySafe;
	public Key safeKey; // safe key generated by an algorithm
	
	
	public AbstractCipherKey(String key) {
		this.key = key;
	}
	
	
	public abstract String getKeySafenessDescription();
	
	/**
	 *Return the byte array that rappresent the key
	 *@return an array of bytes rappresenting the key
	 */
	public byte[] getByteArray() {
		if(key == null) {
			return new byte[0];
		}

		return this.key.getBytes(Charset.forName("UTF-8"));
	}
	
	/**
	 *Generate secure key for the algorithm
	 */
	public abstract void generateSecureKey();
	
	/**
	 *Validate the key to use with an encription method
	 *@return a validation result.
	 */
	public abstract boolean validate();

}
