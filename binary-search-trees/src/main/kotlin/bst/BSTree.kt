package bst

import bst.balancer.BSBalancer
import bst.node.BinSearchTreeNode

class BSTree<E : Comparable<E>> :
    BinarySearchTree<E, BinSearchTreeNode<E>>(
        balancer = BSBalancer(),
    )
