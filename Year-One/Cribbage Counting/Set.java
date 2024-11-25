
public class Set<T> {
	private LinearNode<T> setStart;
	//Initializes setStart
	
	public Set() {
		this.setStart = null; //Sets setStart to Null
	}
	
	public void add(T element) {
		LinearNode<T> newNode = new LinearNode<>(element);
		if (setStart == null) {
			setStart = newNode;
			//Makes a new LinearNode called newNode and checks if the start is empty
			//If it is it sets the start as the newNode
			
		}
		else if (!contains(element)) {
			newNode.setNext(setStart);
			setStart = newNode;
			//If it isn't it sets the start to the next node and makes the newNode the new start
		}
		
	}
	
	public int getLength() {
		int count = 0;
		LinearNode<T> current = setStart;
		while (current != null) {
			count++;
			current = current.getNext();
		}
		return count;
		//Checks if the start of the set is not null and counts for each non null node until it reaches the end of the set which is null
	}
	
	public T getElement(int i) {
		LinearNode<T> current = setStart;
		int count = 0;
		while (current != null&&count<i) {
			current = current.getNext();
			count++;
			//Checks if the starting node is null and under which node value asked for and if count is under the asked number it moves
			//to the next node and so on until it finds which element is 1 less than i
		}
		if (current == null) {
			return null;
			//If current is equal to null it returns null
		}
		else {
			return current.getElement();
			//Otherwise it returns the element in the stack
		}
	}
	
	public boolean contains(T element) {
		LinearNode<T> current = setStart;
		while (current != null) {
			if (current.getElement().equals(element)) {
				return true;
				//Creates new node current with the value of the start and while the node current isnt null it checks if the element
				//is equal to the asked one and returns true if it is otherwise gets the next element and tries again. If it never
				//finds a matching element, it returns false
			}
			current = current.getNext();
		}
		return false;
	}
	
	public String toString() {
		StringBuilder stringBuilt = new StringBuilder();
		LinearNode<T> current = setStart;
		while (current != null) {
			stringBuilt.append(current.getElement().toString()).append(" ");
			current = current.getNext();
			//Makes a new node current and checks if current is not null, if it isnt it adds it's element to the stringbuilder and 
			//adds a space at the end and goes until current has no more elements then returns it
		}
		return stringBuilt.toString().trim();
	}
	
}
