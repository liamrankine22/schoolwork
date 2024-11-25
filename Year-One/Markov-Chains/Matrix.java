public class Matrix {

    private int numRows;
    private int numCols;
    private double[][] data;
    // initializes all needed variables

    public Matrix(int r, int c) {
        numRows = r;
        numCols = c;
        data = new double[r][c];
        //initializes the variables when given input by user and makes a matrix off the given information
    }

    public Matrix(int r, int c, double[] linArr) {
        numRows = r;
        numCols = c;
        data = new double[r][c];
        int index = 0;
        for (int i = 0; i < r; i++) {
            for (int p = 0; p < c; p++) {
                data[i][p] = linArr[index++];
                //Initializes the variables given by user and creates a matrix with the size given as well as what should be stored inside it
            }
        }
    }

    public int getNumRows() {
        return numRows;
        //Method that returns the number of rows in the matrix
    }

    public int getNumCols() {
        return numCols;
        //Method that returns the number of columns in the matrix
    }

    public double[][] getData() {
        return data;
        //Method that returns the data of the entire matrix
    }

    public double getElement(int r, int c) {
        return data[r][c];
        //Method that gets a single element in a matrix given the row and column its in
    }

    public void setElement(int r, int c, double value) {
        data[r][c] = value;
        //Method that changes an element at the given address to info the user inputs
    }
    
    public void transpose() {
    	double[][] transposedData = new double[numCols][numRows];
    	//Creates a new array called transposedData that has a switched number of rows and columns
    	for (int i = 0; i < this.numRows; i++) {
    		for (int p = 0; p < this.numCols; p++) {
    			transposedData[p][i] = data[i][p];
    			//Uses 2 loops that don't go over the size of the initial array and adds those values to where they will be transposed
    		}
    	}
    	int temp = numRows;
    	//Stores numRows in a temporary variable so that the matrix doesn't become a square
    	this.numRows = numCols;
    	this.numCols = temp;
    	this.data = transposedData;
    	//Switches all the data to that of the transposed one so that the object is now transposed permanently and can be used by other methods
    }
    
    public Matrix multiply (double scalar) {
    	Matrix multiplication = new Matrix(numRows, numCols);
    	//Creates a new object called multiplication for use within the method
    	for (int i = 0; i < this.numRows; i++) {
    		for (int p = 0; p < this.numCols; p++) {
    			multiplication.data[i][p] = data[i][p] * scalar;
    			//Uses two loops to multiply each element within the data array by the given scalar value and adds it to the nuew multiplication object.
    		}
    	}
    	return multiplication;

    }
    
    public Matrix multiply (Matrix other) {
        if (this.numCols != other.numRows) {
            return null;
            //Checks if the number of columns originally input is equal to another Matrix object's columns
        } else {
            Matrix multiplication = new Matrix(this.numRows, other.numCols);
            //Creates a new object called multiplication for use within the method
            for (int i = 0; i < this.numRows; i++) {
                for (int j = 0; j < other.numCols; j++) {
                    double sum = 0;
                    //Creates a sum value for later use in the new object multiplication
                    for (int k = 0; k < this.numCols; k++) {
                        sum += this.data[i][k] * other.data[k][j];
                        //Multiplies the matrixes so that the top row of the first matrix  by the left column of the second matrix and adds their sums and does  similar for the rest
                        
                    }
                    multiplication.data[i][j] = sum;
                    //Adds the sum of the two multiplicated matrixes and puts them in their corresponding spot
                }
            }
            return multiplication;
            //Returns the multiplicated matrix to the user
        }
    }

    
    public String toString() {
    	if (data.length == 0) {
    		return "Empty matrix";
    		//Checks if the matrix is empty and returns "Empty matrix" if it is
    	}
    	else {
    		StringBuilder stringBuilt = new StringBuilder();
    		for (int i = 0; i < numRows; i++) {
    			for (int p = 0; p < numCols; p++) {
    				stringBuilt.append(String.format("%8.3f", data[i][p]));
    				//Builds the matrix by inputting the data in its corresponding spot given the area it should take up which is 8 and the maximum decimal places which is 3
    			}
    			stringBuilt.append("\n");
    			//When the row ends it adds a new line so that the matrix is formatted properly in a square of rectangle instead of a line
    		}
    		return stringBuilt.toString();
    		//Returns the matrix to the user in string form
    	}
    }

    
}
