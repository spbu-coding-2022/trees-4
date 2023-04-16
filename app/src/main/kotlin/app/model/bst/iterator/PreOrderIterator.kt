package app.model.bst.iterator

import app.model.bst.node.BinTreeNode
import java.util.*

internal class PreOrderIterator<E : Comparable<E>, NodeType : BinTreeNode<E, NodeType>>(root: NodeType?) : Iterator<E> {
    private val stack = LinkedList<NodeType>()

    init {
        root?.let { stack.push(it) }
    }

    override fun hasNext(): Boolean {
        return stack.isNotEmpty()
    }

    override fun next(): E {
        val node = stack.pop()
        node.right?.let { stack.push(it) }
        node.left?.let { stack.push(it) }
        return node.value
    }
}