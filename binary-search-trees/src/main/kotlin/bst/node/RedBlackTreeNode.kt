package bst.node

abstract class RedBlackTreeNode<E : Comparable<E>> : BinTreeNode<E, RedBlackTreeNode<E>>() {
    abstract var color: Color
        internal set
    abstract var parent: RedBlackTreeNode<E>?
        internal set

    enum class Color {
        RED, BLACK
    }
}
