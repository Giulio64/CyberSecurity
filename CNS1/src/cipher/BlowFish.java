package cipher;

import java.security.Key;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import cipher.AbstractCipher.encriptionWrapper;
import key.BlowFishCipherKey;
import plainText.PlainText;
import key.AESCipherKey;
import key.AbstractCipherKey;
import stopWatch.StopWatch;

/**
 *The Blowfish block cipher designed by Bruce Schneier.
 */
public class BlowFish extends AbstractCipher{

	public BlowFish(OperatingMode opm) {
		super(opm);
		super.enablePadding();
		
	}
	
	@Override
	protected String getMode() {
		if(opm == OperatingMode.CBC) {
			if(padding) 
				return "Blowfish/CBC/PKCS5Padding";

			return "Blowfish/CBC/NoPadding";
		}

		else {
			if(padding) 
				return "Blowfish/ECB/PKCS5Padding";

			return "Blowfish/ECB/NoPadding";
		}
	}

	@Override
	public encriptionWrapper encript(byte[] plainText, AbstractCipherKey key) {
		
		StopWatch timer = new StopWatch();

		if(super.print) {
			System.out.println("------------------------------------------------------------");
			System.out.println("Starting " + this.toString() + " encryption " + ((BlowFishCipherKey)key).getKeySafenessDescription() + " in " 
					+ super.opm.toString() + " mode." + " (" + this.getMode() + ")");
		}
		try {
			
			Key key_s = null;
			
			if(key.isKeySafe) {
				key_s = key.safeKey;
			}else {
				key_s = new SecretKeySpec(key.getByteArray(), "Blowfish");
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
				key_s = new SecretKeySpec(key.getByteArray(), "Blowfish");
			}
			
			Cipher cipher = Cipher.getInstance(getMode());
		     
		    cipher.init(Cipher.ENCRYPT_MODE, key_s);
		    byte[] decripted = cipher.doFinal(cipherText);
		
			if(super.print) {
				System.out.println("Elapsed Time: " + timer.getSeconds() + "s");
				System.out.println("decrypted message length: " + decripted.length);
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
	public String toString(){
		return "BlowFish";
	}

	/**
	 *Give the average speed of encription of a file
	 */
	public static double getAverageSpeedRateo(byte[] toEncrypt) {
		
		try {
			BlowFish blow_cbc = new BlowFish(OperatingMode.CBC);
			blow_cbc.print = false;

			BlowFish  blow_ecb = new BlowFish(OperatingMode.ECB);
			blow_ecb.print = false;

			BlowFishCipherKey key = new BlowFishCipherKey("");
			key.generateSecureKey();


			AbstractCipher.encriptionWrapper cbc_encripted= blow_cbc.encript(toEncrypt,key);
			AbstractCipher.encriptionWrapper cbc_decripted = blow_cbc.decript(cbc_encripted.data,key);

			AbstractCipher.encriptionWrapper ecb_encripted= blow_ecb.encript(toEncrypt,key);
			AbstractCipher.encriptionWrapper ecb_decripted = blow_ecb.decript(ecb_encripted.data,key);

			double avg_encription = (cbc_encripted.elapsedSeconds + ecb_encripted.elapsedSeconds)/2;
			double avg_decription = (cbc_decripted.elapsedSeconds + ecb_decripted.elapsedSeconds)/2;

			return avg_encription/avg_decription;


				
		} catch (Exception e) {
			return 0.0;
		}
		
	}
}
