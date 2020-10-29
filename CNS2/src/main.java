import cipher.AES;
import cipher.AbstractCipher.bruteForceWrapper;
import cipher.AbstractCipher.encriptionWrapper;
import cipher.OperatingMode;
import dictionary.Dictionary;
import key.AESCipherKey;
import key.AbstractCipherKey;

public class main {
	
	public static void main(String[] args) {
		bruteForceFile();
	}	
	
	/*
	 * Tries to brute force the file reading all the possible keys from a dictionary
	 * */
	private static void bruteForceFile() {
		
		System.out.println("--------------------------------------------------------");
		System.out.println("Starting brute force/dictionary attack:");
		try {
			byte[] file = cipherText.cipherText.getInstance().getFileArray();
			bruteForceWrapper enc = new AES(OperatingMode.CBC).bruteForceFile(file);
			
			System.out.println("decription succeded, key : " + enc.getKey() + " elapsed time: " + enc.elapsedSeconds +"s");
			System.out.println("--------------------------------------------------------");
			System.out.println("Decripted message:\n");
			System.out.println(enc.getPlainText());
			
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
}
