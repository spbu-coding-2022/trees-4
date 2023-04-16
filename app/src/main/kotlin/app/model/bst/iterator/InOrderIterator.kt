package app.model.bst.iterator

import app.model.bst.node.BinTreeNode
import java.util.*

internal class InOrderIterator<E : Comparable<E>, NodeType : BinTreeNode<E, NodeType>>(root: NodeType?) : Iterator<E> {
    private val stack = LinkedList<NodeType>()

    init {
        var node = root
        while (node != null) {
            stack.push(node)
            node = node.left
        }
    }

    override fun hasNext(): Boolean {
        return stack.isNotEmpty()
    }

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



