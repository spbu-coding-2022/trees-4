package app.model.repo.serialization.strategy

import app.model.bst.node.RBNode
import app.model.bst.node.RedBlackTreeNode
import app.model.repo.serialization.Metadata
import app.model.repo.serialization.SerializableNode
import app.model.repo.serialization.SerializableValue
import app.model.repo.serialization.TypeOfTree

/**
 * A class representing a strategy for serializing and deserializing red-black tree data structures.
 *
 * @param E the type of element stored in the red-black tree
 * @property serializeValue a function that serializes an element of type E into a [SerializableValue]
 * @property deserializeValue a function that deserializes a [SerializableValue] into an element of type E
 * @constructor creates a new [RBTreeStrategy] with the given serialization and deserialization functions
 */
class RBTreeStrategy<E : Comparable<E>>(
    serializeValue: (E) -> SerializableValue,
    deserializeValue: (SerializableValue) -> E
) : SerializationStrategy<E, RedBlackTreeNode<E>, RedBlackTreeNode.Color>(serializeValue, deserializeValue) {

    /**
     * The type of tree being serialized or deserialized.
     */
    override val typeOfTree: TypeOfTree = TypeOfTree.RED_BLACK_TREE

    /**
     * Builds a [RedBlackTreeNode] from a [SerializableNode].
     *
     * @param node the [SerializableNode] to build the [RedBlackTreeNode] from
     * @return the [RedBlackTreeNode] built from the [SerializableNode], or null if the [SerializableNode] is null
     */
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

    /**
     * Deserializes metadata of type [RedBlackTreeNode.Color] from a [Metadata] object.
     *
     * @param metadata the [Metadata] object to deserialize the metadata from
     * @return the deserialized metadata of type [RedBlackTreeNode.Color]
     * @throws IllegalArgumentException if the metadata value is not "RED" or "BLACK"
     */
    override fun deserializeMetadata(metadata: Metadata): RedBlackTreeNode.Color {
        return when (metadata.value) {
            "RED" -> RedBlackTreeNode.Color.RED
            "BLACK" -> RedBlackTreeNode.Color.BLACK
            else -> throw IllegalArgumentException("Can deserialize 'RED', 'BLACK' metadata value strings only")
        }
    }

    /**
     * Serializes metadata of type [RedBlackTreeNode.Color] into a [Metadata] object.
     *
     * @param node the node to serialize the metadata from
     * @return the serialized metadata as a [Metadata] object
     */
    override fun serializeMetadata(node: RedBlackTreeNode<E>): Metadata {
        return Metadata(
            when (node.value) {
                RedBlackTreeNode.Color.RED -> "RED"
                else -> "BLACK"
            }
        )
    }

}