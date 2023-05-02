package app.model.bst

import app.model.bst.balancer.BinTreeBalancer
import app.model.bst.iterator.InOrderIterator
import app.model.bst.iterator.LevelOrderIterator
import app.model.bst.iterator.PreOrderIterator
import app.model.bst.node.BinTreeNode

/**
 * An abstract class representing a binary search tree.
 *
 * @param <E> the type of elements stored in the tree
 * @param <NodeType> the type of node used in the tree
 */
abstract class BinarySearchTree<E : Comparable<E>, NodeType : BinTreeNode<E, NodeType>>(
    /**
     * The balancer used to perform all magic with balance
     */
    protected val balancer: BinTreeBalancer<E, NodeType>,
) : Iterable<E> {
    /**
     * The root node of the tree.
     */
    var root: NodeType? = null

    /**
     * Adds a value to the tree.
     *
     * @param value the value to add
     * @param unique whether to only add unique values (replaces old with new one if unique and found)
     */
    open fun add(value: E, unique: Boolean = false) {
        root = balancer.add(root, value, unique)
    }

    /**
     * Removes a value from the tree.
     *
     * @param value the value to remove
     */
    open fun remove(value: E) {
        root = balancer.remove(root, value)
    }

    /**
     * Checks if a value is in the tree.
     *
     * @param value the value to check for
     * @return true if the value is in the tree, false otherwise
     */
    operator fun contains(value: E): Boolean {
        return search(root, value)
    }

    /**
     * Returns an iterator over the elements in the tree in in-order traversal order.
     *
     * @return an iterator over the elements in the tree in in-order traversal order
     */
    override operator fun iterator(): Iterator<E> {
        return InOrderIterator(root)
    }

    /**
     * Returns an iterator over the elements in the tree in pre-order traversal order.
     *
     * @return an iterator over the elements in the tree in pre-order traversal order
     */
    fun preOrderIterator(): Iterator<E> {
        return PreOrderIterator(root)
    }

    /**
     * Returns an iterator over the elements in the tree in level-order traversal order.
     *
     * @return an iterator over the elements in the tree in level-order traversal order
     */
    fun levelOrderIterator(): Iterator<E> {
        return LevelOrderIterator(root)
    }

    /**
     * Recursively searches for a value in the tree.
     *
     * @param node the node to start the search from
     * @param value the value to search for
     * @return true if the value is in the tree, false otherwise
     */
    private tailrec fun search(node: NodeType?, value: E): Boolean {
        if (node == null) {
            return false
        }

        if (value == node.value) {
            return true
        }

        return search((if (value < node.value) node.left else node.right), value)
    }
}
