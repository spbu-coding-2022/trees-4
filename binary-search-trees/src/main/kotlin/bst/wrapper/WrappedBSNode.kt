package bst.wrapper

import bst.node.BinSearchTreeNode

class WrappedBSNode<E : Comparable<E>>(private val node: BinSearchTreeNode<E>) :
    WrappedBinNode<E, WrappedBSNode<E>> {
    override val value: E
        get() = node.value
    override val left: WrappedBSNode<E>?
        get() = node.left?.let { WrappedBSNode(it) }
    override val right: WrappedBSNode<E>?
        get() = node.right?.let { WrappedBSNode(it) }

    override fun toString() = "$value"
}