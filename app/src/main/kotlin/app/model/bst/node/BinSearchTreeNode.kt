package app.model.bst.node

interface BinSearchTreeNode<E : Comparable<E>> : BinTreeNode<E, BinSearchTreeNode<E>>
