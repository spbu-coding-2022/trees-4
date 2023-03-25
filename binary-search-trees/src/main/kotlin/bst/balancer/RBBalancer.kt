package bst.balancer

import bst.node.RedBlackTreeNode

internal class RBBalancer<E : Comparable<E>> : BinTreeBalancer<E, RedBlackTreeNode<E>> {
    override fun add(root: RedBlackTreeNode<E>?, value: E, unique: Boolean): RedBlackTreeNode<E> {
        TODO("Not yet implemented")
    }

    override fun remove(root: RedBlackTreeNode<E>?, value: E): RedBlackTreeNode<E>? {
        TODO("Not yet implemented")
    }
}