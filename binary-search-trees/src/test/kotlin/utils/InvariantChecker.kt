package utils

import bst.BinarySearchTree
import bst.RBTree
import bst.node.BinTreeNode
import bst.wrapper.WrappedAVLNode
import bst.node.RedBlackTreeNode
import bst.wrapper.WrappedBinNode
import bst.wrapper.WrappedRBNode
import java.util.*
import kotlin.math.abs

object InvariantChecker {
    fun <E : Comparable<E>, NodeType : BinTreeNode<E, NodeType>, WrappedType : WrappedBinNode<E, WrappedType>> // python actually is better
            isBinarySearchTree(
        bst: BinarySearchTree<E, NodeType, WrappedType>
    ): Boolean {
        var currentNode = bst.wrappedRoot
        var prevNode: WrappedType? = null
        val stack = Stack<WrappedType>()

        while (currentNode != null || stack.isNotEmpty()) {
            while (currentNode != null) {
                stack.push(currentNode)
                currentNode = currentNode.left
            }

            currentNode = stack.pop()
            if (prevNode != null && currentNode.value < prevNode.value) {
                return false
            }
            prevNode = currentNode

            currentNode = currentNode.right
        }

        return true
    }

    fun <E : Comparable<E>> checkNeighborHeights(node: WrappedAVLNode<E>?): Boolean {
        if (node == null) {
            return true
        }

        val leftHeight = calculateHeight(node.left)
        val rightHeight = calculateHeight(node.right)

        if (abs(leftHeight - rightHeight) > 1) {
            return false
        }

        return checkNeighborHeights(node.left) && checkNeighborHeights(node.right)
    }

    private fun <T : Comparable<T>> calculateHeight(node: WrappedAVLNode<T>?): Int {
        if (node == null) {
            return 0
        }
        val leftHeight = calculateHeight(node.left)
        val rightHeight = calculateHeight(node.right)
        return 1 + maxOf(leftHeight, rightHeight)

        private fun <E : Comparable<E>> isBlack(root: WrappedRBNode<E>?): Boolean {
            root ?: return true
            return root.color == RedBlackTreeNode.Color.BLACK
        }

        fun <E : Comparable<E>> isColoredRight(tree: RBTree<E>?): Boolean {
            fun helper(root: WrappedRBNode<E>?): Boolean {
                root ?: return true
                if (root.color == RedBlackTreeNode.Color.RED && !isBlack(root.parent)
                )
                    return false

                return helper(root.left) && helper(root.right)
            }
            return helper(tree?.wrappedRoot) && isBlack(tree?.wrappedRoot)
        }

        fun <E : Comparable<E>> isBlackHeightBalanced(tree: RBTree<E>?): Boolean {
            fun helper(root: WrappedRBNode<E>?): Boolean {
                root ?: return true

                val leftHeight = blackHeight(root.left)
                val rightHeight = blackHeight(root.right)
                if (leftHeight != rightHeight) {
                    return false
                }

                return helper(root.left) && helper(root.right)
            }
            return helper(tree?.wrappedRoot)
        }

        private fun <E : Comparable<E>> blackHeight(node: WrappedRBNode<E>?): Int {
            if (node == null) {
                return 0
            }

            val leftHeight = blackHeight(node.left)
            val rightHeight = blackHeight(node.right)

            return if (node.color == RedBlackTreeNode.Color.BLACK) {
                maxOf(leftHeight, rightHeight) + 1
            } else {
                maxOf(leftHeight, rightHeight)
            }
        }
    }