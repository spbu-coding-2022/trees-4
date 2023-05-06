package app.model.bst

import app.model.bst.balancer.AVLBalancer
import app.model.bst.node.AVLTreeNode

/**
 * AVLTree is a self-balancing binary search tree
 * It uses the AVLBalancer to maintain balance and ensure that the height of the left and right subtrees of any node
 * differ by at most one.
 *
 * @param E the type of elements stored in the tree
 *
 * @see BinarySearchTree
 * @see AVLBalancer
 */
class AVLTree<E : Comparable<E>> :
    BinarySearchTree<E, AVLTreeNode<E>>(
        balancer = AVLBalancer(),
    )
