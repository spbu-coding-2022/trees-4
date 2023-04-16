package app.model.bst.iterator

import app.model.bst.node.BinTreeNode
import java.util.*

internal class LevelOrderIterator<E : Comparable<E>, NodeType : BinTreeNode<E, NodeType>>(root: NodeType?) :
    Iterator<E> {
    private val queue = LinkedList<NodeType>()

    init {
        root?.let { queue.offer(it) }
    }

    override fun hasNext(): Boolean {
        return queue.isNotEmpty()
    }

    override fun next(): E {
        val node = queue.poll()
        node.left?.let { queue.offer(it) }
        node.right?.let { queue.offer(it) }
        return node.value
    }
}