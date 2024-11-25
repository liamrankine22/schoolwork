public class Vector extends Matrix{
	
	
	public Vector(int c) {
		super(1, c);
		//Initializes Vector objects using the constructor within Matrix class
	}
	
	public Vector(int c, double[] linArr) {
		super(1, c, linArr);
		//Initializes Vector objects using the constructor within Matrix class
	}
	
	public double getElement(int c) {
		return super.getElement(0,c);
		//Returns the element of the Vector object using the getElement method within the Matrix class
	}
}
