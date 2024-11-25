import java.util.Iterator;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Stack;

public class Maze {
	private String inputFile;
	private Graph mazeGraph;
	private String[][] mazeArray;
	private int entranceLocation;
	private int exitLocation;
	private Stack<GraphNode> visitedNodes;
	private int numCoins;
	//Private variables for use within program
	
	
	public Maze(String inputFile) throws IOException, GraphException, MazeException {
		//Constructor for maze class
		
		this.inputFile = inputFile;
		BufferedReader in = new BufferedReader(new FileReader(this.inputFile));
		//Creates a text reader to convert the string text into useful code
		
		this.visitedNodes = new Stack<GraphNode>();
		//Creates a stack for use within solve function

		String s = in.readLine();
		int scale = Integer.parseInt(s);
		if( scale <= 0) {
			throw new MazeException("Invalid maze format");
		}
		//Stores the scale factor for the maze and checks if it's a valid number, if not throws an exception
		
		String a = in.readLine();
		int width = Integer.parseInt(a);
		if(width <= 0) {
			throw new MazeException("Invalid maze format");
		}
		//Stores the width of the maze and checks if it's a valid number, if not throws an exception
		
		String l = in.readLine();
		int length = Integer.parseInt(l);
		if(length <= 0) {
			throw new MazeException("Invalid maze format");
		}
		//Stores the length of the maze and checks if it's a valid number, if not throws an exception
		
		String k = in.readLine();
		int numCoins = Integer.parseInt(k);
		if(numCoins < 0) {
			throw new MazeException("Invalid maze format");
		}
		this.numCoins = numCoins;
		//Stores the number of coins and checks if it's a valid number, if not throws an exception
		
		mazeGraph = new Graph(width*length);
		//Creates the graph that will store all nodes that should be within the graph
		mazeArray = new String[length*2-1][width*2-1];
		//Creates the Matrix(should be named mazeMatrix) used to help create the graph
		
		int nodeNum = 0;
		//Stores the node number for user later in program
		
		for(int i = 0; i < length*2-1; i++) {
			//Loops through the length of the maze times 2 minus since the total number of values within the matrix will be that much
			String row = in.readLine();
			//Reads the next line in the input file
			for (int j = 0; j < width*2-1; j++) {
				//Loops through the width of the maze times 2 minus since the total number of values within the matrix will be that much
				if(row.charAt(j) == 'w' || row.charAt(j) == '1' ||  row.charAt(j) == '2' ||  row.charAt(j) == '3' ||  
						row.charAt(j) == '4' || row.charAt(j) == '5' ||  row.charAt(j) == '6' ||  row.charAt(j) == '7' ||
						 row.charAt(j) == '8' ||  row.charAt(j) == '9' || row.charAt(j) == 'c' || row.charAt(j) == '0') {
					//Checks if the character at the width index j is a wall or a door
					mazeArray[i][j] = String.valueOf(row.charAt(j));
					continue;
					//Then adds it to the array in its respective location without increase the number of nodes
				}
				mazeArray[i][j] = row.charAt(j)+"."+Integer.toString(nodeNum);
				//Adds to the array the current node and its position stored from nodeNum and adds it to the array
				if(row.charAt(j) == 's') {
					entranceLocation = nodeNum;
					//Checks if the node being added is the start, if so stores it's location
				} else if (row.charAt(j) == 'x') {
					exitLocation = nodeNum;
					//Checks if the node being added is the end, if so stores it's location
				}
				nodeNum++;
				//Increases the nodeNum for later node's correct positioning
			}
		}
		
		for(int i = 0; i < length*2-1; i++) {
			for (int j = 0; j < width*2-1; j++) {
				//Nested for loop for going through the entire array
				if(!mazeArray[i][j].contains("s") && !mazeArray[i][j].contains("o") && !mazeArray[i][j].contains("x")) {
					continue;
					//Checks if the current value in the array is not a node, if so do not move on to the further for loops
				} 
				//All further if statements are the same thing but for different directions directly horizontally and vertically
				if(i > 1 && !mazeArray[i-1][j].contains("w")) {
					//First checks if there is enough space in the array at the current position to continue the code without going
					//out of bounds. Then checks if the index left of the current one is a wall
					if(mazeArray[i-2][j] != null) {
						String[] node1Info = mazeArray[i][j].split("\\.");
						String[] node2Info = mazeArray[i-2][j].split("\\.");
						//Creates arrays storing the info from the nodes by splitting the dot seperating the type of node and its pos
						GraphNode um1 = mazeGraph.getNode(Integer.parseInt(node1Info[1]));
						GraphNode um2 = mazeGraph.getNode(Integer.parseInt(node2Info[1]));
						//Gets the nodes at the position determined by the node's info
						try {
							mazeGraph.getEdge(um1, um2);
							//Try's to get an edge to test if it doesn't exist
						} catch(GraphException e) {
							try {
								mazeGraph.getEdge(um2, um1);
								//Try's to get the same edge but opposite to test if it doesn't exist
							} catch(GraphException ec) {
								if(mazeArray[i-1][j].contains("c")) {
									mazeGraph.insertEdge(um1, um2, 0, "corridor");
									//Checks if the index between the two nodes is a corridor, if so creates an edge with a corridor label
								} else {
									mazeGraph.insertEdge(um1, um2, Integer.parseInt(mazeArray[i-1][j]), "door");
									//Otherwise the only other option is that it's a door since walls were checked earlier, creates a door
									//with the label door and the type of the number of coins it requires to open
								}
							}
						}
					}
					
				} 
				
				if(j < width*2-2 && !mazeArray[i][j+1].contains("w")) {
					if(mazeArray[i][j+2] != null) {
						String[] node1Info = mazeArray[i][j].split("\\.");
						String[] node2Info = mazeArray[i][j+2].split("\\.");
						GraphNode um1 = mazeGraph.getNode(Integer.parseInt(node1Info[1]));
						GraphNode um2 = mazeGraph.getNode(Integer.parseInt(node2Info[1]));
						try {
							mazeGraph.getEdge(um1, um2);
						} catch(GraphException e) {
							try {
								mazeGraph.getEdge(um2, um1);
							} catch(GraphException ec) {
								
								if(mazeArray[i][j+1].contains("c")) {
									mazeGraph.insertEdge(um1, um2, 0, "corridor");
								} else {
									mazeGraph.insertEdge(um1, um2, Integer.parseInt(mazeArray[i][j+1]), "door");
								}
							}
						}
					}
					
				} if(i < length*2-2 && !mazeArray[i+1][j].contains("w")) {
					if(mazeArray[i+2][j] != null) {
						String[] node1Info = mazeArray[i][j].split("\\.");
						String[] node2Info = mazeArray[i+2][j].split("\\.");
						GraphNode um1 = mazeGraph.getNode(Integer.parseInt(node1Info[1]));
						GraphNode um2 = mazeGraph.getNode(Integer.parseInt(node2Info[1]));
						try {
							mazeGraph.getEdge(um1, um2);
						} catch(GraphException e) {
							try {
								mazeGraph.getEdge(um2, um1);
							} catch(GraphException ec) {
								
								if(mazeArray[i+1][j].contains("c")) {
									mazeGraph.insertEdge(um1, um2, 0, "corridor");
								} else {
									mazeGraph.insertEdge(um1, um2, Integer.parseInt(mazeArray[i+1][j]), "door");
								}
							}
						}
					}

				} if(j > 1 && !mazeArray[i][j-1].contains("w")) {
					if(mazeArray[i][j-2] != null) {
						String[] node1Info = mazeArray[i][j].split("\\.");
						String[] node2Info = mazeArray[i][j-2].split("\\.");
						GraphNode um1 = mazeGraph.getNode(Integer.parseInt(node1Info[1]));
						GraphNode um2 = mazeGraph.getNode(Integer.parseInt(node2Info[1]));
						try {
							mazeGraph.getEdge(um1, um2);
						} catch(GraphException e) {
							try {
								mazeGraph.getEdge(um2, um1);
							} catch(GraphException ec) {
								
								if(mazeArray[i][j+1].contains("c")) {
									mazeGraph.insertEdge(um1, um2, 0, "corridor");
								} else {
									mazeGraph.insertEdge(um1, um2, Integer.parseInt(mazeArray[i][j-1]), "door");
								}
							}
						}
					}

				}
			}
		}
		
		
	}
	
	public Graph getGraph(){
		//Returns the Graph object of the Maze
		if(mazeGraph == null) {
			return null;
			//Checks if the graph is null, if so returns null
		} else {
			return mazeGraph;
			//Otherwise returns the graph
		}
	}
	
	public Iterator solve() throws GraphException {
		//Solves the 
		boolean success = paths(mazeGraph.getNode(entranceLocation), mazeGraph.getNode(exitLocation), numCoins);
		//Calls a private method getting the path from the start to the exit
		if (success == true) {
			return visitedNodes.iterator();
			//If a path is found it returns an iterator storing all nodes required to find exit
		}
		return null;
		
	}
	
	private boolean paths(GraphNode s, GraphNode d, int coins) throws GraphException {
		//Finds a path from node a to node b with the number of coins
		
		visitedNodes.push(s);
		//Stores the current node in the stack to store visited locations
		Iterator iter = mazeGraph.incidentEdges(s);
		//Creates an iterator storing all edges of the current node
		if (s == d) {
			return true;
			//If the current node is equal to the destination it returns true
		}
		s.mark(true);
		//Marks the node so it does not return to that node
		while(iter.hasNext()) {
			//While the iterator has another edge to visit it continues this while loop
			GraphEdge edge = (GraphEdge) iter.next();
			//Creates an edge object for use within this program
			if (edge.secondEndpoint().isMarked() == false && edge.getType() <= coins) {
				boolean foundPath = paths(edge.secondEndpoint(),d,coins - edge.getType());
				//If the edge's second endpoint is unmarked meaning not visited and the connection between them's required coins is
				//less than equal to the number of coins available it recursively calls this algorithm
				if(foundPath == true) {
					return true;
					//If it finds a path it returns true, this is for post recursion exiting
				}
			}
		}
		visitedNodes.pop();
		return false;
		//If no path is found from the current path it removes the most recently added node to either go a step back recursively or
		//to end the program if this is the first node and no path is found
	}
	
}


