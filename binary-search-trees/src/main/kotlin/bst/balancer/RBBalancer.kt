package bst.balancer

import bst.node.RBNode
import bst.node.RedBlackTreeNode

class RBBalancer<E : Comparable<E>> : BinTreeBalancer<E, RedBlackTreeNode<E>> {
    private fun rotateLeft(node: RedBlackTreeNode<E>?, root: RedBlackTreeNode<E>?): RedBlackTreeNode<E>? {
        val child = node?.right ?: return root
        var newRoot = root

        node.right = child.left
        child.left?.parent = node
        child.parent = node.parent

        if (node.parent == null) newRoot = child
        else {
            if (node == node.parent?.left) node.parent?.left = child
            else node.parent?.right = child
        }
        child.left = node
        node.parent = child



        return newRoot
    }

    private fun rotateRight(node: RedBlackTreeNode<E>?, root: RedBlackTreeNode<E>?): RedBlackTreeNode<E>? {
        val child = node?.left ?: return root
        var newRoot = root

        node.left = child.right
        child.right?.parent = node
        child.parent = node.parent

        if (node.parent == null) newRoot = child
        else {
            if (node == node.parent?.left) node.parent?.left
            else node.parent?.right = child
        }

        child.right = node
        node.parent = child
        return newRoot
    }

    private fun fixAfterInsertion(node: RedBlackTreeNode<E>, root: RedBlackTreeNode<E>): RedBlackTreeNode<E>? {
        if (node.parent == null) return root
        var current: RedBlackTreeNode<E>? = node
        var newRoot: RedBlackTreeNode<E>? = root

        while (current?.parent?.color == RedBlackTreeNode.Color.RED) {
            if (current.parent == current.parent?.parent?.left) {
                val uncle = current.parent?.parent?.right
                if (uncle?.color == RedBlackTreeNode.Color.RED) {
                    current.parent?.color = RedBlackTreeNode.Color.BLACK
                    uncle.color = RedBlackTreeNode.Color.BLACK
                    current.parent?.parent?.color = RedBlackTreeNode.Color.RED
                    current = current.parent?.parent
                } else {
                    if (current == current.parent?.right) {
                        current = current.parent
                        newRoot = rotateLeft(current, newRoot)
                    }
                    current?.parent?.color = RedBlackTreeNode.Color.BLACK
                    current?.parent?.parent?.color = RedBlackTreeNode.Color.RED
                    newRoot = rotateRight(current?.parent?.parent, newRoot)
                }

            } else {
                val uncle = current.parent?.parent?.left
                if (uncle?.color == RedBlackTreeNode.Color.RED) {
                    current.parent?.color = RedBlackTreeNode.Color.BLACK
                    uncle.color = RedBlackTreeNode.Color.BLACK
                    current.parent?.parent?.color = RedBlackTreeNode.Color.RED
                    current = current.parent?.parent
                } else {
                    if (current == current.parent?.left) {
                        current = current.parent
                        newRoot = rotateRight(current, newRoot)
                    }
                    current?.parent?.color = RedBlackTreeNode.Color.BLACK
                    current?.parent?.parent?.color = RedBlackTreeNode.Color.RED
                    newRoot = rotateLeft(current?.parent?.parent, newRoot)
                }
            }
        }
        newRoot?.color = RedBlackTreeNode.Color.BLACK

        return newRoot
    }

    override fun add(root: RedBlackTreeNode<E>?, value: E, unique: Boolean): RedBlackTreeNode<E> {
        val newNode = RBNode(value)

        fun addToSubtree(subtree: RedBlackTreeNode<E>?): RedBlackTreeNode<E> {
            val node = subtree ?: return newNode

            if (value < node.value) node.left = addToSubtree(node.left).also { it.parent = node }
            else if (value > node.value) node.right = addToSubtree(node.right).also { it.parent = node }
            else {
                if (unique) node.value = value
                else node.right = addToSubtree(node.right).also { it.parent = node }
            }

            return node
        }

        return fixAfterInsertion(newNode, addToSubtree(root))
            ?: throw Exception("after insertion root must be not null")
    }

    private fun findNode(node: RedBlackTreeNode<E>?, value: E): RedBlackTreeNode<E>? {
        node ?: return null
        if (value == node.value) {
            return node
        }
        return findNode(if (value < node.value) node.left else node.right, value)
    }

    private fun minValueNode(node: RedBlackTreeNode<E>): RedBlackTreeNode<E> {
        var current = node
        while (true) {
            current = current.left ?: break
        }
        return current
    }

    private fun fixAfterDeletion(node: RedBlackTreeNode<E>?, root: RedBlackTreeNode<E>?): RedBlackTreeNode<E>? {
        var newRoot = root
        var current = node
        var sibling: RedBlackTreeNode<E>?

        while (current != newRoot && current?.color == RedBlackTreeNode.Color.BLACK) {
            if (current == current.parent?.left) {
                sibling = current.parent?.right
                if (sibling?.color == RedBlackTreeNode.Color.RED) {
                    sibling.color = RedBlackTreeNode.Color.BLACK
                    current.parent?.color = RedBlackTreeNode.Color.RED
                    newRoot = rotateLeft(current.parent, newRoot)
                    sibling = current.parent?.right
                }
                if (sibling?.left?.color == RedBlackTreeNode.Color.BLACK && sibling.right?.color == RedBlackTreeNode.Color.BLACK) {
                    sibling.color = RedBlackTreeNode.Color.RED
                    current = current.parent
                } else {
                    if (sibling?.right?.color == RedBlackTreeNode.Color.BLACK) {
                        sibling.left?.color = RedBlackTreeNode.Color.BLACK
                        sibling.color = RedBlackTreeNode.Color.RED
                        newRoot = rotateRight(sibling, newRoot)
                        sibling = current.parent?.right
                    }
                    sibling?.color = current.parent?.color ?: RedBlackTreeNode.Color.BLACK
                    current.parent?.color = RedBlackTreeNode.Color.BLACK
                    sibling?.right?.color = RedBlackTreeNode.Color.BLACK
                    newRoot = rotateLeft(current.parent, newRoot)
                    current = newRoot
                }
            } else {
                sibling = current.parent?.left
                if (sibling?.color == RedBlackTreeNode.Color.RED) {
                    sibling.color = RedBlackTreeNode.Color.BLACK
                    current.parent?.color = RedBlackTreeNode.Color.RED
                    newRoot = rotateRight(current.parent, newRoot)
                    sibling = current.parent?.left
                }
                if (sibling?.right?.color == RedBlackTreeNode.Color.BLACK && sibling.left?.color == RedBlackTreeNode.Color.BLACK) {
                    sibling.color = RedBlackTreeNode.Color.RED
                    current = current.parent
                } else {
                    if (sibling?.left?.color == RedBlackTreeNode.Color.BLACK) {
                        sibling.right?.color = RedBlackTreeNode.Color.BLACK
                        sibling.color = RedBlackTreeNode.Color.RED
                        newRoot = rotateLeft(sibling, newRoot)
                        sibling = current.parent?.left
                    }
                    sibling?.color = current.parent?.color ?: RedBlackTreeNode.Color.BLACK
                    current.parent?.color = RedBlackTreeNode.Color.BLACK
                    sibling?.left?.color = RedBlackTreeNode.Color.BLACK
                    newRoot = rotateRight(current.parent, newRoot)
                    current = newRoot
                }
            }
        }
        current?.color = RedBlackTreeNode.Color.BLACK

        return newRoot
    }

    override fun remove(root: RedBlackTreeNode<E>?, value: E): RedBlackTreeNode<E>? {
        val node = findNode(root, value) ?: return root
        val successor: RedBlackTreeNode<E>

        if (node.left == null || node.right == null)
            successor = node
        else {
            successor = minValueNode(node.right ?: throw Exception("node.right must be not null"))
            node.value = successor.value
        }

        val child = successor.left ?: successor.right

        child?.parent = successor.parent
        var newRoot = if (successor.parent == null) child else root

        if (successor == successor.parent?.left) {
            successor.parent?.left = child
        } else {
            successor.parent?.right = child
        }

        if (successor.color == RedBlackTreeNode.Color.BLACK) {
            newRoot = fixAfterDeletion(child, newRoot)
        }

        return newRoot
    }

}