package cipherText;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

/*Singleton to parse an encrypted file*/
public class cipherText {
	
	
	 private String ENC_PATH = "/Users/giulioserra/Documents/Universita/CNS2/ciphertext.enc";
	
	 private static final cipherText SINGLE_INSTANCE = new cipherText();
	 private cipherText() {}
	 public static cipherText getInstance() {
	    return SINGLE_INSTANCE;
	 }
	 
	 /**
	  * Reads the encrypted document as a byte array
	  * NB:Opens and close automatically the stream
	  * @return all the lines contained in the documents
	  */
	public byte[] getFileArray() throws Exception{
		return Files.readAllBytes(Paths.get(ENC_PATH));
	}
	  
}
