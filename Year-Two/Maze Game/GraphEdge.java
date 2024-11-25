public class GraphEdge {
	
	private int type;
	private String label;
	private GraphNode u;
	private GraphNode v;
	//Private variables for use within program
	
	public GraphEdge(GraphNode u, GraphNode v, int type, String label) {
		this.u = u;
		this.v = v;
		this.type = type;
		this.label = label;
		//Constructor for the GraphEdge which sets the two endpoints, the type and label
	}
	
	public GraphNode firstEndpoint() {
		return u;
		//Returns the first endpoint of the graph edge
	}
	
	public GraphNode secondEndpoint() {
		return v;
		//Returns the second endpoint of the graph edge
	}
	
	public int getType() {
		return type;
		//Returns the type of the graph edge
	}
	
	public void setType(int newType) {
		type = newType;
		//Sets the new type of the graph edge
	}
	
	public String getLabel() {
		return label;
		//Returns the label of the graph edge
	}
	
	public void setLabel(String newLabel) {
		label = newLabel;
		//Sets the label of the graph edge
	}
}
