/**
 *This java code defines the class 'BinarySearchTree' which extends an abstract class
 * 'AbstractBinarySearchTree'. This class implements a binary search tree using an array
 * to store the elements.
 *
 * <p>
 * Date Last Modified 4/12/2024
 *
 * @author Caden VanSickle & Brenden Keahl
 * <p>
 * CS1121, Spring 2024
 * Lab Section 3
 */


public class BinarySearchTree extends AbstractBinarySearchTree {

    /**
     * Convenience constructor method that allocates space for the treeArray
     *
     * @param capacity - the length of the tree array.
     */
    public BinarySearchTree(int capacity) {
        super(capacity);
    }

    public BinarySearchTree(Comparable[] elements) {
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
    public boolean insert(Comparable value) throws IllegalArgumentException {
        if (value == null) {
            throw new IllegalArgumentException("Illegal Argument: Cannot insert null value.");
        }

        if (size == treeArray.length - 1) {
            // Tree array is full
            return false;
        }
        if (isEmpty()) {
            // If the tree is empty, insert at root index
            treeArray[rootIndex()] = value;
            size++;
            return true;
        } else {
            return insertHelper(rootIndex(), value);
        }
    }

    /**
     *
     * Recursively inserts a value into the binary search tree at index.
     * If the index is empty (null), the value is inserted and the tree size expands
     * If the value is less than the current node's held value, the method inserts it in the left subtree,
     * but if the value is greater, insertion is on the right subtree.
     *This method helps ensure the tree maintains its properties.
     *
     * @param index current index in the tree array.
     * @param value the value to be inserted into the tree.
     * @return true, if insertion is succesful, false otherwise when position exceeds bounds.
     */
    private boolean insertHelper(int index, Comparable value) {
        if (treeArray[index] == null) {
            treeArray[index] = value; // Value insert in even when empty
            size++;
            return true;
        }

        if (value.compareTo(treeArray[index]) < 0) {
            // Go to left subtree
            int leftIndex = leftIndex(index);
            if (leftIndex < treeArray.length) {
                return insertHelper(leftIndex, value);
            }
        } else if (value.compareTo(treeArray[index]) > 0) {
            // Go to right subtree
            int rightIndex = rightIndex(index);
            if (rightIndex < treeArray.length) {
                return insertHelper(rightIndex, value);
            }
        }
        return false;
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

    /**
     *
     * Searches for a specified value in the binary search tree starting from the given index.
     * This method traverses the tree array for the value and gives the value if true
     * otherwise returns falls and terminates if it reaches a null node (can't find value)
     *
     * @param index current index in the array.
     * @param value value to search for.
     * @return true if the value is found, false otherwise.
     */
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


    /**
     * Performs a traversal search of the binary search tree from the given index.
     * @param index Index given.
     */
    private void inorderTraversal(int index) {
        if (index < treeArray.length && treeArray[index] != null) {
            inorderTraversal(leftIndex(index));
            System.out.println(treeArray[index] + "");
            inorderTraversal(rightIndex(index));
        }
    }

    /**
     *
     * Deletes a value specified from the binary search three. Throws an exception if the value is null.
     * Uses the deleteHelper method as a utility starting from the root index to delete.
     *
     * @param value value given to delete from the tree
     * @return true if the value is deleted, otherwise false.
     * @throws IllegalArgumentException if the value doesn't exist (null).
     */
    public boolean delete(Comparable value) throws IllegalArgumentException {
        if (value == null) {
            throw new IllegalArgumentException("Illegal Argument: Cannot delete null value.");
        }
        return deleteHelper(rootIndex(), value);
    }

    /**
     *
     * Recursively helps delete a specified value from the binary search tree.
     * The method checks if the current node has the value given. If it is found, it goes through 4 cases:
     * - If the node is a leaf: node is removed.
     * - If the node has a left child: replaces the node with its left child.
     * - If the node has a right child: replaces the node with its right child.
     * - If the node has two children: replaces the node with min value in right subtree.
     *
     * If the value is not found, the method continues to search in each subtree.
     *
     * NO ORPHANS
     *
     * @param index current index in the tree array.
     * @param value the value to be deleted.
     * @return true if deleted, otherwise false.
     */

    private boolean deleteHelper(int index, Comparable value) {
        if (index >= treeArray.length || treeArray[index] == null) {
            return false; // Value not found
        }


        if (treeArray[index].equals(value)) {
            // Found the value to delete
            if (isLeaf(index)) {
                // Node is a leaf, just delete it
                treeArray[index] = null;
                size--;
                return true;
            } else if (hasOnlyLeftChild(index)) {
                // Node has only left child
                treeArray[index] = treeArray[leftIndex(index)];
                treeArray[leftIndex(index)] = null;
                size--;
                return true;
            } else if (hasOnlyRightChild(index)) {
                // Node has only right child
                treeArray[index] = treeArray[rightIndex(index)];
                treeArray[rightIndex(index)] = null;
                size--;
                return true;
            } else {
                // Node has both children
                // Find minimum value in the right subtree and replace the current node with it
                int minIndex = findMinIndex(rightIndex(index));
                treeArray[index] = treeArray[minIndex];
                // Delete the minimum value node from the right subtree
                deleteHelper(minIndex, treeArray[minIndex]);
                return true;
            }
        } else if (value.compareTo(treeArray[index]) < 0) {
            // Search left subtree
            return deleteHelper(leftIndex(index), value);
        } else {
            // Search right subtree
            return deleteHelper(rightIndex(index), value);
        }
    }


    private int findMinIndex(int index) {
        while (leftIndex(index) < treeArray.length && treeArray[leftIndex(index)] != null) {
            index = leftIndex(index);
        }
        return index;
    }


    private boolean isLeaf(int index) {
        return leftIndex(index) >= treeArray.length || rightIndex(index) >= treeArray.length
                || (treeArray[leftIndex(index)] == null && treeArray[rightIndex(index)] == null);
    }


    private boolean hasOnlyLeftChild(int index) {
        return leftIndex(index) < treeArray.length && treeArray[leftIndex(index)] != null
                && treeArray[rightIndex(index)] == null;
    }


    private boolean hasOnlyRightChild(int index) {
        return rightIndex(index) < treeArray.length && treeArray[rightIndex(index)] != null
                && treeArray[leftIndex(index)] == null;
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

        System.out.println();
        System.out.println("Deleting 7...");
        newTree.delete(7);
        System.out.println("Binary Search Tree after deletion:");
        newTree.inorderPrint();
        System.out.println();
    }

}