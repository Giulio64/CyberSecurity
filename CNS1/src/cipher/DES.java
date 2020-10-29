package cipher;

import java.security.Key;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import cipher.AbstractCipher.encriptionWrapper;
import key.DESCipherKey;
import plainText.PlainText;
import key.AESCipherKey;
import key.AbstractCipherKey;
import stopWatch.StopWatch;

/**
 *The Digital Encryption Standard as described in FIPS PUB 46-3.
 */
public class DES extends AbstractCipher{

	public DES(OperatingMode opm) {
		super(opm);
		super.enablePadding();
	}


	@Override
	public encriptionWrapper encript(byte[] plainText, AbstractCipherKey key) {

		StopWatch timer = new StopWatch();

		if(super.print) {
			System.out.println("------------------------------------------------------------");
			System.out.println("Starting " + this.toString() + " encription " + ((DESCipherKey)key).getKeySafenessDescription() + " in " 
					+ super.opm.toString() + " mode." + " (" + this.getMode() + ")");
		}

		try {

			byte[] raw = key.getByteArray();
			Key skeySpec = null;
			
			if(key.isKeySafe) {
				skeySpec = key.safeKey;
			}else {
				skeySpec = new SecretKeySpec(key.getByteArray(), "DES");
			}
			
			Cipher cipher = Cipher.getInstance(getMode());

			cipher.init(Cipher.ENCRYPT_MODE, skeySpec);

			byte[] encriptedArray  = cipher.doFinal(plainText);
			

			if(super.print) {
				System.out.println("Encryption ended. Elapsed Time: " + timer.getSeconds() + "s");
				System.out.println("encrypted message length: " + encriptedArray.length);
				System.out.println("------------------------------------------------------------");

			}

			return new encriptionWrapper(encriptedArray,timer.getSeconds());

		} catch (Exception e) {
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

			byte[] raw = key.getByteArray();
			Key skeySpec = null;
			
			if(key.isKeySafe) {
				skeySpec = key.safeKey;
			}else {
				skeySpec = new SecretKeySpec(raw, "DES");
			}
			
			Cipher cipher = Cipher.getInstance(getMode());

			if(super.opm == OperatingMode.CBC) {

				byte[] iv = new byte[cipher.getBlockSize()];
				IvParameterSpec ivParams = new IvParameterSpec(iv);
				cipher.init(Cipher.DECRYPT_MODE, skeySpec,ivParams);

			}else {
				cipher.init(Cipher.DECRYPT_MODE, skeySpec);
			}

			byte[] decriptedArray  = cipher.doFinal(cipherText);

			if(super.print) {

				System.out.println("Elapsed Time: " + timer.getSeconds() + "s");
				System.out.println("decrypted message length: " + decriptedArray.length);
				System.out.println("------------------------------------------------------------");
			}

			return new encriptionWrapper(decriptedArray,timer.getSeconds());

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(this.toString() + " decryption failed. \n");
			System.out.println("Encryption ended. Elapsed Time: " + timer.getSeconds() + "s");
			System.out.println("------------------------------------------------------------\n");
			return null;
		}
	}

	@Override
	protected String getMode() {
		if(opm == OperatingMode.CBC) {
			if(padding) 
				return "DES/CBC/PKCS5Padding";

			return "DES/CBC/NoPadding";
		}

		else {
			if(padding) 
				return "DES/ECB/PKCS5Padding";

			return "DES/ECB/NoPadding";
		}
	}

	@Override
	public String toString() {
		return "DES";
	}


	/**
	 *Give the average speed of encription of a file
	 */
	public static double getAverageSpeedRateo(byte[] toEncrypt) {
		
		try {
			DES des_cbc = new DES(OperatingMode.CBC);
			des_cbc.print = false;

			DES des_ecb = new DES(OperatingMode.ECB);
			des_ecb.print = false;

			DESCipherKey key = new DESCipherKey("");
			key.generateSecureKey();


			AbstractCipher.encriptionWrapper cbc_encripted= des_cbc.encript(toEncrypt,key);
			AbstractCipher.encriptionWrapper cbc_decripted = des_cbc.decript(cbc_encripted.data,key);

			AbstractCipher.encriptionWrapper ecb_encripted= des_ecb.encript(toEncrypt,key);
			AbstractCipher.encriptionWrapper ecb_decripted = des_ecb.decript(ecb_encripted.data,key);

			double avg_encription = (cbc_encripted.elapsedSeconds + ecb_encripted.elapsedSeconds)/2;
			double avg_decription = (cbc_decripted.elapsedSeconds + ecb_decripted.elapsedSeconds)/2;

			return avg_encription/avg_decription;
	
		} catch (Exception e) {
			return 0.0;
		}
	}


}
