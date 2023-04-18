package app.model.bst.node

/**
 * Represents a node in a Red-Black Tree.
 *
 * @param E the type of element stored in the node
 * @property value the value stored in the node.
 * @property parent the parent node of this node, or null if this node is the root of the tree.
 * @property color the color of the node, which can be either RED or BLACK.
 * @property left the left child node of this node, or null if this node has no left child.
 * @property right the right child node of this node, or null if this node has no right child.
 * @see RedBlackTreeNode
 */
internal class RBNode<E : Comparable<E>>(
    override var value: E,
    override var parent: RedBlackTreeNode<E>? = null,
    override var color: RedBlackTreeNode.Color = RedBlackTreeNode.Color.RED,
    override var left: RedBlackTreeNode<E>? = null,
    override var right: RedBlackTreeNode<E>? = null,
) : RedBlackTreeNode<E>