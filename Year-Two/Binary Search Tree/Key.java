
public class Key {
	
	private String label;
	private int type;
	//Private variables for identifying values within the Key

	//Constructor for Key class
	public Key(String theLabel, int theType) {
		this.label = theLabel.toLowerCase();
		this.type = theType;
		
	}
	
	//Gets the label of the key
	public String getLabel() {
		return this.label;
	}
	
	//Gets the type of the key
	public int getType() {
		return this.type;
	}
	
	//lexicographically compares two keys
	public int compareTo(Key k) {
		int cmp=label.compareTo(k.getLabel());
		if(cmp==0) {
			if(type==k.getType()) {
				return 0;
			}else if(type<k.getType()) {
				return -1;
			}else {
				return 1;
			}
		}else {
			return cmp;
		}
	}
	
}
