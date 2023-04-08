package bst.node

abstract class BinTreeNode<E : Comparable<E>, Subtype : BinTreeNode<E, Subtype>> {
    abstract var value: E
        internal set
    abstract var left: Subtype?
        internal set
    abstract var right: Subtype?
        internal set
}
