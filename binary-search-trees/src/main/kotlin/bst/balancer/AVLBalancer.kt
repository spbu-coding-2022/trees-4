package bst.balancer

import bst.node.AVLTreeNode

internal class AVLBalancer<E : Comparable<E>> : BinTreeBalancer<E, AVLTreeNode<E>> {
    override fun add(root: AVLTreeNode<E>?, value: E, unique: Boolean): AVLTreeNode<E> {
        TODO("Not yet implemented")
    }

    override fun remove(root: AVLTreeNode<E>?, value: E): AVLTreeNode<E>? {
        TODO("Not yet implemented")
    }
}
