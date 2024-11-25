
public class Record {

	private Key key;
	private String data;
	//Private variables for containing data within Record
	
	//Constructor for Record class
	public Record(Key k, String theData) {
		this.key = k;
		this.data = theData;
	}
	
	//Returns the key of the Record
	public Key getKey() {
		return this.key;
	}
	
	//Returns the data within the record
	public String getDataItem() {
		return this.data;
	}

}
