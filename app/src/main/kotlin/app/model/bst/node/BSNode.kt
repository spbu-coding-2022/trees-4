package app.model.bst.node

/**
 * Represents a node in a Binary Search Tree.
 *
 * @param E the type of element stored in the node
 * @property value the value stored in the node.
 * @property left the left child node of this node, or null if this node has no left child.
 * @property right the right child node of this node, or null if this node has no right child.
 * @see BinSearchTreeNode
 */
internal class BSNode<E : Comparable<E>>(
    override var value: E,
    override var left: BinSearchTreeNode<E>? = null,
    override var right: BinSearchTreeNode<E>? = null,
) : BinSearchTreeNode<E>