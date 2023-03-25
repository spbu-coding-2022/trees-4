package bst.node

interface BinTreeNode<E : Comparable<E>, Subtype : BinTreeNode<E, Subtype>> {
    var value: E
    var left: Subtype?
    var right: Subtype?
}