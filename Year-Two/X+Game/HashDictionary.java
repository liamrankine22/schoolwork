import java.util.LinkedList;
//imports the linkedlist class for use in program

public class HashDictionary implements DictionaryADT {
	
	private LinkedList<Data>[] Table;
	private int tSize;
	private int numRecords;
	//initializes variables for use within program.
	
	public HashDictionary(int size) {
		
		this.tSize = size;
		//sets local variables with their values
		
		Table = new LinkedList[size];
		for(int i = 0;i < size;i++) {
			Table[i] = new LinkedList<Data>();
		}
		//creates a linkedlist called table and initializes a linked list for all indexes within the table to create a hash table
		
	}
	
	public int polyHashFunction(String input){
		
		int n = input.length();
		int hash = 0;
		
		for (int i = 0; i < n; i++) {
			hash = (hash * 11 + (int)input.charAt(i)) % tSize;
		}
		
		return hash;
		//we iterate through the characters of the input string and apply the Horner's method to compute the polynomial hash. 
		//The prime number "p" is chosen to reduce the chance of collisions, 
		//and the modulo operation ensures that the hash code falls within the desired range
	}
	
	
	public int put(Data record) throws DictionaryException{
		int hash = polyHashFunction(record.getConfiguration());
		if(Table[hash].isEmpty()) {
			Table[hash].add(record);
			numRecords++;
			return 0;
			//First checks if the Table at index hash is empty, if so it adds it to that index's linked list 
			//and increases the number of records
		} else {
			for(Data data : Table[hash]) {
				if (data.getConfiguration().equals(record.getConfiguration())){
					throw new DictionaryException();
				}
				//If it isnt the only value within the index of hash it goes through all values within the index hash and checks if
				//theyre equal to the inputted value, if so it throws a dictionary exception
			}
			Table[hash].add(record);
			numRecords++;
			//if both if both statements don't execute it adds the input record to the linked list at index hash
			
		}
		
		return 1;
	}
	
	public void remove(String config) throws DictionaryException{
		int hash = polyHashFunction(config);
		LinkedList<Data> list = Table[hash];
		
		if (list != null) {
			for (Data data : list) {
				if (data.getConfiguration().equals(config)) {
					list.remove(data);
					numRecords--;
					return;
					//Checks if the list is not empty if so it searches the index hash for the same value and if it finds it
					//its removed and decreases the number of records
				}
			}
		} 
		throw new DictionaryException();
		//If it cannot find the same value within the hash index it throws an exception
	}
	
	public int get(String config) {
		int hash = polyHashFunction(config);
		LinkedList<Data> list = Table[hash];
		if (!list.isEmpty()) {
			for (Data data : list) {
				if (data.getConfiguration().equals(config)) {
					return data.getScore();
				}
				//Checks if the list is not empty, if true it searches all pieces of data at index hash and compares configurations
				//If it finds the same value it returns the score to the user
			}
		} else {
			return -1;
		}
		return -1;
		//if its unable to find the same value it returns -1 to the user
	}
	
	public int numRecords() {
		return numRecords;
		//returns the number of records to the user
	}

}
