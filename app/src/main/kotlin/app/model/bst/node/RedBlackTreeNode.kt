package app.model.bst.node

/**
 * Represents a node in a Red-Black Tree.
 *
 * @param E the type of element stored in the node
 * @see BinTreeNode
 */
interface RedBlackTreeNode<E : Comparable<E>> : BinTreeNode<E, RedBlackTreeNode<E>> {
    /**
     * The color of the node, which can be either RED or BLACK.
     */
    var color: Color

    /**
     * The parent node of this node, or null if this node is the root of the tree.
     */
    var parent: RedBlackTreeNode<E>?

    /**
     * An enum representing the possible colors of a Red-Black Tree node.
     */
    enum class Color {
        RED, BLACK
    }
}