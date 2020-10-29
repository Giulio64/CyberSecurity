package cipher;


import java.security.Key;
import java.util.ArrayList;
import java.util.List;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import key.AbstractCipherKey;
import plainText.PlainText;
import key.AESCipherKey;
import stopWatch.StopWatch;

/**
 *Advanced Encryption Standard as specified by NIST in FIPS 197. Also known as the Rijndael 
 *algorithm by Joan Daemen and Vincent Rijmen, 
 *AES is a 128-bit block cipher supporting keys of 128, 192, and 256 bits.
 */
public class AES extends cipher.AbstractCipher {


	public AES(OperatingMode opm) {
		super(opm);
		super.enablePadding();
	}


	@Override
	protected String getMode() {
		if(opm == OperatingMode.CBC) {
			if(padding) 
				return "AES/CBC/PKCS5Padding";

			return "AES/CBC/NoPadding";
		}

		else {
			if(padding) 
				return "AES/ECB/PKCS5Padding";

			return "AES/ECB/NoPadding";
		}
	}



	@Override
	public encriptionWrapper encript(byte[] plainText,AbstractCipherKey key){

		StopWatch timer = new StopWatch();

		if(super.print) {
			System.out.println("------------------------------------------------------------");
			System.out.println("Starting " + this.toString() + " encription " + ((AESCipherKey)key).getKeySafenessDescription() + " in " 
					+ super.opm.toString() + " mode." + " (" + this.getMode() + ")");
		}

		try {

			byte[] raw = key.getByteArray();
			
			
			Key skeySpec = null;
			
			if(key.isKeySafe) {
				skeySpec = key.safeKey;
			}else {
				skeySpec = new SecretKeySpec(raw, "AES");
			}
			
			Cipher cipher = Cipher.getInstance(getMode());

			if(super.opm == OperatingMode.CBC) {

				byte[] iv = new byte[cipher.getBlockSize()]; // initialization vector
				IvParameterSpec ivParams = new IvParameterSpec(iv);
				cipher.init(Cipher.ENCRYPT_MODE, skeySpec,ivParams);

			}else {
				cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
			}

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
	public encriptionWrapper decript(byte[]cipherText,AbstractCipherKey key) {

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
				skeySpec = new SecretKeySpec(raw, "AES");
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
			System.out.println("decryption ended. Elapsed Time: " + timer.getSeconds() + "s");
			System.out.println("------------------------------------------------------------\n");
			return null;
		}
	}



	@Override
	public String toString() {
		return "AES";
	}


	/**
	 *Give the average speed of encription of a File
	 */
	public static double getAverageSpeedRateo(byte[] toEncrypt) {

		try {
			AES aes_cbc = new AES(OperatingMode.CBC);
			aes_cbc.print = false;

			AES aes_ecb = new AES(OperatingMode.ECB);
			aes_ecb.print = false;

			AESCipherKey key = new AESCipherKey("");
			key.generateSecureKey();


			AbstractCipher.encriptionWrapper cbc_encripted= aes_cbc.encript(toEncrypt,key);
			AbstractCipher.encriptionWrapper cbc_decripted = aes_cbc.decript(cbc_encripted.data,key);

			AbstractCipher.encriptionWrapper ecb_encripted= aes_ecb.encript(toEncrypt,key);
			AbstractCipher.encriptionWrapper ecb_decripted = aes_ecb.decript(ecb_encripted.data,key);

			double avg_encription = (cbc_encripted.elapsedSeconds + ecb_encripted.elapsedSeconds)/2;
			double avg_decription = (cbc_decripted.elapsedSeconds + ecb_decripted.elapsedSeconds)/2;

			return avg_encription/avg_decription;



		} catch (Exception e) {
			return 0.0;
		}

	}
}
