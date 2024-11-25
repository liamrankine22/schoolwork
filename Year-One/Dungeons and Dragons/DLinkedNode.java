public class DLinkedNode<T> {
	private T dataItem;
	private double priority;
	private DLinkedNode<T> next;
	private DLinkedNode<T> prev;
	// Initializes 4 instance variables: dataItem, priority, next and prev.
	
	public DLinkedNode (T data, double prio) {
		this.dataItem = data;
		this.priority = prio;
		this.next = null;
		this.prev = null;
		// Inputted Constructor for class DLinkedNode
	}
	
	public DLinkedNode() {
		this.dataItem = null;
		this.priority = 0;
		this.next = null;
		this.prev = null;
		// Empty Constructor for class DLinked Node
	}
	
	public double getPriority() {
		return this.priority;
		// Returns node's Priority to user
	}
	
	public T getDataItem() {
		return this.dataItem;
		// Returns node's dataItem to the user
	}
	
	public DLinkedNode<T> getNext(){
		return this.next;
		// Returns node's next value to the user
	}
	
	public DLinkedNode<T> getPrev(){
		return this.prev;
		// Returns node's previous value to the user
	}
	
	public void setDataItem(T data) {
		this.dataItem = data;
		// Set's node's dataItem to user inputted choice
	}
	
	public void setNext(DLinkedNode<T> next) {
		this.next = next;
		// Set's node's next node to user inputted choice
	}
	
	public void setPrev(DLinkedNode<T> prev) {
		this.prev = prev;
		// Set's node's previous node to user inputted choice
	}
	
	public void setPriority(double prio) {
		this.priority = prio;
		// Set's node's priority to user inputted choice
	}
}
