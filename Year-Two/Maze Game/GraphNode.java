

public class GraphNode {
	
	private int name;
	private boolean value;
	//private variables containing name and marked status of the node
	
	public GraphNode(int name) {
		this.name = name;
		this.value = false;
		//constructor for the node
	}
	
	public void mark(boolean mark) {
		value = mark;
		//Marks the graph according to input boolean value
	}
	
	public boolean isMarked() {
		return value;
		//Returns the marked status of the node
	}
	
	public int getName() {
		return name;
		//Returns the name of the node
	}
}
