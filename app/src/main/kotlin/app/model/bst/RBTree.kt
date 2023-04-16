package app.model.bst

import app.model.bst.balancer.RBBalancer
import app.model.bst.node.RedBlackTreeNode

class RBTree<E : Comparable<E>> :
    BinarySearchTree<E, RedBlackTreeNode<E>>(
        balancer = RBBalancer(),
    )
