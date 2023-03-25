package bst.wrapper

import bst.node.AVLTreeNode

class WrappedAVLNode<E : Comparable<E>>(private val node: AVLTreeNode<E>) :
    WrappedBinNode<E, WrappedAVLNode<E>> {
    override val value: E
        get() = node.value
    override val left: WrappedAVLNode<E>?
        get() = node.left?.let { WrappedAVLNode(it) }
    override val right: WrappedAVLNode<E>?
        get() = node.right?.let { WrappedAVLNode(it) }

    val height: Int
        get() = node.height

    override fun toString() = "$value($height)"
}