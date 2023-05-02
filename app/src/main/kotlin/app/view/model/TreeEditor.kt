package app.view.model

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import app.model.bst.AVLTree
import app.model.bst.BSTree
import app.model.bst.BinarySearchTree
import app.model.bst.RBTree
import app.model.bst.node.AVLTreeNode
import app.model.bst.node.BinSearchTreeNode
import app.model.bst.node.BinTreeNode
import app.model.bst.node.RedBlackTreeNode
import app.view.DrawableNode
import app.view.defaultNodeSize


abstract class TreeEditor<N : BinTreeNode<String, N>, BST : BinarySearchTree<String, N>> {
    abstract val tree: BST

    fun resetCoordinates(node: DrawableNode?) {
        node?.let {
            calcLeft(node, 0.dp, 0.dp)
            calcRight(node, 0.dp, 0.dp)
        }
    }

    private fun countWidth(node: DrawableNode?): Pair<Int, Int> {
        var leftWidth = 0
        var rightWidth = 0
        if (node?.left != null) {
            leftWidth = countWidth(node.left).toList().sum() + 1
        }
        if (node?.right != null) {
            rightWidth = countWidth(node.right).toList().sum() + 1
        }
        return Pair(leftWidth, rightWidth)
    }

    private fun calcLeft(node: DrawableNode?, parentX: Dp, parentY: Dp) {
        var count = 0
        if (node?.right != null) {
            count = countWidth(node).second
        }
        val x = parentX - defaultNodeSize - (defaultNodeSize * count)
        val y = parentY + defaultNodeSize
        node?.x = x
        node?.y = y
        if (node?.left != null) calcLeft(node.left, x, y)
        if (node?.right != null) calcRight(node.right, x, y)
    }


    private fun calcRight(node: DrawableNode?, parentX: Dp, parentY: Dp) {
        var count = 0
        if (node?.left != null) {
            count = countWidth(node).first
        }
        val x = parentX + defaultNodeSize + (defaultNodeSize * count)
        val y = parentY + defaultNodeSize
        node?.x = x
        node?.y = y
        if (node?.left != null) calcLeft(node.left, x, y)
        if (node?.right != null) calcRight(node.right, x, y)
    }

    fun addToTree(value: String): DrawableNode? {
        this.tree.add(value)
        return toDrawableNode(this.tree.root).also { resetCoordinates(it) }
    }

    fun removeFromTree(value: String): DrawableNode? {
        this.tree.remove(value)
        return toDrawableNode(this.tree.root).also { resetCoordinates(it) }
    }

    fun findNodeInTree(value: String): DrawableNode? {
        fun findNode(node: N?): N? {
            node ?: return null
            if (value == node.value) {
                return node
            }
            return findNode(if (value < node.value) node.left else node.right)
        }
        return toDrawableNode(findNode(tree.root))
    }

    abstract fun toDrawableNode(node: N?): DrawableNode?
}

class BSTreeEditor : TreeEditor<BinSearchTreeNode<String>, BinarySearchTree<String, BinSearchTreeNode<String>>>() {
    override fun toDrawableNode(node: BinSearchTreeNode<String>?): DrawableNode? {
        return node?.let {
            DrawableNode(
                node.value,
                left = toDrawableNode(node.left),
                right = toDrawableNode(node.right),
            )
        }
    }

    override val tree = BSTree<String>()
}


class RBTreeEditor : TreeEditor<RedBlackTreeNode<String>, BinarySearchTree<String, RedBlackTreeNode<String>>>() {
    override fun toDrawableNode(node: RedBlackTreeNode<String>?): DrawableNode? {
        return node?.let {
            DrawableNode(
                node.value,
                left = toDrawableNode(node.left),
                right = toDrawableNode(node.right),
                color = if (it.color == RedBlackTreeNode.Color.RED) Color.Red else Color.DarkGray
            )
        }
    }

    override val tree = RBTree<String>()
}


class AVLTreeEditor : TreeEditor<AVLTreeNode<String>, BinarySearchTree<String, AVLTreeNode<String>>>() {
    override fun toDrawableNode(node: AVLTreeNode<String>?): DrawableNode? {
        return node?.let {
            DrawableNode(
                node.value,
                left = toDrawableNode(node.left),
                right = toDrawableNode(node.right),
            )
        }
    }

    override val tree = AVLTree<String>()
}
