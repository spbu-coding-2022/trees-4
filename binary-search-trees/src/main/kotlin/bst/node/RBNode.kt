package bst.node

internal data class RBNode<E : Comparable<E>>(
    override var value: E,
    override var parent: RedBlackTreeNode<E>? = null,
    override var color: RedBlackTreeNode.Color = RedBlackTreeNode.Color.RED,
    override var left: RedBlackTreeNode<E>? = null,
    override var right: RedBlackTreeNode<E>? = null,
) : RedBlackTreeNode<E>