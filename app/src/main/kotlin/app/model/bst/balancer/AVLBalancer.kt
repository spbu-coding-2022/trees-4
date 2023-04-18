package app.model.bst.balancer

import app.model.bst.node.AVLNode
import app.model.bst.node.AVLTreeNode
import kotlin.math.max

internal class AVLBalancer<E : Comparable<E>> : BinTreeBalancer<E, AVLTreeNode<E>> {
    override fun add(root: AVLTreeNode<E>?, value: E, unique: Boolean): AVLTreeNode<E> {
        fun insertToSubtree(subtree: AVLTreeNode<E>?): AVLTreeNode<E>? {
            if (subtree == null) {
                return AVLNode(value)
            }

            if (value < subtree.value) {
                subtree.left = insertToSubtree(subtree.left)
            } else if (value > subtree.value) {
                subtree.right = insertToSubtree(subtree.right)
            } else {
                if (unique) return subtree
                else subtree.right = insertToSubtree(subtree.right)
            }

            return balance(subtree)
        }
        return insertToSubtree(root) ?: throw IllegalStateException("Root after fixup must be not null")
    }

    private fun balance(subtree: AVLTreeNode<E>): AVLTreeNode<E>? {
        subtree.height = 1 + max(height(subtree.left), height(subtree.right))

        val balance = getBalance(subtree)

        if (balance > 1 && getBalance(subtree.left) >= 0) {
            return rotateRight(subtree)
        }

        if (balance > 1 && getBalance(subtree.left) < 0) {
            subtree.left = rotateLeft(subtree.left)
            return rotateRight(subtree)
        }

        if (balance < -1 && getBalance(subtree.right) <= 0) {
            return rotateLeft(subtree)
        }

        if (balance < -1 && getBalance(subtree.right) > 0) {
            subtree.right = rotateRight(subtree.right)
            return rotateLeft(subtree)
        }

        return subtree
    }

    private fun height(node: AVLTreeNode<E>?): Int {
        return node?.height ?: 0
    }

    private fun getBalance(node: AVLTreeNode<E>?): Int {
        return if (node == null) 0 else height(node.left) - height(node.right)
    }

    private fun rotateRight(y: AVLTreeNode<E>?): AVLTreeNode<E>? {
        val x = y?.left
        val child = x?.right

        x?.right = y
        y?.left = child

        y?.height = max(height(y?.left), height(y?.right)) + 1
        x?.height = 1 + max(height(x?.left), height(x?.right))

        return x
    }

    private fun rotateLeft(x: AVLTreeNode<E>?): AVLTreeNode<E>? {
        val y = x?.right
        val child = y?.left

        y?.left = x
        x?.right = child

        x?.height = 1 + max(height(x?.left), height(x?.right))
        y?.height = 1 + max(height(y?.left), height(y?.right))

        return y
    }

    private fun minValueNode(node: AVLTreeNode<E>): AVLTreeNode<E> {
        var current = node
        while (true) {
            current = current.left ?: break
        }
        return current
    }

    override fun remove(root: AVLTreeNode<E>?, value: E): AVLTreeNode<E>? {
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
                    minValueNode(
                        root.right
                            ?: throw IllegalStateException("Impossible to find minValueNode from node without right child")
                    )
                root.value = temp.value
                root.right = remove(root.right, temp.value)
            }
        }
        return balance(root)
    }
}
