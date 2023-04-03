package bst.node

internal class AVLNode<E : Comparable<E>>(
    override var value: E,
    override var height: Int = 1,
    override var left: AVLTreeNode<E>? = null,
    override var right: AVLTreeNode<E>? = null,
) : AVLTreeNode<E>