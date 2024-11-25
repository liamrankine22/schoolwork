import java.util.Iterator;
//Imports an Iterator for later use within the class

public class FileStructure {
    private NLNode<FileObject> root;
    //Initializes NLNode object of type FileObject called root

    public FileStructure(String fileObjectName) throws FileObjectException {
        // Create a new FileObject with the specified name and initialize the root node with it
        FileObject file = new FileObject(fileObjectName);
        root = new NLNode<FileObject>(file, null);
        // Build the file structure starting from the root node
        buildFileStructure(root);
    }

    private void buildFileStructure(NLNode<FileObject> node) {
        // Get an iterator over the files and directories in the data of the current node
        Iterator<FileObject> iterator = node.getData().directoryFiles();
        if (iterator == null) {
            // If the iterator is null, the current node is a file and has no child nodes, so return
            return;
        }
        // Iterate over the files and directories and add child nodes for each directory
        while (iterator.hasNext()) {
            FileObject file = iterator.next();
            NLNode<FileObject> childNode = new NLNode<FileObject>(file, node);
            node.addChild(childNode);
            if (file.isDirectory()) {
                // If the file is a directory, recursively build the file structure starting from its child node
                buildFileStructure(childNode);
            }
        }
    }
    /*This is a constructor that takes a String parameter fileObjectName. It creates a new FileObject with the given name, creates a new NLNode<FileObject> 
     * with the FileObject as its data and null as its parent, and sets it as the root node of the file structure. 
     * It then calls the method buildFileStructure passing the root node as an argument to build the rest of the file structure.
     */

    public NLNode<FileObject> getRoot() {
        return root;
    }
    //Gets the root
    
    
    public Iterator<String> filesOfType(String type) {
        ListNodes<String> fileList = new ListNodes<>();
        filesOfTypeRecursive(root, type, fileList);
        return fileList.getList();
    }
    /*This method returns an iterator of the file names that have the given file extension type. 
     * It creates a new ListNodes<String> to hold the file names and calls the private recursive method filesOfTypeRecursive passing the root node, 
     * the file extension, and the ListNodes<String> as arguments to build the list of file names. It then returns the iterator of the ListNodes<String>
     */

    private void filesOfTypeRecursive(NLNode<FileObject> node, String type, ListNodes<String> fileList) {
    	// Iterate over the children of the current node
        Iterator<NLNode<FileObject>> children = node.getChildren();
        while (children.hasNext()) {
            NLNode<FileObject> childNode = children.next();
            FileObject child = childNode.getData();
            if (child.isDirectory()) {
            	 // If the child node is a directory, recursively search for matching files in that directory
                filesOfTypeRecursive(childNode, type, fileList);
            } else {
                String fileName = child.getLongName();
                if (fileName.endsWith(type)) {
                	// If the child node is a file with the desired extension, add its name to the list
                    fileList.add(fileName);
                }
            }
        }
    }
    /*This is a recursive method that builds the file structure starting from the given node. 
     * It first retrieves an iterator of the files in the directory represented by the node's data FileObject. 
     * If the iterator is null, it returns since the node does not represent a directory. 
     * Otherwise, it iterates over the files in the directory, creates a new child node for each file, 
     * adds the child node to the parent node, and recursively calls itself passing the child node as an argument if the file is a directory.
     */
    
    public String findFile(String name) {
        return findFileRecursive(root, name);
    }
    /*
     * Recursively searches for the file with the specified name in the file structure starting from the specified node.
     * Returns the full path of the file if it is found, or an empty string if it is not found.
     */

    private String findFileRecursive(NLNode<FileObject> node, String name) {
    	// Base case: if the current node contains the file we're looking for, return its full path
        if (node.getData().getName().equals(name)) {
            return node.getData().getLongName();
        }
     // Recursive case: search through each child node of the current node
        Iterator<NLNode<FileObject>> children = node.getChildren();
        while (children.hasNext()) {
            NLNode<FileObject> child = children.next();
            if (child.getData().isDirectory()) {
            	// If the child node is a directory, search for the file in that directory
                String result = findFileRecursive(child, name);
                if (!result.isEmpty()) {
                	// If the file is found in the child directory, return its full path
                    return result;
                }
            } else if (child.getData().getName().equals(name)) {
            	 // If the child node is a file with the desired name, return its full path
                return child.getData().getLongName();
            }
        }
        // If the file is not found in the current node or its children, return an empty string
        return "";
    }
    /*This is a recursive method that builds a list of file names with the given file extension type. 
     * It first retrieves an iterator of the child nodes of the given node, iterates over the child nodes, 
     * and recursively calls itself passing the child node, the file extension, and the `ListNodes<String
     */


}
