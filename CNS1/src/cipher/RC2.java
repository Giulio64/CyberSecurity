package cipher;

import java.security.Key;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import cipher.AbstractCipher.encriptionWrapper;
import key.AbstractCipherKey;
import key.BlowFishCipherKey;
import key.RC2CipherKey;
import plainText.PlainText;
import stopWatch.StopWatch;

/*
 * Variable-key-size encryption algorithms developed by Ron Rivest 
 * for RSA Data Security, Inc.
 */
public class RC2 extends AbstractCipher{

	public RC2(OperatingMode opm) {
		super(opm);
		super.enablePadding();
	}
	

	@Override
	protected String getMode() {
		if(opm == OperatingMode.CBC) {
			if(padding) 
				return "RC2/CBC/PKCS5Padding";

			return "RC2/CBC/NoPadding";
		}

		else {
			if(padding) 
				return "RC2/ECB/PKCS5Padding";

			return "RC2/ECB/NoPadding";
		}
	}

	@Override
	public encriptionWrapper encript(byte[] plainText, AbstractCipherKey key) {
		
		StopWatch timer = new StopWatch();

		if(super.print) {
			System.out.println("------------------------------------------------------------");
			System.out.println("Starting " + this.toString() + " encryption " + ((RC2CipherKey)key).getKeySafenessDescription() + " in " 
					+ super.opm.toString() + " mode." + " (" + this.getMode() + ")");
		}
		try {
			
			Key key_s = null;
			
			if(key.isKeySafe) {
				key_s = key.safeKey;
			}else {
				key_s = new SecretKeySpec(key.getByteArray(), "RC2");
			}
			
			Cipher cipher = Cipher.getInstance(getMode());
		        
		    cipher.init(Cipher.ENCRYPT_MODE, key_s);
		    byte[] encripted = cipher.doFinal(plainText);
		    
		    if(super.print) {
				System.out.println("Encryption ended. Elapsed Time: " + timer.getSeconds() + "s");
				System.out.println("encrypted message length: " + encripted.length);
				System.out.println("------------------------------------------------------------");

			}

		    return new encriptionWrapper(encripted,timer.getSeconds());
			
		}catch(Exception e) {
			e.printStackTrace();
			System.out.println(this.toString() + " encryption failed. \n");
			System.out.println("Elapsed Time: " + timer.getSeconds() + "s");
			System.out.println("------------------------------------------------------------");
			return null;
		}
	}

	@Override
	public encriptionWrapper decript(byte[] cipherText, AbstractCipherKey key) {
		
		StopWatch timer = new StopWatch();

		if(super.print) {
			System.out.println("------------------------------------------------------------");
			System.out.println("Starting " + this.toString() + " decryption" + " in " 
					+ opm.toString() + " mode."+ " (" + this.getMode() + ")");
		}

		try {

			Key key_s = null; 
			
			if(key.isKeySafe) {
				key_s = key.safeKey;
			}else {
				key_s = new SecretKeySpec(key.getByteArray(), "RC2");
			}
			
			
			Cipher cipher = Cipher.getInstance(getMode());
		     
		    cipher.init(Cipher.ENCRYPT_MODE, key_s);
		    byte[] decripted = cipher.doFinal(cipherText);
		
			if(super.print) {
				System.out.println("Elapsed Time: " + timer.getSeconds() + "s");
				System.out.println("decrypted message lenght: " + decripted.length);
				System.out.println("------------------------------------------------------------");
			}

			return new encriptionWrapper(decripted,timer.getSeconds());
			

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(this.toString() + " decryption failed. \n");
			System.out.println("Encryption ended. Elapsed Time: " + timer.getSeconds() + "s");
			System.out.println("------------------------------------------------------------\n");
			return null;
		}
	}

	
	@Override
	public String toString() {
		return "RC2";
	}
	
	
	/**
	 *Give the average speed of encription of a file
	 */
	public static double getAverageSpeedRateo(byte[] toEncrypt) {
		
		try {

			RC2 blow_cbc = new RC2(OperatingMode.CBC);
			blow_cbc.print = false;

			RC2  blow_ecb = new RC2(OperatingMode.ECB);
			blow_ecb.print = false;

			RC2CipherKey key = new RC2CipherKey("");
			key.generateSecureKey();



			AbstractCipher.encriptionWrapper cbc_encripted= blow_cbc.encript(toEncrypt,key);
			AbstractCipher.encriptionWrapper cbc_decripted = blow_cbc.decript(toEncrypt,key);

			AbstractCipher.encriptionWrapper ecb_encripted= blow_ecb.encript(PlainText.getInstance().getShortFileBytesArray(),key);
			AbstractCipher.encriptionWrapper ecb_decripted = blow_ecb.decript(ecb_encripted.data,key);

			double avg_encription = (cbc_encripted.elapsedSeconds + ecb_encripted.elapsedSeconds)/2;
			double avg_decription = (cbc_decripted.elapsedSeconds + ecb_decripted.elapsedSeconds)/2;

			return avg_encription/avg_decription;



		} catch (Exception e) {
			return 0.0;
		}
		
	}
}
