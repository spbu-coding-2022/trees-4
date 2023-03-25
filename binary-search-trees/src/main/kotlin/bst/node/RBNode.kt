package bst.node

internal data class RBNode<E : Comparable<E>>(
    override var value: E,
    override var color: RedBlackTreeNode.Color = RedBlackTreeNode.Color.RED,
    override var left: RedBlackTreeNode<E>? = null,
    override var right: RedBlackTreeNode<E>? = null,
) : RedBlackTreeNode<E> {
    override fun flipColor() {
        color = when (color) {
            RedBlackTreeNode.Color.BLACK -> RedBlackTreeNode.Color.RED
            else -> RedBlackTreeNode.Color.BLACK
        }
    }
}

internal fun <E : Comparable<E>> RedBlackTreeNode<E>?.isBlack(): Boolean {
    if (this == null) return true
    return this.color == RedBlackTreeNode.Color.BLACK
}

internal fun <E : Comparable<E>> RedBlackTreeNode<E>?.isRed(): Boolean {
    return !this.isBlack()
}
