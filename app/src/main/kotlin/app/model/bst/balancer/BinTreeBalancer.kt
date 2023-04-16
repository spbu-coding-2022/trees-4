package app.model.bst.balancer

import app.model.bst.node.BinTreeNode

interface BinTreeBalancer<E : Comparable<E>, NodeType : BinTreeNode<E, NodeType>> {
    /**
     * Adds a new node with a value to the root's subtree, and rebalances it if necessary.
     */
    fun add(root: NodeType?, value: E, unique: Boolean): NodeType

    /**
     * Removes a node with a value from the root's subtree, and rebalances it if necessary.
     */
    fun remove(root: NodeType?, value: E): NodeType?
}