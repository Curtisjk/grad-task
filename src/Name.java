/**
 * A basic object class which holds information on a name including the original name,
 * and a pre-processed version of the name (used for searching).
 * @author Curtis Kennington <curtis@curtiskennington.co.uk>
 * @version 1.0
 */
public class Name {

	/** The original (unmodified) name */
	private String name;
	
	/** The pre-processed name */
	private String preprocessedName;
	
	/**
	 * The constructor method which stores the original name in the name variable.
	 * @param inputName
	 */
	public Name(String inputName){
		//store the name
		this.name = inputName;
		
		//Initialise the preProcessedName
		this.preprocessedName = null;
	}
	
	/**
	 * Retrieves the original name.
	 * @return The original name.
	 */
	public String getName(){
		return this.name;
	}
	
	/**
	 * Stores the pre-processed name in the preprocessedName variable.
	 * @param processedInput The pre-processed name.
	 */
	public void setPreprocessedName(String processedInput){
		this.preprocessedName = processedInput;
	}
	
	/**
	 * Retrieves the pre-processed name.
	 * @return The pre-processed name
	 */
	public String getPreprocessedName(){
		return this.preprocessedName;
	}

}
