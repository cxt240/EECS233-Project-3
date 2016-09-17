/**
 * The class holding the fields of the string and the frequency
 * @author Chris Tsuei
 */
public class StringCounter{

	/* the fields of this class
	 * name is the name of the string
	 * frequency is the number of times this string has shown up
	 */
	public String name;
	public int frequency;
	public StringCounter next;
	
	/**
	 * the constructor for this class
	 * @param input the input string containing the stats
	 */
	public StringCounter(String input) {
		this.name = input;
		frequency = 1;
		next = null;
	}
}
