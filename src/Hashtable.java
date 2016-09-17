import java.io.*;
/**
 * Hashtable holding the StringCounter nodes
 * @author Chris Tsuei
 */
public class Hashtable {

	/* The fields of this hashtable
	 * wordCount holds the individual words and their stats
	 * elements is the number of StringCounters in the table
	 * maxSize is the maximum amount of elements in the table
	 * word is the number of words in the file
	 * indicies is the number of indexes used
	 */
	public StringCounter [] wordCount;
	public int elements;
	public int maxSize;
	public int word;
	public int indicies;
	
	/**
	 * the constructor of this class
	 * sets the length of the array and the maxSize as 512
	 * sets the number of elements as currently 0;
	 */
	public Hashtable() {
		wordCount = new StringCounter[512];
		elements = 0;
		maxSize = 512;
		word = 0;
		indicies = 0;
	}
	
	/**
	 * adding a String to the hashtable
	 * precondition: the current string is all lowercase with no punctuation
	 * @param current the string to be entered into the hashtable
	 */
	public void add(String current) {
		//the hashcode of the current string
		int position = Math.abs((current.hashCode() % maxSize));
		StringCounter nodeptr = wordCount[position];
		boolean repeat = false;
		word++;
		
		//if the current position in the table is null
		if(nodeptr == null) {
			wordCount[position] = new StringCounter(current);
			elements++;
			indicies++;
		}
		
		// goes through the current linkedlist till it hits a null then adds the new pointer
		else {
			StringCounter previous = nodeptr;
			while(nodeptr != null && !repeat) {
				//if there is a duplicate word
				if(nodeptr.name.equals(current)) {
					nodeptr.frequency++;
					repeat = true;
				}
				//going to the next node
				else {
					previous = nodeptr;
					nodeptr = nodeptr.next;
				}
			}
			
			//if there was no repeat
			if(repeat != true) {
				//adds a new node to the list
				previous.next = new StringCounter(current);
				elements++;
			}
		}
	}
	
	/**method to check that the current table isn't 75% full */
	public void checkload() {
		if(indicies > (maxSize * .75)) {
			rehash();
		}
	}
	
	/**
	 * method to rehash the entire table
	 */
	public void rehash() {
		//creates a new array twice as big as the old one
		StringCounter[] newHash = new StringCounter[maxSize * 2];
		int indicies2 = 0;
		// goes through the entire array looking for elements
		for(int i = 0; i < wordCount.length; i++) {
			
			// if the current index is filled
			if(wordCount[i] != null) {
				
				StringCounter nodeptr = wordCount[i];
				
				//goes through and rehashes the entire linked list till it reaches null
				while(nodeptr != null) {
					int position = Math.abs((nodeptr.name.hashCode() % (maxSize * 2)));
					
					//if the new position is blank
					if(newHash[position] == null) {
						newHash[position] = nodeptr;
						indicies2++;
					}
					
					//otherwise go through the new Hashtable's linked list till you hit null
					else {
						StringCounter nodeptr2 = newHash[position];
						StringCounter previous = nodeptr2;
						//goes through the new table's list till it hits a null
						while(nodeptr2 != null) {							
							previous = nodeptr2;
							nodeptr2 = nodeptr2.next;
						}
						previous.next = nodeptr;
						previous.next.next = null;
					}
					nodeptr = nodeptr.next;
				}
			}
		}
		
		//increases maxSize and sets the new table to be the field
		wordCount = newHash;
		maxSize = maxSize * 2;
		indicies = indicies2;
	}
	
	/**
	 * the method that prints all of the elements of this array
	 * @param newVersion filewriter which will write the output file
	 * @throws Exception if there is no filewriter
	 */
	public void printTable(FileWriter output) throws Exception {
		
		//loops through wordCount looking for a StringCounter
		for(int i = 0; i < maxSize; i++) {
			StringCounter nodeptr = wordCount[i];
			while(nodeptr != null) {
				// writes the fields in the form (word frequency) into the output file
				output.write("(" + nodeptr.name + " " + nodeptr.frequency + ")" + System.getProperty("line.separator"));
				nodeptr = nodeptr.next;
			}
		}
		
		//printing out all of the stats for this program
		System.out.println("Size: " + maxSize);
		System.out.println("Words: " + word);
		System.out.println("Unique Words: " + elements);
		System.out.println("Indicies used: " + indicies);
		System.out.println("Average length: " + (double) (elements/maxSize));
	}
}
