import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

/**
 * A class for performing all of the required search operations for the Phonetic Search
 * algorithm.
 * @author Curtis Kennington <curtis@curtiskennington.co.uk>
 * @version 1.0
 */
public class SearchEngine {

	/** Holds all of the Name objects forming a search library in the {@link #processInput(String)} function.*/
	private ArrayList<Name> library;
	
	/** A set of equivalent characters (A,E,I,O,U) */
	private static final HashSet<Character> GROUP1 = new HashSet<Character>(Arrays.asList('A','E','I','O','U'));
	
	/** A set of equivalent characters (C,G,J,K,Q,S,X,Y,Z) */
	private static final HashSet<Character> GROUP2 = new HashSet<Character>(Arrays.asList('C','G','J','K','Q','S','X','Y','Z'));
	
	/** A set of equivalent characters (B,F,P,V,W) */
	private static final HashSet<Character> GROUP3 = new HashSet<Character>(Arrays.asList('B','F','P','V','W'));
	
	/** A set of equivalent characters (D,T) */
	private static final HashSet<Character> GROUP4 = new HashSet<Character>(Arrays.asList('D','T'));
	
	/** A set of equivalent characters (M,N) */
	private static final HashSet<Character> GROUP5 = new HashSet<Character>(Arrays.asList('M','N'));
	
	/** A set containing a single non-comparable character (L) */
	private static final HashSet<Character> GROUP6 = new HashSet<Character>(Arrays.asList('L'));
	
	/** A set containing a single non-comparable character (R) */
	private static final HashSet<Character> GROUP7 = new HashSet<Character>(Arrays.asList('R'));
	
	/** A set containing a single non-comparable character (H) */
	private static final HashSet<Character> GROUP8 = new HashSet<Character>(Arrays.asList('H'));
	
	/**
	 * Constructor method for SearchEngine. This is initialised with an ArrayList of names 
	 * which will is pre-processed using {@link #processInput(java.util.ArrayList<java.lang.String>)} and 
	 * {@link #preprocessName(String)}.
	 * 
	 * @param names An ArrayList<String> a collection of names which will form the basis of
	 * a library used in all search requests.
	 */
	SearchEngine(ArrayList<String> names){
		// Process the names
		processInput(names);
	}
	
	/**
	 * A private method which takes all of the names and instantiates it as a Name object. This method also
	 * performs the pre-processing tasks (Steps 1 - 3) as outlined in the algorithm using the 
	 * {@link #preprocessName(String)} and stores the result in the created Name object. This Name object is 
	 * then added to the "library" ArrayList.
	 * 
	 * @param names A collection of names which will form the basis of a library used in all search requests.
	 */
	private void processInput(ArrayList<String> names){
		// Initialise the library object
		library = new ArrayList<Name>();
		
		// Process all the names passed to the constructor
		for(String name : names){
			// Create a net Name object to store the name
			Name tempName = new Name(name);
			
			// Pre-process it and store the result in the Name object.
			tempName.setPreprocessedName(this.preprocessName(name));
			
			//Add it to the library
			library.add(tempName);
		}
	}
	
	/**
	 * Performs the pre-processing operations on the provided name string as per steps 1-3 in the algorithm:<br>
	 * <br>
	 * STEP 1: Filter out non-alphabetical characters using a regular expression.<br>
	 * STEP 2: Convert all characters to upper-case to facilitate case-insensitivity.<br>
	 * STEP 3: Remove all instances of A,E,I,H,O,U,W,Y after the first letter using a regular expression.
	 * @param original The original name string to be pre-processed.
	 * @return The pre-processed string.
	 */
	private String preprocessName(String original){
		// Remove non-alphabetical characters from the name
		original = original.replaceAll( "[^A-Za-z]", "" );
				
		// Convert to uppercase to help us ignore case-sensitivity
		original = original.toUpperCase();
		
		// Remove all occurences of the letters outlined in step 3
		original = original.substring(0,1) + original.substring(1).replaceAll("[AEIHOUWY]","");
		
		// Return the result
		return original;
	}
	
	/**
	 * Performs the Phonetic Search algorithm on the provided term. This method will first of all convert 
	 * the search term into a Name object and pre-process it. It then applies the second part of the search 
	 * algorithm on each name in the library individually (the first half has already been applied via 
	 * pre-processing - see {@link #preprocessName(String)}). The second part of the algorithm is implemented
	 * in {@link #compare(Name, Name)}.
	 * @param term The name to find a match for.
	 * @return An ArrayList containing matches (if any).
	 */
	public ArrayList<String> search(String term){
		// Take the search term and store it in a Name object. Then pre-process ready for comparison
		Name searchTerm = new Name(term);
		searchTerm.setPreprocessedName(this.preprocessName(term));
		
		// Create an ArrayList to store the matching results
		ArrayList<String> results = new ArrayList<String>();
		
		// Iterate over every name stored in the library
		for ( Name libName : library ){
			// Check to see if the names are equal according to the algorithm
			if( compare(libName, searchTerm) ){
				// We have a match - add it to our results ArrayList
				results.add(libName.getName());
			}
		}
		
		return results;
	}
	
	/**
	 * Compares 2 names against eachother using steps 4-5 of the algorithm. If the 2 names match
	 * true will be returned. False will indicate an invalid match.<br>
	 * <br>
	 * STEP 4: Identify the equivalent characters. A match is verified using {@link #getCharacterGroup(char)}
	 * within a for loop checking a character array of both names. If a match is found, the function 
	 * continues onto step 5. Otherwise false is returned.<br>
	 * STEP 5: Identify any consecutive occurrences of equivalent letters and consider them as a single
	 * occurance. This is done by iterating through the remainder of both word character arrays in
	 * conjunction with {@link #getCharacterGroup(char)}. If a match is found, the array index is increased
	 * meaning it will be skipped in the next iteration of the for loop.<br>
	 * <br>
	 * The method finishes by checking the number of characters compared for each name to ensure it
	 * matches the length of the original name. If this fails, we assume there is extra character(s)
	 * in the name, and return false to indicate a failed comparison. If all of these checks are passed,
	 * we have a match and true is returned.
	 * @param n1 A word to be compared to another.
	 * @param n2 A word to be compared to another.
	 * @return True to indicate a successful comparison. Failed matchings will return false.
	 */
	private boolean compare(Name n1, Name n2){
		
		// Get character arrays of both names for easier comparison
		char[] name1 = n1.getPreprocessedName().toCharArray();
		char[] name2 = n2.getPreprocessedName().toCharArray();
		int i, j;
		
		// Iterate through each character of both words and compare them
		for( i = j = 0; (i < name1.length) && (j < name2.length); i++, j++ ){
			
			// Store a local copy of the character group, this will be used multiple times
			int group1 = getCharacterGroup(name1[i]);
			int group2 = getCharacterGroup(name2[j]);
			
			// Check if the groups match, if so carry on comparing
			if( group1 == group2 ){	
				// Now check if the next character for both words is part of the same group
				while( name1.length > (i+1) ){
					if(group1 == getCharacterGroup(name1[i + 1])){
						// Tell the for loop to ignore the next character
						++i;
					} else {
						break;
					}
				}
				
				while( name2.length > (j+1) ){
					if(group2 == getCharacterGroup(name2[j + 1])){
						// Tell the for loop to ignore the next character
						++j;
					} else {
						break;
					}
				}
				
			} else { 
				return false; // They do not match - this word has failed, return false.
			}
			
		} // end for
		
		// A further check must be performed to check there is no remaining unchecked characters
		if( (i < name1.length) || (j < name2.length) ){
			// There are remaining characters, the word has failed
			return false;
		} else {
			// Otherwise we have a match!
			return true;
		}
	}
	
	/**
	 * A simple function which matches the character in the parameter to its matching HashSet.
	 * If it matches a set, an integer between 1-8 will be returned, indicating the set it belongs
	 * to. If a match cannot be found -1 will be returned.
	 * @param c The character to be matched to a group.
	 * @return An integer between 1-8 for successful matchings. -1 if no matching set can be found.
	 */
	private int getCharacterGroup(char c){
		// Check to see which HashSet contains the character
		if ( GROUP1.contains( c ) ) {
			return 1;
		} else if ( GROUP2.contains(c) ) {
			return 2;
		} else if ( GROUP3.contains(c) ) {
			return 3;
		} else if ( GROUP4.contains(c) ) {
			return 4;
		} else if ( GROUP5.contains(c) ) {
			return 5;
		} else if ( GROUP6.contains(c) ) {
			return 6;
		} else if ( GROUP7.contains(c) ) {
			return 7;
		} else if ( GROUP8.contains(c) ) {
			return 8;
		}
		
		// Return -1 for any failed matchings 
		//(this should not occur as all alphabetical characters have been declared in a HashSet)
		return -1;
	}
}
