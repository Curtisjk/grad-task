
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class PhoneticSearch {

	private static void printResults(String term, ArrayList<String> results){
		String print = term + ": ";
		
		// Iterate through all the results and build up the print string
		for( int i = 0; i < results.size(); i++ ){
			print += results.get(i);
			
			// Check whether this is the last item in the ArrayList. If not, we do not require a comma.
			if((i+1) < results.size()){
				print += ", ";
			}
		}
		
		// Print out the results to the console
		System.out.println(print + "\n");
	}
	
	public static void main(String[] args) {

		try{
			BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));
		    String s;
		    ArrayList<String> searchLibrary = new ArrayList<String>();
		    while ((s=bufferRead.readLine())!=null){
		    	searchLibrary.add(s);
		    } 
		    
		    SearchEngine searchEngine = new SearchEngine(searchLibrary);
		    String[] searchString = args;
			for(String word : searchString){
				printResults(word, searchEngine.search(word));
			}
		} catch (Exception e) {
			
		}
		
	}

}
