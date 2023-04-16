package app.model.bst.node

interface RedBlackTreeNode<E : Comparable<E>> : BinTreeNode<E, RedBlackTreeNode<E>> {
    var color: Color
    var parent: RedBlackTreeNode<E>?
    enum class Color {
        RED, BLACK
    }
}
