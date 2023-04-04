package utils

import bst.BinarySearchTree
import bst.node.BinTreeNode
import bst.wrapper.WrappedAVLNode
import bst.wrapper.WrappedBinNode
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

    fun <E: Comparable<E>> checkNeighborHeights(node: WrappedAVLNode<E>?): Boolean {
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
    }
}