public class MarkovChain {
	
	private Vector stateVector;
	private Matrix transitionMatrix;
	//Initializes variables to be used throughout the code
	
	public MarkovChain(Vector sVector, Matrix tMatrix) {
		this.stateVector = sVector;
		this.transitionMatrix = tMatrix;
		//Constructor for the variables to be used throughout the code
	}
	
	public boolean isValid() {
		if (transitionMatrix.getNumRows() != transitionMatrix.getNumCols()) {
			return false;
			//Checks the transition matrix is a square and if it is not it returns false
		}
		if (transitionMatrix.getNumCols() != stateVector.getNumCols()) {
			return false;
			//Checks the state vector has the same number of columns as the transition matrix has doesn't matter which side is used for transition matrix because its a square
		}
		double sum = 0.0;
		for (int i=0; i < stateVector.getNumCols(); i++) {
			sum += stateVector.getElement(i);
			//Gets each element from the the column and adds it to a sum
			
		}
		if (sum < 0.99 || sum > 1.01) {
				return false;
				//Checks if the sum is greater than 1.01 or less than 0.99 for a little variation. If it is it returns false
		}
		for (int i =0; i < transitionMatrix.getNumRows(); i++) {
			sum = 0.0;
			for (int p = 0; p < transitionMatrix.getNumCols(); p++) {
				sum += transitionMatrix.getElement(i, p);
				//Gets each element in the transition matrix and addits it to a sum
			}
			if (sum < 0.99 || sum > 1.01) {
				return false;
				//Checks if the sum is greater than 1.01 or less than 0.99 for a little variation. If it is it returns false
			}
		}
		return true;
		//Returns true if all other if statements do not return false
	}
	
	public Matrix computeProbabilityMatrix(int numSteps) {
		if (!isValid()) {
			return null;
			//Returns null if the given Matrix is not valid
		}
		Matrix computed = transitionMatrix;
		for (int i = 1; i < numSteps; i++) {
			computed = computed.multiply(transitionMatrix);
			//Multiplies the transition matrix by itself for numSteps - 1 times since i must be less than numSteps
		}
		return stateVector.multiply(computed);
		//Returns the resulting Matrix object
	}
}
