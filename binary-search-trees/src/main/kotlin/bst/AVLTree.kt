package bst

import bst.balancer.AVLBalancer
import bst.node.AVLTreeNode

class AVLTree<E : Comparable<E>> :
    BinarySearchTree<E, AVLTreeNode<E>>(
        balancer = AVLBalancer(),
    )
