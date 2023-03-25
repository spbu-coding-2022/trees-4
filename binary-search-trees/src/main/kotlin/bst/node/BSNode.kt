package bst.node

internal data class BSNode<E : Comparable<E>>(
    override var value: E,
    override var left: BinSearchTreeNode<E>? = null,
    override var right: BinSearchTreeNode<E>? = null,
) : BinSearchTreeNode<E>