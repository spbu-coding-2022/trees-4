package app.model.bst

import app.model.bst.balancer.RBBalancer
import app.model.bst.node.RedBlackTreeNode

/**
 * RBTree is a self-balancing binary search tree
 * It uses the RBBalancer to maintain balance and ensure that the tree satisfies the red-black tree properties.
 *
 * 1. Every node is either red or black.
 * 2. The root node is always black.
 * 3. Every leaf node (null node) is black.
 * 4. If a node is red, then both its children are black.
 * 5. For each node, all simple paths from the node to descendant leaves contain the same number of black nodes.
 *
 * @param E the type of elements stored in the tree.
 *
 * @see BinarySearchTree
 * @see RBBalancer
 */
class RBTree<E : Comparable<E>> :
    BinarySearchTree<E, RedBlackTreeNode<E>>(
        balancer = RBBalancer(),
    )
