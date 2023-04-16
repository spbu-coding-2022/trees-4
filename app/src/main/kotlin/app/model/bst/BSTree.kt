package app.model.bst

import app.model.bst.balancer.BSBalancer
import app.model.bst.node.BinSearchTreeNode

class BSTree<E : Comparable<E>> :
    BinarySearchTree<E, BinSearchTreeNode<E>>(
        balancer = BSBalancer(),
    )
