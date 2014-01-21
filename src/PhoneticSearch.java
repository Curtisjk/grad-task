import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * The main method that implements the Phonetic Search. This is done using the SearchEngine class.
 * @author Curtis Kennington <curtis@curtiskennington.co.uk>
 * @version 1.0
 */
public class PhoneticSearch {

	/**
	 * Prints the results of the search. A successful search will print the following:<br>
	 * <br>
	 * Jones: Jonas, Johns, Saunas<br>
	 * <br>
	 * An unsuccessful search will return the following:<br>
	 * <br>
	 * Bones: No results found.
	 * @param term The original search term.
	 * @param results An ArrayList holding the results.
	 */
	private static void printResults(String term, ArrayList<String> results){
		String print = term + ": ";
		
		// Check we have any results
		if( results.size() == 0 ){
			print += "No results found.";
		} else {
			// Iterate through all the results and build up the print string
			for( int i = 0; i < results.size(); i++ ){
				print += results.get(i);
				
				// Check whether this is the last item in the ArrayList. If not, we do not require a comma.
				if((i+1) < results.size()){
					print += ", ";
				}
			}
		}
	
		// Print out the results to the console
		System.out.println(print);
	}
	
	/**
	 * The main method of the program. This expects a number of command line arguments as search terms.
	 * A standard input should be provided to build up a search library. If this is not provided the user
	 * can press enter, and they will be confronted with an error message. Further information can be found
	 * in the readme.
	 * @param args One or more names to search for.
	 */
	public static void main(String[] args) {

		if(args.length == 0){
			System.out.println("You have not entered any search terms.");
			System.out.println("Example usage:\n\nPhoneticSearch Jones Winston Smith < names.txt\n");
			System.out.println("Please try again.");
		} else {
			try{
				BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));
			    String s;
			    ArrayList<String> searchLibrary = new ArrayList<String>();
			    while ( ((s = bufferRead.readLine() ) != null) && (s.trim().length() != 0) ){
			    	searchLibrary.add(s);
			    }
			    
			    // Check whether the input is at least 1 in size, otherwise show an error message
			    if(searchLibrary.size() == 0){
			    	System.out.println("You have not entered or provided any names to be added to the search library. Please provide a file containing names (1 per line) as a standard input.");
			    	System.out.println("Example usage:\n\nPhoneticSearch Jones < names.txt\n");
					System.out.println("Please try again.");
			    } else { // Process the search query.
			    	// Create a new search engine
			    	SearchEngine searchEngine = new SearchEngine(searchLibrary);
			    	
			    	//Process each of the input arguments
				    String[] searchString = args;
					for(String word : searchString){
						printResults(word, searchEngine.search(word));
					}
			    }
			} catch (Exception e) {
				// Display a user-friendly error exception
				System.out.println("An error has occured. Sorry about that - please report all bugs to curtis@curtiskennington.co.uk");
				System.out.println("Error: " + e.getMessage());
			}
		}
	}
}
