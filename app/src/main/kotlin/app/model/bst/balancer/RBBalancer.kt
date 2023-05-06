package app.model.bst.balancer

import app.model.bst.node.RBNode
import app.model.bst.node.RedBlackTreeNode

internal class RBBalancer<E : Comparable<E>> : BinTreeBalancer<E, RedBlackTreeNode<E>> {
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
            ?: throw IllegalStateException("Root after fixup must be not null")
    }

    private fun rotateLeft(node: RedBlackTreeNode<E>?, root: RedBlackTreeNode<E>?): RedBlackTreeNode<E>? {
        val rightChild = node?.right ?: throw IllegalStateException("Node to rotate must have a right child")
        node.right = rightChild.left

        if (rightChild.left != null) {
            rightChild.left?.parent = node
        }

        rightChild.parent = node.parent
        var newRoot = root

        if (node.parent == null) {
            newRoot = rightChild
        } else if (node === node.parent?.left) {
            node.parent?.left = rightChild
        } else {
            node.parent?.right = rightChild
        }

        rightChild.left = node
        node.parent = rightChild

        return newRoot
    }

    private fun rotateRight(node: RedBlackTreeNode<E>?, root: RedBlackTreeNode<E>?): RedBlackTreeNode<E>? {
        val leftChild = node?.left ?: throw IllegalStateException("Node to rotate must have a left child")
        node.left = leftChild.right

        if (leftChild.right != null) {
            leftChild.right?.parent = node
        }

        leftChild.parent = node.parent
        var newRoot = root

        if (node.parent == null) {
            newRoot = leftChild
        } else if (node === node.parent?.right) {
            node.parent?.right = leftChild
        } else {
            node.parent?.left = leftChild
        }

        leftChild.right = node
        node.parent = leftChild

        return newRoot
    }

    private fun fixAfterInsertion(node: RedBlackTreeNode<E>, root: RedBlackTreeNode<E>): RedBlackTreeNode<E>? {
        if (node.parent == null) return root.also { it.color = RedBlackTreeNode.Color.BLACK }
        var current: RedBlackTreeNode<E>? = node
        var newRoot: RedBlackTreeNode<E>? = root

        while (current?.parent?.color == RedBlackTreeNode.Color.RED) {
            if (current.parent === current.parent?.parent?.left) {
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

    override fun remove(root: RedBlackTreeNode<E>?, value: E): RedBlackTreeNode<E>? {
        var current = findNode(root, value) ?: return root
        var newRoot = root

        if (current.left != null && current.right != null) {
            val successor = minValueNode(
                current.right
                    ?: throw IllegalStateException("Impossible to find minValueNode from node without left child")
            )
            current.value = successor.value
            current = successor
        }

        val child = if (current.left != null) current.left else current.right
        if (child != null) {
            child.parent = current.parent
            if (current.parent == null) return child
            if (current == current.parent?.left) {
                current.parent?.left = child
            } else {
                current.parent?.right = child
            }

            if (current.color == RedBlackTreeNode.Color.BLACK) {
                newRoot = fixAfterDeletion(child, newRoot)
            }
        } else if (current.parent == null) {
            return null
        } else {
            if (current.color == RedBlackTreeNode.Color.BLACK) {
                newRoot = fixAfterDeletion(current, newRoot)
            }
            if (current.parent?.left == current) {
                current.parent?.left = null
            } else {
                current.parent?.right = null
            }
        }

        return newRoot
    }

    private fun takeColor(node: RedBlackTreeNode<E>?): RedBlackTreeNode.Color {
        return node?.color ?: RedBlackTreeNode.Color.BLACK
    }

    private fun fixAfterDeletion(node: RedBlackTreeNode<E>?, root: RedBlackTreeNode<E>?): RedBlackTreeNode<E>? {
        var newRoot = root
        var current = node
        var sibling: RedBlackTreeNode<E>?

        while (current !== newRoot && takeColor(current) == RedBlackTreeNode.Color.BLACK) {
            if (current === current?.parent?.left) {
                sibling = current?.parent?.right
                if (takeColor(sibling) == RedBlackTreeNode.Color.RED) {
                    sibling?.color = RedBlackTreeNode.Color.BLACK
                    current?.parent?.color = RedBlackTreeNode.Color.RED
                    newRoot = rotateLeft(current?.parent, newRoot)
                    sibling = current?.parent?.right
                }
                if (takeColor(sibling?.left) == RedBlackTreeNode.Color.BLACK && takeColor(sibling?.right) == RedBlackTreeNode.Color.BLACK) {
                    sibling?.color = RedBlackTreeNode.Color.RED
                    current = current?.parent
                } else {
                    if (takeColor(sibling?.right) == RedBlackTreeNode.Color.BLACK) {
                        sibling?.left?.color = RedBlackTreeNode.Color.BLACK
                        sibling?.color = RedBlackTreeNode.Color.RED
                        newRoot = rotateRight(sibling, newRoot)
                        sibling = current?.parent?.right
                    }
                    sibling?.color = current?.parent?.color ?: RedBlackTreeNode.Color.BLACK
                    current?.parent?.color = RedBlackTreeNode.Color.BLACK
                    sibling?.right?.color = RedBlackTreeNode.Color.BLACK
                    newRoot = rotateLeft(current?.parent, newRoot)
                    current = newRoot
                }
            } else {
                sibling = current?.parent?.left
                if (takeColor(sibling) == RedBlackTreeNode.Color.RED) {
                    sibling?.color = RedBlackTreeNode.Color.BLACK
                    current?.parent?.color = RedBlackTreeNode.Color.RED
                    newRoot = rotateRight(current?.parent, newRoot)
                    sibling = current?.parent?.left
                }
                if (takeColor(sibling?.right) == RedBlackTreeNode.Color.BLACK && takeColor(sibling?.left) == RedBlackTreeNode.Color.BLACK) {
                    sibling?.color = RedBlackTreeNode.Color.RED
                    current = current?.parent
                } else {
                    if (takeColor(sibling?.left) == RedBlackTreeNode.Color.BLACK) {
                        sibling?.right?.color = RedBlackTreeNode.Color.BLACK
                        sibling?.color = RedBlackTreeNode.Color.RED
                        newRoot = rotateLeft(sibling, newRoot)
                        sibling = current?.parent?.left
                    }
                    sibling?.color = current?.parent?.color ?: RedBlackTreeNode.Color.BLACK
                    current?.parent?.color = RedBlackTreeNode.Color.BLACK
                    sibling?.left?.color = RedBlackTreeNode.Color.BLACK
                    newRoot = rotateRight(current?.parent, newRoot)
                    current = newRoot

                }
            }
        }
        current?.color = RedBlackTreeNode.Color.BLACK
        newRoot?.parent = null
        return newRoot
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
}