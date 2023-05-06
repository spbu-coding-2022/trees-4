package app.model.bst.node

/**
 * Represents a node in a Binary Search Tree.
 *
 * @param E the type of element stored in the node
 * @see BinTreeNode
 */
interface BinSearchTreeNode<E : Comparable<E>> : BinTreeNode<E, BinSearchTreeNode<E>> {
}