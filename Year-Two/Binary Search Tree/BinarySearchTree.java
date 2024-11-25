
public class BinarySearchTree {

	private BSTNode root;
	//Private variable containing the root
	
	//Constructor for the binary search tree
	public BinarySearchTree(){
		this.root = null;
	}
	
	//Returns the root of the tree
	public BSTNode getRoot() {
		return this.root;
	}	
	
	//Returns the node with the matching key k in the tree with the root r
	public BSTNode get(BSTNode r, Key k) {
		if (r.isLeaf()) {
			return r;
			//Checks if the root is a leaf, if so returns it's value (used for finding where a new node should go)
		} else {
			if (r.getRecord().getKey().compareTo(k)==0) {
				return r;
				//Checks if the current node's key is equal to the given key, if so returns the value of the node
			} else if(r.getRecord().getKey().compareTo(k) < 0) {
				return get(r.getRightChild(),k);
				//Checks if the current node's key is less than the given key, if so goes to the right child of the current node
			} else {
				return get(r.getLeftChild(),k);
				//Checks if the current node's key is greater than the given key, if so goes to the left child of the current node
			}
		}
	}
	
	//Inserts a node with the given record d in the tree with the root r
	public void insert(BSTNode r, Record d)throws DictionaryException {
		BSTNode a = new BSTNode(null);
		BSTNode b = new BSTNode(null);
		//Variables for use within updating a new node
		if(r == null) {
			root = new BSTNode(d);
			root.setLeftChild(a);
			a.setParent(root);
			root.setRightChild(b);
			b.setParent(root);
			return;
			//Checks if the root is null, if so the new node becomes the root and sets it's children as leaf nodes
		} else  {
			BSTNode p = get(r,d.getKey());
			if(p.isLeaf()==false) {
				throw new DictionaryException("Already in Dictionary");
				//Checks if the given record is already in the dictionary and throws a Dictionary Exception if so
			
			} else {
				p.setRecord(d);
				a.setParent(p);
				b.setParent(p);
				p.setLeftChild(a);
				p.setRightChild(b);
				//Creates the new node by inserting a record into the leaf node making it an internal node and creating new leaves
			}
		}	
	}	
	
	//Method for removing a node with the key k from the tree with the root r
	public void remove(BSTNode r, Key k)throws DictionaryException {
		BSTNode p = get(r,k);
		if(p.isLeaf()) {
			throw new DictionaryException("Not in Dictionary");
			//Checks if the given node is not in the dictionary and throws a dictionary exception if so
		} else if (p.getLeftChild().isLeaf()||p.getRightChild().isLeaf()){
			//check's if either of the children of the node are leaves
			if(p.getLeftChild().isLeaf()) {
				//Checks if the left child of the node is a leaf
				BSTNode b = p.getRightChild();
				BSTNode parent = p.getParent();
				if(parent != null) {
					if(parent.getRightChild() == p) {
						parent.setRightChild(b);
						b.setParent(parent);
						//If the node is the right child of it's parent, the parent's new right child is the opposite node of the leaf
					} else {
						parent.setLeftChild(b);
						b.setParent(parent);
						//If the node is the left child of it's parent, the parent's new left child is the opposite node of the leaf

					}
				} else {
					root = b;
					//If the node's parents are null the root becomes the the opposite node of the leaf which is the right one
				}
			} else if(p.getRightChild().isLeaf()) {
				//Checks if the right child of the node is a leaf
				BSTNode b = p.getLeftChild();
				BSTNode parent = p.getParent();
				if(parent != null) {
					if(parent.getRightChild() == p) {
						parent.setRightChild(b);
						b.setParent(parent);
						//If the node is the right child of it's parent, the parent's new right child is the opposite node of the leaf

					} else {
						parent.setLeftChild(b);
						b.setParent(parent);
						//If the node is the left child of it's parent, the parent's new left child is the opposite node of the leaf

					}
				} else {
					root = b;
					//If the node's parents are null the root becomes the the opposite node of the leaf which is the left one
				}
			} 
		}else {
			BSTNode s = smallest(p.getRightChild());
			p.setRecord(s.getRecord());
			remove(s,s.getRecord().getKey());
			//If both the node's children are internal nodes, it finds the successor of the node and replaces their information and deletes the old one
		}
	}
	
	//Method for finding the successor of a node with the key k within a tree with the root r
	public BSTNode successor(BSTNode r, Key k) {
		BSTNode p = get(r,k);
		if(p.getRightChild() == null) {
			p = p.getParent();
			while(p != null && p.getRecord().getKey().compareTo(k) < 0) {
				p = p.getParent();
				//If the right child isn't a leaf it finds the node which would succeed it by going backwards and comparing each parent to the key k
			}
			return p;
			//return p;
		}
		if(!p.getRightChild().isLeaf()) {
			return smallest(p.getRightChild());
			//Checks if the right child is not a leaf if so, it find the smallest node on the right 
		} else {
			p = p.getParent();
			while(p != null && p.getRecord().getKey().compareTo(k) < 0) {
				p = p.getParent();
				//If the right child isn't a leaf it finds the node which would succeed it by going backwards and comparing each parent to the key k
			}
			return p;
		}
	}
	
	//Method for finding the predecessor of node with key k in tree with root r
	public BSTNode predecessor(BSTNode r, Key k) {
		BSTNode p = get(r,k);
		if(!p.getLeftChild().isLeaf()) {
			return largest(p.getLeftChild());
			//Checks if the left child is a leaf if not it finds the largest left side node
		} else {
			p = p.getParent();
			while(p != null && p.getRecord().getKey().compareTo(k) > 0) {
				p = p.getParent();
				//Otherwise it does the opposite with successor comparing the parent's key to k and seeing when k will be less than the parent if not it continues to update p 
			}
			return p;
		}
	}
	
	//Method for finding the smallest node in a tree
	public BSTNode smallest(BSTNode r) {
		if(r.getLeftChild().isLeaf()) {
			return r;
			//Checks if the left child is a leaf if so returns the leaf node
		}else {
			return smallest(r.getLeftChild());
			//Otherwise it continues getting the leftmost node which will be the smallest node
		}
	}
	
	public BSTNode largest(BSTNode r) {
		if(r.getRightChild().isLeaf()) {
			return r;
			//Checks if the right child is a leaf if so returns the leaf node
		}else {
			return largest(r.getRightChild());
			//Otherwise it continues getting the rightmost node which will be the smallest node
		}
	}
}
