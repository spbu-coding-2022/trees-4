package bst.node

interface RedBlackTreeNode<E : Comparable<E>> : BinTreeNode<E, RedBlackTreeNode<E>> {
    var color: Color
    fun flipColor()
    enum class Color {
        RED, BLACK
    }
}
