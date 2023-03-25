package bst

import bst.balancer.BSBalancer
import bst.node.BinSearchTreeNode
import bst.wrapper.WrappedBSNode

class BSTree<E : Comparable<E>> :
    BinarySearchTree<E, BinSearchTreeNode<E>, WrappedBSNode<E>>(
        balancer = BSBalancer(),
        wrap = ::WrappedBSNode
    )