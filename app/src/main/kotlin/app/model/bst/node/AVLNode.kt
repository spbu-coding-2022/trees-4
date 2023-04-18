package app.model.bst.node

/**
 * Internal class representing a node in an AVL tree.
 * @param E the type of element stored in the node, must implement Comparable interface.
 * @property value the value stored in the node.
 * @property height the height of the node in the tree.
 * @property left the left child of the node.
 * @property right the right child of the node.
 * @constructor creates a new AVLNode with the given value, height, left and right children.
 */
internal class AVLNode<E : Comparable<E>>(
    override var value: E,
    override var height: Int = 1,
    override var left: AVLTreeNode<E>? = null,
    override var right: AVLTreeNode<E>? = null,
) : AVLTreeNode<E>
