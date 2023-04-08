package bst.node

abstract class AVLTreeNode<E : Comparable<E>> : BinTreeNode<E, AVLTreeNode<E>>() {
    internal abstract var height: Int
}
