import java.util.Iterator;
import java.util.ArrayList;
import java.util.List;


public class Graph implements GraphADT {
	
	private GraphNode[] verMatrix;
	private GraphEdge[][] adjMatrix;
	private int size;
	//Private variables for use within program
	
	public Graph(int n){
		verMatrix = new GraphNode[n];
		//Creates an array storing graphnodes of size n for all nodes within the graph
		for(int i = 0; i < n; i++) {
			GraphNode a = new GraphNode(i);
			verMatrix[i] = a;
			//Inputs all nodes into the array
		}
		adjMatrix = new GraphEdge[n][n];
		size = n;
		//Creates an adjacentcy matrix with the size of n*n
	}
	
	public void insertEdge(GraphNode u, GraphNode v, int edgeType, String label) throws GraphException {
		if(u.getName() >= size || v.getName() >= size ||  u.getName() < 0 || v.getName() < 0) {
			throw new GraphException("Node does not exist within graph");
			//Checks if the nodes are valid, if not throws a GraphException
		}
		GraphEdge edge = new GraphEdge(u,v,edgeType,label);
		GraphEdge revEdge = new GraphEdge(v,u,edgeType,label);
		//Creates edges between u and v 
		
		if(adjMatrix[u.getName()][v.getName()] != null) {
			throw new GraphException("Edge already exists within graph");
			//Checks if the normal edge is pre-existing, if so throws a GraphException
		} else if(adjMatrix[v.getName()][u.getName()] != null) {
			throw new GraphException("Edge already exists within graph");
			//Checks if the reversed edge is pre-existing, if so throws a GraphException
		}else {
			adjMatrix[u.getName()][v.getName()] = edge;
			adjMatrix[v.getName()][u.getName()] = revEdge;
			//Sets the edges in the adjacentcy matrix
		}
	}	
	
	public GraphNode getNode(int name) throws GraphException {
		if(name >= size || name < 0) {
			throw new GraphException("Node does not exist within graph");
			//Checks if the node is valid for the graph, if not throws a GraphException
		} else {
			return verMatrix[name];
			//Returns the node from at the index of the name given
		}
	}
	
	public Iterator incidentEdges(GraphNode u) {
		int num=u.getName();
		
		List<GraphEdge> edgeList = new ArrayList<GraphEdge>();
		//Creates a list containing all edges connecting u and every other possible connection
		
		for(int i = 0; i < size; i++) {
			if(adjMatrix[u.getName()][i] == null) {
				continue;
				//Checks within the adjacency matrix if the index is null meaning no connection and chooses to skip it's input in the list
			} else {
				edgeList.add(adjMatrix[u.getName()][i]);
				//If the location at the index isn't null the connection is added to the list
			}
		}
		if(edgeList.isEmpty()) {
			return null;
			//checks if the list of edges is empty meaning no connections and returns null
		} else {
			return edgeList.iterator();
			//Returns the iterator for the list of connections for node u
		}
	}
	
	public GraphEdge getEdge(GraphNode u, GraphNode v) throws GraphException{
		if(adjMatrix[u.getName()][v.getName()] == null) {
			throw new GraphException("No edge exists within graph");
			//Checks if the edge is in the adjacency matrix, if not it throws a Graph Exception
		} else {
			return adjMatrix[u.getName()][v.getName()];
			//Otherwise returns the edge
		}
	}
	
	public boolean areAdjacent(GraphNode u, GraphNode v) throws GraphException {
		if(verMatrix[u.getName()] == null) {
			throw new GraphException("No node exists within graph");
			//Checks if the node is within the vertex array, if not throws a Graph Exception
		} else if(verMatrix[v.getName()] == null) {
			throw new GraphException("No node exists within graph");
			//Checks if the second node is within the vertex array, if not throws a Graph Exception
		} else {
			GraphEdge edge = getEdge(u,v);
			//Gets the edge of u and v
			if(edge != null) {
				return true;
				//If there is connection between the two it returns true
			}
		}
		return false;
		//Otherwise returns false
	}
}
