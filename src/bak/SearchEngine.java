
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

	private ArrayList<Name> library;
	private static final HashSet<Character> GROUP1 = new HashSet<Character>(Arrays.asList('A','E','I','O','U'));
	private static final HashSet<Character> GROUP2 = new HashSet<Character>(Arrays.asList('C','G','J','K','Q','S','X','Y','Z'));
	private static final HashSet<Character> GROUP3 = new HashSet<Character>(Arrays.asList('B','F','P','V','W'));
	private static final HashSet<Character> GROUP4 = new HashSet<Character>(Arrays.asList('D','T'));
	private static final HashSet<Character> GROUP5 = new HashSet<Character>(Arrays.asList('M','N'));
	private static final HashSet<Character> GROUP6 = new HashSet<Character>(Arrays.asList('L'));
	private static final HashSet<Character> GROUP7 = new HashSet<Character>(Arrays.asList('R'));
	
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
	 * A private method which takes all of the names and instantiates it as a Name class. This method also
	 * performs the pre-processing tasks (Steps 1 - 3) as outlined in the algorithm
	 * @param names An ArrayList<String> a collection of names which will form the basis of
	 * a library used in all search requests.
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
	
	private String preprocessName(String original){
		// Remove non-alphabetical characters from the name
		original = original.replaceAll("[^A-Za-z]", "");
				
		// Convert to uppercase to help us ignore case-sensitivity
		original = original.toUpperCase();
		
		// Remove all occurences of the letters outlined in step 3
		original = original.substring(0,1) + original.substring(1).replaceAll("[AEIHOUWY]","");
		
		// Return the result
		return original;
	}
	
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
				System.out.println(libName.getName() + " - " + libName.getPreprocessedName());
				results.add(libName.getName());
			}
		}
		
		return results;
	}
	
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
				while( (name1.length < (i-1)) && (group1 == getCharacterGroup(name1[i + 1])) ){
					// Tell the for loop to ignore the next character
					i++;
				}
				
				while( (name2.length < (j-1)) && (group2 == getCharacterGroup(name2[j + 1])) ){
					// Tell the for loop to ignore the next character
					j++;
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
		}
		
		return -1;
	}
}
