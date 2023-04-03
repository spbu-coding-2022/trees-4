package bst.wrapper

import bst.node.RedBlackTreeNode

class WrappedRBNode<E : Comparable<E>>(private val node: RedBlackTreeNode<E>) :
    WrappedBinNode<E, WrappedRBNode<E>> {
    val color: RedBlackTreeNode.Color
        get() = node.color

    override val value: E
        get() = node.value

    val parent: WrappedRBNode<E>?
        get() = node.parent?.let { WrappedRBNode(it) }

    override val left: WrappedRBNode<E>?
        get() = node.left?.let { WrappedRBNode(it) }

    override val right: WrappedRBNode<E>?
        get() = node.right?.let { WrappedRBNode(it) }

    override fun toString() = "$value($color)"
}
