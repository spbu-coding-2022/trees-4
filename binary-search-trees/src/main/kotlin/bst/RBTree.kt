package bst

import bst.balancer.RBBalancer
import bst.node.RedBlackTreeNode

class RBTree<E : Comparable<E>> :
    BinarySearchTree<E, RedBlackTreeNode<E>>(
        balancer = RBBalancer(),
    )
