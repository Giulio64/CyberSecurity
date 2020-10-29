import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.TimeUnit;

import javax.crypto.SecretKey;

import plainText.PlainText;
import cipher.AES;
import cipher.BlowFish;
import cipher.DES;
import cipher.OperatingMode;
import cipher.RC2;
import key.AESCipherKey;
import key.BlowFishCipherKey;
import key.DESCipherKey;
import key.RC2CipherKey;



public class main {

	public static void main(String[] args) {
		
		aesCBC();
		aesEBC();
		
		averageAES();
		
		desCBC();
		desECB();
		
		averageDES();
		
		BlowfishCBC();
		BlowfishECB();
		
		averageBlowFish();
		
		RC5CBC();
		RC5ECB();
		
		averageRC2();
		
	}
	

	/**
	 *Encript the plaint text using aesCBC
	 */
	private static void aesCBC() {
		
		AES aes = new AES(OperatingMode.CBC);
		try {
			AESCipherKey key = new AESCipherKey("");
			key.generateSecureKey();
			
			byte[] encripted = aes.encript(PlainText.getInstance().getShortFileBytesArray(),key).data;
			byte[] decripted = aes.decript(encripted,key).data;
			
			System.out.println("Now e/d AES with a bigger file:");
			
			byte[] s_encripted = aes.encript(PlainText.getInstance().getLargeFileBytesArray(),key).data;
			byte[] s_decripted = aes.decript(s_encripted,key).data;
			
				
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	/**
	 *Encript the plaint text using aesEBC
	 */
	private static void aesEBC() {
		
		AES aes = new AES(OperatingMode.ECB);
		try {
			AESCipherKey key = new AESCipherKey("");
			key.generateSecureKey();
			byte[] encripted = aes.encript(PlainText.getInstance().getShortFileBytesArray(),key).data;
			byte[] decripted = aes.decript(encripted,key).data;
			
			System.out.println("Now e/d AES with a bigger file:");
			
			byte[] s_encripted = aes.encript(PlainText.getInstance().getLargeFileBytesArray(),key).data;
			byte[] s_decripted = aes.decript(s_encripted,key).data;
				
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	
	}
	
	/**
	 *Get the average speed of AES
	 */
	private static void averageAES() {
		System.out.println("------------------------------------------------------------");
		try {
			System.out.println("Average AES rateo(Small) " + AES.getAverageSpeedRateo(PlainText.getInstance().getShortFileBytesArray()));
			System.out.println("Average AES rateo(Big) " + AES.getAverageSpeedRateo(PlainText.getInstance().getLargeFileBytesArray()));
		} catch (Exception e) {
			System.out.println("Error trying getting AES E/D rateo");
			
		}
		System.out.println("------------------------------------------------------------");
	}
	
	
	/**
	 *Encript the plaint text using desCBC
	 */
	private static void desCBC() {
		
		DES des = new DES(OperatingMode.CBC);
		try {
			DESCipherKey key = new DESCipherKey("");
			key.generateSecureKey();
			
			byte[] encripted = des.encript(PlainText.getInstance().getShortFileBytesArray(),key).data;
			byte[] decripted = des.decript(encripted,key).data;
			
			System.out.println("Now e/d DES with a bigger file:");
		
			byte[] s_encripted = des.encript(PlainText.getInstance().getLargeFileBytesArray(),key).data;
			byte[] s_decripted = des.decript(s_encripted,key).data;
				
		} catch (Exception e) {
			e.printStackTrace();
		}	
	}
	
	/**
	 *Encript the plaint text using desECB
	 */
	private static void desECB() {
		DES des = new DES(OperatingMode.ECB);
		try {
			DESCipherKey key = new DESCipherKey("");
			key.generateSecureKey();
			
			byte[] encripted = des.encript(PlainText.getInstance().getShortFileBytesArray(),key).data;
			byte[] decripted = des.decript(encripted,key).data;
			
			System.out.println("Now e/d DES with a bigger file:");
			
			byte[] s_encripted = des.encript(PlainText.getInstance().getLargeFileBytesArray(),key).data;
			byte[] s_decripted = des.decript(s_encripted,key).data;
				
		} catch (Exception e) {
			e.printStackTrace();
		}	
	}
	
	
	/**
	 *Get the average speed of AES
	 */
	private static void averageDES() {
		System.out.println("------------------------------------------------------------");
	
		try {
			System.out.println("Average DES rateo(Small) " +  DES.getAverageSpeedRateo(PlainText.getInstance().getShortFileBytesArray()));
			System.out.println("Average DES rateo(Big) " +  DES.getAverageSpeedRateo(PlainText.getInstance().getLargeFileBytesArray()));
		} catch (Exception e) {
			System.out.println("Error trying getting DES E/D rateo");
			
		}
		
		System.out.println("------------------------------------------------------------");
		
	}
	
	/**
	 *Encript the plaint text using blowfish CBC
	 */
	private static void BlowfishCBC() {
		BlowFish blowfish = new BlowFish(OperatingMode.CBC);
		try {
			BlowFishCipherKey key = new BlowFishCipherKey("");
			key.generateSecureKey();
			
			byte[] encripted = blowfish.encript(PlainText.getInstance().getShortFileBytesArray(),key).data;
			byte[] decripted = blowfish.decript(encripted,key).data;
			
			
			System.out.println("Now e/d Blowfish with a bigger file:");
			
			byte[] s_encripted = blowfish.encript(PlainText.getInstance().getShortFileBytesArray(),key).data;
			byte[] s_decripted = blowfish.decript(s_encripted,key).data;
				
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 *Encript the plaint text using blowfish ECB
	 */
	private static void BlowfishECB() {
		BlowFish blowfish = new BlowFish(OperatingMode.ECB);
		try {
			BlowFishCipherKey key = new BlowFishCipherKey("");
			key.generateSecureKey();
			
			byte[] encripted = blowfish.encript(PlainText.getInstance().getShortFileBytesArray(),key).data;
			byte[] decripted = blowfish.decript(encripted,key).data;
			
			System.out.println("Now e/d Blowfish with a bigger file:");
			
			byte[] s_encripted = blowfish.encript(PlainText.getInstance().getShortFileBytesArray(),key).data;
			byte[] s_decripted = blowfish.decript(s_encripted,key).data;
				
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 *get the average speed of blowfish
	 */
	private static void averageBlowFish() {
		System.out.println("------------------------------------------------------------");
		
		try {
			System.out.println("Average Blowfish rateo(Small) " +  BlowFish.getAverageSpeedRateo(PlainText.getInstance().getShortFileBytesArray()));
			System.out.println("Average Blowfish rateo(Big) " +  BlowFish.getAverageSpeedRateo(PlainText.getInstance().getLargeFileBytesArray()));
		} catch (Exception e) {
			System.out.println("Error trying getting Blowfish E/D rateo");
			
		}
		
		System.out.println("------------------------------------------------------------");
		
	}
	
	/**
	 *Encript the plaint text using RC5
	 */
	private static void RC5CBC() {
		RC2 rc2 = new RC2(OperatingMode.CBC);
		try {
			RC2CipherKey key = new RC2CipherKey("");
			key.generateSecureKey();
			
			byte[] encripted = rc2.encript(PlainText.getInstance().getShortFileBytesArray(),key).data;
			byte[] decripted = rc2.decript(encripted,key).data;
			
			
			System.out.println("Now decrypting RC2 with a safe key:");
			
			byte[] s_encripted = rc2.encript(PlainText.getInstance().getShortFileBytesArray(),key).data;
			byte[] s_decripted = rc2.decript(s_encripted,key).data;
				
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 *Encript the plaint text using RC5
	 */
	private static void RC5ECB() {
		RC2 rc2 = new RC2(OperatingMode.ECB);
		try {
			RC2CipherKey key = new RC2CipherKey("");
			key.generateSecureKey();
			
			byte[] encripted = rc2.encript(PlainText.getInstance().getShortFileBytesArray(),key).data;
			byte[] decripted = rc2.decript(encripted,key).data;
			
			System.out.println("Now e/d RC2 with a bigger file:");
			
			byte[] s_encripted = rc2.encript(PlainText.getInstance().getShortFileBytesArray(),key).data;
			byte[] s_decripted = rc2.decript(s_encripted,key).data;
				
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 *get the average speed of RC5
	 */
	private static void averageRC2() {
		System.out.println("------------------------------------------------------------");
		try {
			System.out.println("Average RC2 rateo(Small) " +  RC2.getAverageSpeedRateo(PlainText.getInstance().getShortFileBytesArray()));
			System.out.println("Average RC2 rateo(Big) " +  RC2.getAverageSpeedRateo(PlainText.getInstance().getLargeFileBytesArray()));
		} catch (Exception e) {
			System.out.println("Error trying getting RC2 E/D rateo");
			
		}
		System.out.println("------------------------------------------------------------");
	}
	
	
}
