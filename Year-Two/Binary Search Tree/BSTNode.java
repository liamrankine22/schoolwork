
public class BSTNode {
	
	private Record record;
	private BSTNode leftChild;
	private BSTNode rightChild;
	private BSTNode parent;
	//Private variables for use within BSTNode
	
	//Constructor for BSTNodes
	public BSTNode(Record item) {
		this.record = item;
		this.leftChild = null;
		this.rightChild = null;
		this.parent = null;
	}
	
	//Returns the record of the node
	public Record getRecord() {
		return this.record;
	}
	
	//Sets the record of the node
	public void setRecord(Record d) {
		this.record = d;
	}
	
	//Gets the left child of the node
	public BSTNode getLeftChild() {
		return this.leftChild;
	}
	
	//Gets the right child of the node
	public BSTNode getRightChild() {
		return this.rightChild;
	}
	
	//Gets the parent of the node
	public BSTNode getParent() {
		return this.parent;
	}
	
	//Sets the left child of the node
	public void setLeftChild(BSTNode u) {
		this.leftChild = u;
	}
	
	//Sets the right child of the node
	public void setRightChild(BSTNode u) {
		this.rightChild = u;
	}
	
	//Sets the parent of the node
	public void setParent(BSTNode u) {
		this.parent = u;
	}
	
	//Checks if the node is a leaf
	public boolean isLeaf() {
		return (this.leftChild == null && this.rightChild == null);
		
}
}
