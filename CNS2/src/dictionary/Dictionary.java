package dictionary;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

/*Read a dictionary of words for an offline attack*/
public class Dictionary {
	
	
	private String DICTIONARY_PATH = "/Users/giulioserra/Documents/Universita/CNS2/englishDic.txt";
	private String COMMON_DICTIONARY_PATH = "/Users/giulioserra/Documents/Universita/CNS2/commonWords.txt";
	public boolean loadedFullDictionary = false;
	
	List<String> returnedWords = new ArrayList<String>(); // Allready returned words
	List<String> dictionary = new ArrayList<String>();//All the dictionary
	
	public Dictionary() {
		readCommonWordsDictionary();
	}
	
	/*
	 * Return a word not allready queried by the dictionary 
	 * @return a new word, not returned before, from the dictionary
	 * */
	public String getWord() {
		if(dictionary.size() == 0) {
			System.out.println("no word in dictionary");
			return generateRandomString();
		}
		
		Set<String> difference = new HashSet<String>(dictionary);
		Set<String> returned = new HashSet<String>(returnedWords);
		
		difference.removeAll(returned);
		List<String> words = new ArrayList<String>(difference);
		
		
		if(words.isEmpty() && !loadedFullDictionary)
		{
			System.out.println("Tried all the 10.000 common words, now trying with the full dictionary: \n");
			readCompleteDictionary();
			difference = new HashSet<String>(dictionary);
			returned = new HashSet<String>(returnedWords);
			
			difference.removeAll(returned);
			words = new ArrayList<String>(difference);
			loadedFullDictionary = true;
			
		}else if(words.isEmpty() && loadedFullDictionary) {
			System.out.println("end of the dictionary \n");
			return null;
		}
		
		int randomIndex = (int) Math.floor(Math.random() * words.size());
		returnedWords.add(words.get(randomIndex));
		
		System.out.println(words.size() + " " + "left to try");
		return words.get(randomIndex);
	}	
	
	/*
	 * Return all the words previously returned from the dictionary
	 * @return a list of string already returned from the dictionary
	 * */
	public List<String> getProvidedWords(){
		return returnedWords;
	}
	
	
	
	/*
	 * Parse the a dictionary containing all the commonly used words in english
	 * */
	private void readCommonWordsDictionary() {
		try {
			List<String> parsed =  Files.readAllLines(Paths.get(COMMON_DICTIONARY_PATH), StandardCharsets.UTF_8); 
			dictionary = new ArrayList<String>();
			for(String str : parsed) {
				String word = str.replace("\\", "");
				dictionary.add(word);
			}
			
			
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	/*
	 * Parse the full english dictionary
	 * */
	private void readCompleteDictionary() {
		try {
			List<String> parsed =  Files.readAllLines(Paths.get(DICTIONARY_PATH), StandardCharsets.UTF_8); 
			dictionary = new ArrayList<String>();
			
			for(String str : parsed) {
				String word = str.replace("\\", "");
				dictionary.add(word);
			}
			
			
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	/*
	 * Generate a 192 byte random String 
	 * */
	private String  generateRandomString() {
		byte[] array = new byte[192]; 
	    new Random().nextBytes(array);
	    return new String(array, Charset.forName("UTF-8"));
	}

	
}	
