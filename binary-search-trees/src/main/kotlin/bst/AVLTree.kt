package bst

import bst.balancer.AVLBalancer
import bst.node.AVLTreeNode
import bst.wrapper.WrappedAVLNode

class AVLTree<E : Comparable<E>> :
    BinarySearchTree<E, AVLTreeNode<E>, WrappedAVLNode<E>>(
        balancer = AVLBalancer(),
        wrap = ::WrappedAVLNode
    )
