package app.model.repo.serialization.strategy

import app.model.bst.node.BSNode
import app.model.bst.node.BinSearchTreeNode
import app.model.repo.serialization.Metadata
import app.model.repo.serialization.SerializableNode
import app.model.repo.serialization.SerializableValue
import app.model.repo.serialization.TypeOfTree

/**
 * A serialization strategy for binary search tree data structures.
 *
 * @param <E> the type of elements stored in the binary search tree
 * @param serializeValue a function that serializes an element of type E to a SerializableValue
 * @param deserializeValue a function that deserializes a SerializableValue to an element of type E
 */
class BSTreeStrategy<E : Comparable<E>>(
    serializeValue: (E) -> SerializableValue,
    deserializeValue: (SerializableValue) -> E
) : SerializationStrategy<E, BinSearchTreeNode<E>, String>(serializeValue, deserializeValue) {

    /**
     * The type of tree that this strategy serializes and deserializes.
     */
    override val typeOfTree: TypeOfTree = TypeOfTree.BINARY_SEARCH_TREE

    /**
     * Builds a binary search tree node from a serializable node.
     *
     * @param node the serializable node to build from
     * @return the binary search tree node that was built
     */
    override fun buildNode(node: SerializableNode?): BinSearchTreeNode<E>? = node?.let {
        BSNode(
            value = deserializeValue(node.value),
            left = buildNode(node.left),
            right = buildNode(node.right)
        )
    }

    /**
     * Deserializes the metadata for a binary search tree node.
     *
     * @param metadata the metadata to deserialize
     * @return an empty string, since binary search tree nodes do not have metadata
     */
    override fun deserializeMetadata(metadata: Metadata) = ""

    /**
     * Serializes the metadata for a binary search tree node.
     *
     * @param node the binary search tree node to serialize metadata for
     * @return an empty metadata object, since binary search tree nodes do not have metadata
     */
    override fun serializeMetadata(node: BinSearchTreeNode<E>) = Metadata("")
}