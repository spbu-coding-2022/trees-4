package app.model.repo.serialization.strategy

import app.model.bst.node.RBNode
import app.model.bst.node.RedBlackTreeNode
import app.model.repo.serialization.Metadata
import app.model.repo.serialization.SerializableNode
import app.model.repo.serialization.SerializableValue
import app.model.repo.serialization.TypeOfTree

class RBTreeStrategy<E : Comparable<E>>(
    serializeValue: (E) -> SerializableValue,
    deserializeValue: (SerializableValue) -> E
) : SerializationStrategy<E, RedBlackTreeNode<E>, RedBlackTreeNode.Color>(serializeValue, deserializeValue) {
    override val typeOfTree: TypeOfTree = TypeOfTree.RED_BLACK_TREE
    override fun buildNode(node: SerializableNode?): RedBlackTreeNode<E>? {
        fun buildNodeWithParent(node: SerializableNode?, parent: RedBlackTreeNode<E>?): RedBlackTreeNode<E>? {
            node ?: return null
            val rbNode = RBNode(
                value = deserializeValue(node.value),
                color = deserializeMetadata(node.metadata)
            )
            rbNode.parent = parent
            rbNode.left = buildNodeWithParent(node.left, rbNode)
            rbNode.right = buildNodeWithParent(node.right, rbNode)
            return rbNode
        }

        return buildNodeWithParent(node, null)
    }

    override fun deserializeMetadata(metadata: Metadata): RedBlackTreeNode.Color {
        return when (metadata.value) {
            "RED" -> RedBlackTreeNode.Color.RED
            "BLACK" -> RedBlackTreeNode.Color.BLACK
            else -> throw IllegalArgumentException("Can deserialize 'RED', 'BLACK' metadata value strings only")
        }
    }

    override fun serializeMetadata(node: RedBlackTreeNode<E>): Metadata {
        return Metadata(
            when (node.value) {
                RedBlackTreeNode.Color.RED -> "RED"
                else -> "BLACK"
            }
        )
    }

}