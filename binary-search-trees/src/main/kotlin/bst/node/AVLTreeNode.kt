package bst.node

interface AVLTreeNode<E : Comparable<E>> : BinTreeNode<E, AVLTreeNode<E>> {
    var height: Int
}