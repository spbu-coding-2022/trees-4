package app.model.bst

import app.model.bst.balancer.AVLBalancer
import app.model.bst.node.AVLTreeNode

class AVLTree<E : Comparable<E>> :
    BinarySearchTree<E, AVLTreeNode<E>>(
        balancer = AVLBalancer(),
    )
