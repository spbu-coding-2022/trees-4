package app.model.bst

import app.model.bst.balancer.BinTreeBalancer
import app.model.bst.iterator.InOrderIterator
import app.model.bst.iterator.LevelOrderIterator
import app.model.bst.iterator.PreOrderIterator
import app.model.bst.node.BinTreeNode

abstract class BinarySearchTree<E : Comparable<E>, NodeType : BinTreeNode<E, NodeType>>(
    protected val balancer: BinTreeBalancer<E, NodeType>,
) {
    var root: NodeType? = null

    open fun add(value: E, unique: Boolean = false) {
        root = balancer.add(root, value, unique)
    }

    open fun remove(value: E) {
        root = balancer.remove(root, value)
    }

    operator fun contains(value: E): Boolean {
        return search(root, value)
    }

    operator fun iterator(): Iterator<E> {
        return InOrderIterator(root)
    }

    fun preOrderIterator(): Iterator<E> {
        return PreOrderIterator(root)
    }

    fun levelOrderIterator(): Iterator<E> {
        return LevelOrderIterator(root)
    }

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
