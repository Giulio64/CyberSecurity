package cipher;

import key.AbstractCipherKey;

public abstract class AbstractCipher {


	OperatingMode opm; // operating mode on witch you want to operate your cipher
	protected boolean padding = false;
	protected boolean print = true; //if true print log of the algorithm
	
	
	/**
	 *Wrapper to rappresent the elapsed time 
	 */
	public class encriptionWrapper{
		
		public byte[] data;
		public double elapsedSeconds = 0.0;
		
		public encriptionWrapper(byte[] data,double time) {
			this.data = data;
			this.elapsedSeconds = time;
		}
	}
	
	
	/**
	 *Enable the padding in encription
	 */
	public void enablePadding() {padding = true;}
	
	/**
	 *Disable the padding in encription
	 */
	public void disablePadding() {padding = false;}
	
	/*
	 * Get the full encription cipher + operating mode
	 */
	protected String getMode() {
		return "";
	}
	
	
	public AbstractCipher(OperatingMode opm) {
		this.opm = opm;
	}
	
	/**
	 *Encript the data using the cipher
	 */
	public abstract encriptionWrapper encript(byte[] plainText,AbstractCipherKey key);
	
	/**
	 *Decript the data using the cipher
	 */
	public abstract encriptionWrapper decript(byte[]cipherText,AbstractCipherKey key);
	
	
}
