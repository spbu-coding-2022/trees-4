package bst.balancer

import bst.node.BinSearchTreeNode

internal class BSBalancer<E : Comparable<E>> : BinTreeBalancer<E, BinSearchTreeNode<E>> {
    override fun add(root: BinSearchTreeNode<E>?, value: E, unique: Boolean): BinSearchTreeNode<E> {
        TODO("Not yet implemented")
    }

    override fun remove(root: BinSearchTreeNode<E>?, value: E): BinSearchTreeNode<E>? {
        TODO("Not yet implemented")
    }
}