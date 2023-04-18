package app.model.bst.node

/**
 * Represents a node in an AVL Tree.
 *
 * @param E the type of element stored in the node
 * @see BinTreeNode
 */
interface AVLTreeNode<E : Comparable<E>> : BinTreeNode<E, AVLTreeNode<E>> {
    /**
     * The height of the node, which is the length of the longest path from the node to a leaf node.
     */
    var height: Int
}