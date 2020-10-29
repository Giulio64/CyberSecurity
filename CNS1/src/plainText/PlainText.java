package plainText;

import java.util.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets; 
import java.nio.file.*; 


/**
 *Singleton to read a plainText file.
 */
public class PlainText {
	
	private String SMALL_PATH = "/Users/giulioserra/Documents/Universita/CNS1/100Test.txt";
	private String LARGE_PATH = "/Users/giulioserra/Documents/Universita/CNS1/1MB.txt";
	
	 private static final PlainText SINGLE_INSTANCE = new PlainText();
	 private PlainText() {}
	 public static PlainText getInstance() {
	    return SINGLE_INSTANCE;
	 }
	 
	 /**
	  *Print the 100 KB file line-by-line
	  */
	public void printShortFile() throws Exception {
		
		List<String> lines = getShortFileLines();
	   
	    for(String line : lines ){
	    	System.out.println(line);
	    }
	}
	
	 /**
	  * Reads all the lines inside the documents
	  * NB:Opens and close automatically the stream
	  * @return all the lines contained in the documents
	  */
	public List<String> getShortFileLines() throws Exception{
		return Files.readAllLines(Paths.get(SMALL_PATH), StandardCharsets.UTF_8); 
	}
	
	 /**
	  * Return the small text document as an array of Bytes
	  * @return an array of bytes
	  */
	public byte[] getShortFileBytesArray() throws Exception{
		List<String> lines = getShortFileLines();
		
		StringBuilder sb = new StringBuilder();
		for(String line : lines ){
			sb.append(line);
	    }
		
		return sb.toString().getBytes(Charset.forName("UTF-8"));
	}
	
	/**
	  *Print the 1 MB file line-by-line
	  */
	public void printLargeFile() throws Exception {
		
		List<String> lines = getLargeFileLines();
	   
	    for(String line : lines ){
	    	System.out.println(line);
	    }
	}
	
	 /**
	  * Reads all the lines inside the lerge documents
	  * NB:Opens and close automatically the stream
	  * @return all the lines contained in the documents
	  */
	public List<String> getLargeFileLines() throws Exception{
		return Files.readAllLines(Paths.get(LARGE_PATH), StandardCharsets.UTF_8); 
	}
	
	 /**
	  * Return the large text document as an array of Bytes
	  * @return an array of bytes
	  */
	public byte[] getLargeFileBytesArray() throws Exception{
		List<String> lines = getLargeFileLines();
		
		StringBuilder sb = new StringBuilder();
		for(String line : lines ){
			sb.append(line);
	    }
		
		return sb.toString().getBytes(Charset.forName("UTF-8"));
	}
	
}
