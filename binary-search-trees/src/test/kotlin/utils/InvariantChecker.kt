package utils

import bst.AVLTree
import bst.BinarySearchTree
import bst.RBTree
import bst.node.AVLTreeNode
import bst.node.BinTreeNode
import bst.node.RedBlackTreeNode
import java.util.*
import kotlin.math.abs

object InvariantChecker {
    fun <E : Comparable<E>, NodeType : BinTreeNode<E, NodeType>>
            isBinarySearchTree(
        bst: BinarySearchTree<E, NodeType>
    ): Boolean {
        var currentNode = bst.root
        var prevNode: NodeType? = null
        val stack = Stack<NodeType>()

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

    fun <E : Comparable<E>> checkNeighborHeights(tree: AVLTree<E>?): Boolean {
        fun helper(node: AVLTreeNode<E>?): Boolean {
            if (node == null) {
                return true
            }

            val leftHeight = calculateHeight(node.left)
            val rightHeight = calculateHeight(node.right)

            if (abs(leftHeight - rightHeight) > 1) {
                return false
            }

            return helper(node.left) && helper(node.right)
        }
        return helper(tree?.root)
    }

    private fun <E : Comparable<E>> calculateHeight(node: AVLTreeNode<E>?): Int {
        if (node == null) {
            return 0
        }
        val leftHeight = calculateHeight(node.left)
        val rightHeight = calculateHeight(node.right)
        return 1 + maxOf(leftHeight, rightHeight)
    }


    fun <E : Comparable<E>> isBlackHeightBalanced(tree: RBTree<E>?): Boolean {
        fun helper(root: RedBlackTreeNode<E>?): Boolean {
            root ?: return true

            val leftHeight = blackHeight(root.left)
            val rightHeight = blackHeight(root.right)
            if (leftHeight != rightHeight) {
                return false
            }

            return helper(root.left) && helper(root.right)
        }
        return helper(tree?.root)
    }

    private fun <E : Comparable<E>> blackHeight(node: RedBlackTreeNode<E>?): Int {
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

    fun <E : Comparable<E>> isParentLinkedRight(tree: RBTree<E>?): Boolean {
        fun helper(root: RedBlackTreeNode<E>?): Boolean {
            root ?: return true
            if (root.parent != null && (root !== root.parent?.left && root !== root.parent?.right)) return false

            return helper(root.left) && helper(root.right)
        }

        return helper(tree?.root)
    }
}