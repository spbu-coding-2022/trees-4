package app.model.bst.iterator

import app.model.bst.node.BinTreeNode
import java.util.*

/**
 * InOrderIterator is an internal class that implements the Iterator interface for a binary tree.
 * It iterates over the elements of the tree in in-order traversal order.
 * The tree stores elements of type E, which must implement the Comparable interface.
 * The NodeType parameter specifies the type of nodes in the tree.
 *
 * @param E the type of elements stored in the tree, which must implement the Comparable interface.
 * @param NodeType the type of nodes in the tree.
 * @property root the root node of the tree.
 *
 * @see Iterator
 * @see BinTreeNode
 */
internal class InOrderIterator<E : Comparable<E>, NodeType : BinTreeNode<E, NodeType>>(root: NodeType?) : Iterator<E> {
    private val stack = LinkedList<NodeType>()

    init {
        var node = root
        while (node != null) {
            stack.push(node)
            node = node.left
        }
    }

    /**
     * @return true if there are more elements to iterate over, false otherwise.
     */
    override fun hasNext(): Boolean {
        return stack.isNotEmpty()
    }

    /**
     * @return the next element in the iteration.
     */
    override fun next(): E {
        val node = stack.pop()
        var nextNode = node.right
        while (nextNode != null) {
            stack.push(nextNode)
            nextNode = nextNode.left
        }
        return node.value
    }
}



