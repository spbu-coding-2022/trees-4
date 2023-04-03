package utils

import bst.BinarySearchTree
import bst.node.BinTreeNode
import bst.wrapper.WrappedBinNode
import java.util.*

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
}