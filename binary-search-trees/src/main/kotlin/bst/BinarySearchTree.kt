package bst

import bst.balancer.BinTreeBalancer
import bst.iterator.InOrderIterator
import bst.iterator.LevelOrderIterator
import bst.iterator.PreOrderIterator
import bst.node.BinTreeNode

abstract class BinarySearchTree<E : Comparable<E>, NodeType : BinTreeNode<E, NodeType>>(
    protected val balancer: BinTreeBalancer<E, NodeType>,
) {
    var root: NodeType? = null
        internal set

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
