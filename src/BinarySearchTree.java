
public class BinarySearchTree extends AbstractBinarySearchTree {

    /**
     * Convenience constructor method that allocates space for the treeArray
     *
     * @param capacity - the length of the tree array.
     */
    public BinarySearchTree(int capacity) {
        super(capacity);
    }

    public BinarySearchTree( Comparable[ ] elements ) {
        super(elements);
    }

    /**
     * Returns the position of the parent of the node at index.
     *
     * @param index
     * @return
     */
    @Override
    public int parentIndex(int index) {
        return index / 2;
    }

    /**
     * Returns the index of the root bucket in treeArray.
     *
     * @return the index of the root
     */
    @Override
    public int rootIndex() {
        return 1; //???
    }

    /**
     * @return the number of nodes in the tree.
     */
    @Override
    public int size() {
        return size;
    }

    /**
     * Inserts a value into the Binary Search Tree.
     * According to the rules:
     * All values in the left subtree of a node are less than the value of the node.
     * All values in the right subtree of a node are greater than the value of the node.
     * No duplicates are allowed.
     *
     * @param value to be inserted, cannot be null
     * @return true if value was successfully inserted into tree, false otherwise
     * @throws IllegalArgumentException if value is null
     */
    @Override
    public boolean insert ( Comparable value ) throws IllegalArgumentException {
        if ( value == null ) {
            throw new IllegalArgumentException ( "Illegal Argument: Cannot insert null value." );
        }
        return insertHelper(0, value);
    }

    private boolean insertHelper( int index, Comparable value ) {
        if (index >= treeArray.length) { // Check if index is in bounds
            return false;
        }

        if ( treeArray[index].equals ( value ) ) { //if value already exists
            return false;
        } else if ( value.compareTo ( treeArray[index] ) < 0 ) {
            return insertHelper( leftIndex ( index ), value );
        } else {
            return insertHelper( rightIndex ( index ), value );
        }
    }

    /**
     * Returns true if the specified value exists in the tree.
     *
     * @param value
     * @return
     */

    @Override
    public boolean search(Comparable value) {
        return searchHelper(rootIndex(), value);
    }

    private boolean searchHelper(int index, Comparable value) {
        if (index >= treeArray.length || treeArray[index] == null) {
            return false; // Value doesn't exist
        }

        if (treeArray[index].equals(value)) {
            return true; //Val found
        } else if (value.compareTo(treeArray[index]) < 0) {

            return searchHelper(leftIndex(index), value);
        } else {

            return searchHelper(rightIndex(index), value);
        }
    }


    /**
     * Prints the elements in sorted order.
     * Each node prints its left subtree, its value, then its right subtree.
     * See the textbook.
     */
    @Override
    public void inorderPrint() {
        inorderTraversal(rootIndex());
    }

    private void inorderTraversal(int index) {
        if (index < treeArray.length && treeArray[index] != null) {
            inorderTraversal(leftIndex(index));
            System.out.print(treeArray[index] + "");
            inorderTraversal(rightIndex(index));
        }
    }

    public static void main(String[] args) {
        BinarySearchTree newTree = new BinarySearchTree(10);

        newTree.insert(5);
        newTree.insert(4);
        newTree.insert(5);
        newTree.insert(7);
        newTree.insert(10);

        System.out.println("Binary Search Tree!");
        newTree.inorderPrint(); // should be sorted

        System.out.println("Search for value 7: " + newTree.search(7)); //true
        System.out.println("Search for value 22: " + newTree.search(22)); //false
    }

}