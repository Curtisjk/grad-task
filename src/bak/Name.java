

public class Name {

	private String name;
	private String preprocessedName;
	
	public Name(String inputName){
		//store the name
		this.name = inputName;
	}
	
	public String getName(){
		return this.name;
	}
	
	public void setPreprocessedName(String processedInput){
		this.preprocessedName = processedInput;
	}
	
	public String getPreprocessedName(){
		return this.preprocessedName;
	}

}
