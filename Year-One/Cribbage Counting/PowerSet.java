public class PowerSet<T> {
	Set<T>[] set;
	//Initializes set of the Set class
	
	public PowerSet(T[] elements) {
		int n = elements.length;
		//Creates int varible n containing the length of the input array
		int numSets = (int) Math.pow(2,n);
		//Calculates the number of subsets that can be formed from the input array
		set = new Set[numSets];
		//Creates an array of type Set with a length of the number of possible subsets
		
		for (int i = 0; i < numSets; i++) {
			String binary = Integer.toBinaryString(i);
			//Converts each binary number to a string of length n
			while (binary.length() < n) {
				binary = "0" + binary;
			}
			Set<T>  newSet = new Set<T>();
			for (int p = 0; p < n; p++) {
				if (binary.charAt(p) == '1') {
					newSet.add(elements[p]);
					//Adds all the elements from the input array that are included in the subset. And stores the new Set object in the
					//corresponding index of the set array
				}
			}
			set[i] = newSet;
			//array containing all possible subset of the input array
		}
	}
	
	public int getLength() {
		 return set.length;
		 //Method that returns the length of the array containing all possible subsets
	}
	
	public Set<T> getSet(int i){
		return set[i];
		//Method that returns the specific set in the place of i
	}
	
}
