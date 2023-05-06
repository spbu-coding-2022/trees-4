package app.model.bst.node

/**
 * Represents a node in any Binary Tree.
 *
 * @param E the type of element stored in the node
 * @param Subtype the subtype of the node
 * @property value the value stored in the node.
 * @property left the left child node of this node, or null if this node has no left child.
 * @property right the right child node of this node, or null if this node has no right child.
 */
interface BinTreeNode<E : Comparable<E>, Subtype : BinTreeNode<E, Subtype>> {
    var value: E
    var left: Subtype?
    var right: Subtype?
}