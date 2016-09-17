import java.io.*; 
/**
 * The class containing the main method for project 3 of EECS 233
 * @author Chris Tsuei
 */
public class WordCounter {

	// hashtable containing all of the words of this file
	private static Hashtable wordCount;
	
	/**
	 * main method of this class
	 * @param args the input filenames
	 * @throws Exception if there are no file names
	 */
	public static void main(String [] args) throws Exception{
		try {
			WordCounter.countWords("C:\\Users\\Chris\\workspace\\P3_cxt240_Tsuei\\src\\test.txt", "C:\\Users\\Chris\\workspace\\P3_cxt240_Tsuei\\src\\output.txt");
		}
		catch (IndexOutOfBoundsException c){
			System.out.println("Array index not found");
		}
	}
	
	/**
	 * goes through the input file and puts each word into the hashtable,
	 * incrementing up the frequency if there is a repeat
	 * prints out the statistic of word counts
	 * @param input_File the input file of words to be counted
	 * @param output_File the output file for statistics to be written in
	 * @throws Exception if the input file or the output file doesn't exist
	 */
	public static void countWords(String input_File, String output_File) throws Exception {
		
		wordCount = new Hashtable();
	
		// the file reader for the current word to be put into the hashtable
		FileReader inputFile = new FileReader(input_File);
		String next = nextWord(inputFile);
		
		// loops through the file and creates the nodes containing all present characters and frequencies
		while(inputFile.ready()) {
			next = nextWord(inputFile);
			if(next != null) {
				wordCount.add(next);
				wordCount.checkload();
			}
		}
		inputFile.close();
		
		// creates the output file for this class
		FileWriter output = new FileWriter(output_File);
		wordCount.printTable(output);
		output.close();
	}
	
	/**
	 * helper method to find the next word
	 * @param input the filereader that reads the input file
	 * @return the string in all lowercase letters without punctuation or symbols
	 * @throws Exception if there is no filereader
	 */
	private static String nextWord(FileReader input) throws Exception{
		
		/* current is the string that is being read from the filereader
		 * finishword will be true only if the filereader hits a punctuation mark, symbol or space
		 * now is the current character
		 */
		StringBuilder current = new StringBuilder();
		char now;
		String word = null;
		
		// loops throught the file and returns the word as a stringbuilder
		while(((now = (char) input.read()) != (char) -1) && ((now >= 97 && now <= 122) || (now >= 48 && now <= 57) || (now >= 65 && now <= 90))) {
			current.append(now);
		}

		word = current.toString();
		
		//if the result wasn't nothing
		if(!word.equals("")) {
			return word.toLowerCase();
		}
		//otherwise returns hnull
		else {
			return null;
		}
	}
}
