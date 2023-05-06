package app.model.bst

import app.model.bst.balancer.BSBalancer
import app.model.bst.node.BinSearchTreeNode

/**
 * BSTree is a binary search tree that implements the BinarySearchTree interface.
 * It uses the BSBalancer to insert values to the tree.
 *
 * @param E the type of elements stored in the tree
 *
 * @see BinarySearchTree
 * @see BSBalancer
 */
class BSTree<E : Comparable<E>> :
    BinarySearchTree<E, BinSearchTreeNode<E>>(
        balancer = BSBalancer(),
    )
