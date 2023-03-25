package bst.wrapper

interface WrappedBinNode<E, Node : WrappedBinNode<E, Node>> {
    val value: E
    val left: Node?
    val right: Node?
}