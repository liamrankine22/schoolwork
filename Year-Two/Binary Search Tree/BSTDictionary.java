
public class BSTDictionary implements BSTDictionaryADT{
	
	private BinarySearchTree tree;
	//Private variable used to create the tree
	
	//Constructor for the tree
	public BSTDictionary() {
		this.tree = new BinarySearchTree();
	}
	
	//Gets the node storing k within the tree using the root and the given value
	public Record get(Key k) {
		BSTNode p = tree.get(tree.getRoot(), k);
		if(p != null) {
			return p.getRecord();
		} else {
			System.out.println("Null");
			return null;
		}
	}
	
	//Puts a node in the tree using the given record and the tree's root
	public void put(Record d) throws DictionaryException {
		tree.insert(tree.getRoot(), d);
	}

	//Removes a node in the tree using the given record and the tree's root
	public void remove(Key k) throws DictionaryException {
		tree.remove(tree.getRoot(), k);
	}
	
	//Finds the successor of a node in the tree using the given record and the tree's root
	public Record successor(Key k) {
		BSTNode successor=tree.successor(tree.getRoot(), k);
		if(successor != null) {
			return successor.getRecord();
		} else {
			return null;
		}
	}

	//Finds the predecessor of a node in the tree using the given record and the tree's root
	public Record predecessor(Key k) {
		BSTNode predecessor=tree.predecessor(tree.getRoot(), k);
		if(predecessor != null) {
			return predecessor.getRecord();
		} else {
			return null;
		}
	}

	//Finds the smallest node of a tree using the root of the tree
	public Record smallest() {
		BSTNode smallest=tree.smallest(tree.getRoot());
		if(smallest != null) {
			return smallest.getRecord();
		} else {
			return null;
		}
	}

	//Finds the largest node of a tree using the root of the tree
	public Record largest() {
		BSTNode largest=tree.largest(tree.getRoot());
		if(largest != null) {
			return largest.getRecord();
		} else {
			return null;
		}
	}
}
