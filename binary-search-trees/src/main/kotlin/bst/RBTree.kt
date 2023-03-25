package bst

import bst.balancer.RBBalancer
import bst.node.RedBlackTreeNode
import bst.wrapper.WrappedRBNode

class RBTree<E : Comparable<E>> :
    BinarySearchTree<E, RedBlackTreeNode<E>, WrappedRBNode<E>>(
        balancer = RBBalancer(),
        wrap = ::WrappedRBNode
    )
