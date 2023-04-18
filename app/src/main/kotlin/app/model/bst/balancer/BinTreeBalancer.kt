package app.model.bst.balancer

import app.model.bst.node.BinTreeNode

/**
 * An interface for a binary tree balancer.
 * @param E the type of element stored in the tree, must implement Comparable interface.
 * @param NodeType the type of node used in the tree, must implement BinTreeNode interface.
 */
interface BinTreeBalancer<E : Comparable<E>, NodeType : BinTreeNode<E, NodeType>> {
    /**
     * Adds a new node with a value to the root's subtree, and rebalances it if necessary.
     * @param root the root node of the subtree to add the new node to.
     * @param value the value to add to the tree.
     * @param unique whether to insert only unique values in the tree.
     * @return the new root node of the subtree.
     */
    fun add(root: NodeType?, value: E, unique: Boolean): NodeType

    /**
     * Removes a node with a value from the root's subtree, and rebalances it if necessary.
     * @param root the root node of the subtree to remove the node from.
     * @param value the value to remove from the tree.
     * @return the new root node of the subtree.
     */
    fun remove(root: NodeType?, value: E): NodeType?
}