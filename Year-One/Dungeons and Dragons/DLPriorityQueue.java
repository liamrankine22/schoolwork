public class DLPriorityQueue<T> implements PriorityQueueADT<T>{
	private DLinkedNode<T> front;
	private DLinkedNode<T> rear;
	private int count;
	// Initializes 3 variables front, rear and count
	
	public DLPriorityQueue() {
		count = 0;
		front = rear = null;
		// Constructor that creates empty queue
	}
	
	public void add(T dataItem, double priority) {
		// Add's a user input data item as well as it's priority to the queue
	    DLinkedNode<T> newNode = new DLinkedNode<>(dataItem, priority);
	    //Creates a DLinkedNode object called newNode
	    if (front == null) {
	        front = newNode;
	        rear = newNode;
	        count++;
	        // Checks if the queue is empty, if true: it sets the front and the rear to the new node
	    }
	    else if (newNode.getPriority() < front.getPriority()) {
	        newNode.setNext(front);
	        front.setPrev(newNode);
	        front = newNode;
	        count++;
	        // Checks if the node's priority is greater(lower value) than the front's priority, if true: the new node becomes the front node
	    }
	    else {
	        DLinkedNode<T> current = front;
	        while (current.getNext() != null && newNode.getPriority() >= current.getNext().getPriority()) {
	            current = current.getNext();
	        }
	        newNode.setNext(current.getNext());
	        if (current.getNext() != null) {
	            current.getNext().setPrev(newNode);
	        }
	        current.setNext(newNode);
	        newNode.setPrev(current);
	        count++;
	        if (newNode.getNext() == null) {
	            rear = newNode;
	            //If the other if statements fail, a new node current is created, which searches through all nodes within the queue and checks where to put the new node
	        }
	    }
	}
	
	public void updatePriority(T dataItem, double newPriority) throws InvalidElementException {
	    DLinkedNode<T> current = front;
	    // Updates the priority of a node given it's dataItem
	    boolean found = false;
	    while (current != null) {
	        if (dataItem.equals(current.getDataItem())) {
	            current.setPriority(newPriority);
	            found = true;
	            break;
	            // Checks if the node searched for within the queue is found, if true it breaks out of the while loop
	        }
	        current = current.getNext();
	    }
	    if (!found) {
	        throw new InvalidElementException("Data item not found in the list.");
	        // If it is not found, throws an InvalidElementException describing to the user that the dataItem is not within the list
	    }

	    while (current != null && current.getNext() != null && current.getPriority() > current.getNext().getPriority()) {
	    	// Checks if the current is not null and the next node is not null and if the current value's priority is less(greater value) than the next ones
	    	// This part of the while loop checks every node after the current one and checks if it's priority is less(greater value) than them to find the correct
	    	// spor to place it
	        DLinkedNode<T> next = current.getNext();
	        DLinkedNode<T> prev = current.getPrev();
	        DLinkedNode<T> nextNext = next.getNext();
	        //Creates 3 nodes for use within the method

	        if (prev != null) {
	            prev.setNext(next);
	            //Checks if the previous node is not null, if true: it sets the previous node's next value to the next node
	        } else {
	            front = next;
	            // If the previous node is null the front becomes the next value
	        }

	        next.setPrev(prev);
	        next.setNext(current);
	        current.setPrev(next);
	        current.setNext(nextNext);

	        if (nextNext != null) {
	            nextNext.setPrev(current);
	            // checks if the current's next next node is not null and if this is true it sets the next next's previous node to the current node
	        } else {
	            rear = current;
	            // If the former if statement is false then the rear becomes the current value
	        }
	    }

	    while (current != null && current.getPrev() != null && current.getPriority() < current.getPrev().getPriority()) {
	    	// Checks if the current is not null and the current's previous node is not null and if the current value's priority is greater(lesser value) than the next ones
	    	// This part of the while loop checks every node before the current one and checks if it's priority is greater(lesser value) than them to find the correct
	    	// spor to place it
	        DLinkedNode<T> prev = current.getPrev();
	        DLinkedNode<T> next = current.getNext();
	        DLinkedNode<T> prevPrev = prev.getPrev();

	        if (next != null) {
	            next.setPrev(prev);
	            // checks if the current's next node's previous to prev
	        } else {
	            rear = prev;
	            // if the former if statement fails the rear becomes the previous
	        }

	        prev.setNext(next);
	        prev.setPrev(current);
	        current.setNext(prev);
	        current.setPrev(prevPrev);

	        if (prevPrev != null) {
	            prevPrev.setNext(current);
	            // checks if the current's previous previous fails is not null, if true: the current's previous previous value's next value becomes the current
	        } else {
	            front = current;
	            // else the front value becomes the current value
	        }
	    }
	}


	
	 public T removeMin() throws EmptyPriorityQueueException {
	        if (isEmpty()) {
	            throw new EmptyPriorityQueueException("Cannot removeMin from empty queue");
	            //Checks if the queue is empty first and if it is throws an exception
	        }

	        T temp;
	        temp = front.getDataItem();
	        if (front == rear) {
	            front = null;
	            rear = null;
	            count--;
	            return temp;
	        }
	        front = front.getNext();
	        front.setPrev(null);
	        count--;
	        return temp;
	        // Removes the minimum value, if there is only 1 value/the front is equal to the rear, then sets the front and rear to null and returns the single node
	    }
	
	public boolean isEmpty() {
		if (count == 0)
			return true;
		else 
			return false;
		// Checks if the queue is empty
	}
	
	public int size() {
		return count;
		// Returns the size of the queue
	}
	
	public String toString() {
		DLinkedNode<T> current = front;
		String result = "";
		while (current != null) {
			result += current.getDataItem() + "";
			current = current.getNext();
		}
		return result;
		// returns the front of the node in string form
	}
	
	public DLinkedNode<T> getRear(){
		return rear;
		// returns the rear node
	}
	
}
