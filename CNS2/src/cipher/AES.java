package cipher;


import java.math.BigInteger;
import java.security.Key;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

import dictionary.Dictionary;
import key.AbstractCipherKey;
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
	public encriptionWrapper encript(byte[] plainText,AbstractCipherKey key) {
		return null;
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

			
			Cipher dcipher = Cipher.getInstance("AES");
			AESCipherKey aes_key = (AESCipherKey)key;
            
            
			SecureRandom random = new SecureRandom();
			byte[] salt = new byte[16];
			random.nextBytes(salt);

			KeySpec spec = new PBEKeySpec(aes_key.getPassword().toCharArray(), salt, 65536, 192); // AES-192
			SecretKeyFactory f = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
			byte[] secure_key = f.generateSecret(spec).getEncoded();
			SecretKeySpec keySpec = new SecretKeySpec(secure_key, "AES");

            dcipher.init(Cipher.DECRYPT_MODE, keySpec);
            byte[] utf8 = dcipher.doFinal(cipherText);
            

			if(super.print) {

				System.out.println("Encryption ended. Elapsed Time: " + timer.getSeconds() + "s");
				System.out.println("encrypted message length: " + utf8.length);
				System.out.println("------------------------------------------------------------");

			}

			return new encriptionWrapper(utf8,timer.getSeconds());
			

		} catch (Exception e) {
			
	
			if(super.print) {
				System.out.println(e);
				System.out.println(this.toString() + " decryption failed. \n");
				System.out.println("decryption ended. Elapsed Time: " + timer.getSeconds() + "s");
				System.out.println("------------------------------------------------------------\n");
			}
		
			return null;
		}
	}
	


	/*
	 * Try to brute force an encrypted file with AES
	 */
	public bruteForceWrapper bruteForceFile(byte[] encrypted) {
		
		StopWatch watch = new StopWatch();
		Dictionary dic = new Dictionary();
		bruteForceWrapper wrapper = new bruteForceWrapper(null,0.0);
		super.print = false;
		
		System.out.println("Decrypting...");
		
		while(1 == 1) {
		
			if(dic.getWord() == null) {
				wrapper.elapsedSeconds = watch.getSeconds();
				break;
			}
			
			String word = dic.getWord();
			encriptionWrapper enc = decript(encrypted,new AESCipherKey(word));
			
			if(enc != null) {
				if(enc.isPlaintTextValid()) {
					wrapper.data = enc.data;
					wrapper.key = word;
					wrapper.elapsedSeconds = watch.getSeconds();
					break;
				}
			}
				
		}
		super.print = true;
		return wrapper;
	
	}

	@Override
	public String toString() {
		return "AES";
	}

}
