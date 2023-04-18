package app.model.bst.iterator

import app.model.bst.node.BinTreeNode
import java.util.*

/**
 * The PreOrderIterator class is an internal implementation of the Iterator interface for a binary tree.
 * It traverses the tree in pre-order, visiting each node before its children.
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
internal class PreOrderIterator<E : Comparable<E>, NodeType : BinTreeNode<E, NodeType>>(root: NodeType?) : Iterator<E> {
    private val stack = LinkedList<NodeType>()

    init {
        root?.let { stack.push(it) }
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
        node.right?.let { stack.push(it) }
        node.left?.let { stack.push(it) }
        return node.value
    }
}