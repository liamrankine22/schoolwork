import java.util.Comparator;
import java.util.Iterator;
//Imports a Comparator and Iterator for later use

public class NLNode<T> {
	private NLNode<T> parent;
	private ListNodes<NLNode<T>> children;
	private T data;
	//Initializes NLNode<T> parent ListNodes<NLNode<T>> children and T data for later use within the class
	
	public NLNode() {
		this.parent = null;
		this.data = null;
		this.children = new ListNodes<NLNode<T>>();
		//A constructor that initializes a new NLNode instance with no parent, no data, and an empty list of children.
	}
	
	public NLNode(T d, NLNode<T> p) {
		this.children = new ListNodes<NLNode<T>>();
		this.data = d;
		this.parent = p;
		//A constructor that initializes a new NLNode instance with the specified data and parent node. It also initializes an empty list of children.
	}
	
	public void setParent(NLNode<T> p) {
		this.parent = p;
		//Sets the parent node of this NLNode instance to the specified NLNode object.
	}
	
	public NLNode<T> getParent() {
        return parent;
        //Returns the parent NLNode of this NLNode instance.
    }
	
	public void addChild(NLNode<T> newChild) {
        newChild.setParent(this);
        children.add(newChild);
        //Adds a new child NLNode to the list of children of this NLNode instance.
    }
	
	public Iterator<NLNode<T>> getChildren() {
        return children.getList();
        //Returns an iterator over the list of children of this NLNode instance.
    }
	
	public Iterator<NLNode<T>> getChildren(Comparator<NLNode<T>> sorter) {
		children.sortedList(sorter);
		return children.getList();
		//Returns an iterator over the sorted list of children of this NLNode instance, sorted according to the specified Comparator.
	}
	
	public T getData() {
		return this.data;
		//Returns the data object associated with this NLNode instance.
	}
	
	public void setData(T d) {
		this.data = d;
		//Sets the data object associated with this NLNode instance to the specified object.
	}
	
	
}
