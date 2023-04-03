package bst.balancer

import bst.node.BSNode
import bst.node.BinSearchTreeNode

internal class BSBalancer<E : Comparable<E>> : BinTreeBalancer<E, BinSearchTreeNode<E>> {
    override fun add(root: BinSearchTreeNode<E>?, value: E, unique: Boolean): BinSearchTreeNode<E> {
        if (root == null) {
            return BSNode(value)
        }

        if (value < root.value) {
            root.left = add(root.left, value, unique)
        } else if (value > root.value) {
            root.right = add(root.right, value, unique)
        } else {
            if (unique) return root
            else root.right = add(root.right, value, false)
        }
        return root
    }

    override fun remove(root: BinSearchTreeNode<E>?, value: E): BinSearchTreeNode<E>? {
        if (root == null) {
            return null
        }

        if (value < root.value) {
            root.left = remove(root.left, value)
        } else if (value > root.value) {
            root.right = remove(root.right, value)
        } else {
            if (root.left == null || root.right == null) {
                return root.left ?: root.right
            } else {
                val temp =
                    minValueNode(root.right ?: throw Exception("root.right must be not null"))
                root.value = temp.value
                root.right = remove(root.right, temp.value)
            }
        }
        return root
    }

    private fun minValueNode(node: BinSearchTreeNode<E>): BinSearchTreeNode<E> {
        var current = node
        while (true) {
            current = current.left ?: break
        }
        return current
    }
}