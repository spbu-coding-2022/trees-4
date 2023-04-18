package app.model.bst.iterator

import app.model.bst.node.BinTreeNode
import java.util.*

/**
 * The LevelOrderIterator class is an internal implementation of the Iterator interface for a binary tree.
 * It traverses the tree in level-order, visiting each node at each level before moving on to the next level.
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
internal class LevelOrderIterator<E : Comparable<E>, NodeType : BinTreeNode<E, NodeType>>(root: NodeType?) :
    Iterator<E> {
    private val queue = LinkedList<NodeType>()

    init {
        root?.let { queue.offer(it) }
    }

    /**
     * @return true if there are more elements to iterate over, false otherwise.
     */
    override fun hasNext(): Boolean {
        return queue.isNotEmpty()
    }

    /**
     * @return the next element in the iteration.
     */
    override fun next(): E {
        val node = queue.poll()
        node.left?.let { queue.offer(it) }
        node.right?.let { queue.offer(it) }
        return node.value
    }
}