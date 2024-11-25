
public class Data {
	
	private String config;
	private int score;
	//initializes variables for use throughout program
	
	public Data(String config, int score) {
		this.config = config;
		this.score = score;
		//sets local variables with their values
	}
		
	public String getConfiguration() {
		return this.config;
		//returns the configuration of a data piece
	}
	
	public int getScore() {
		return this.score;
		//returns the score of a data piece
	}
	
}
