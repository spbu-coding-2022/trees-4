package app.model.repo.serialization.strategy

import app.model.bst.node.BinTreeNode
import app.model.repo.serialization.Metadata
import app.model.repo.serialization.SerializableNode
import app.model.repo.serialization.SerializableValue
import app.model.repo.serialization.TypeOfTree

/**
 * An abstract class representing a strategy for serializing and deserializing binary tree data structures.
 *
 * @param E the type of element stored in the binary tree
 * @param Node the type of node used in the binary tree
 * @param M the type of metadata associated with each node in the binary tree
 * @property serializeValue a function that serializes an element of type E into a [SerializableValue]
 * @property deserializeValue a function that deserializes a [SerializableValue] into an element of type E
 * @property typeOfTree an abstract property representing the type of binary tree being serialized or deserialized
 */
abstract class SerializationStrategy<E : Comparable<E>, Node : BinTreeNode<E, Node>, M>(
    val serializeValue: (E) -> SerializableValue,
    val deserializeValue: (SerializableValue) -> E
) {
    abstract val typeOfTree: TypeOfTree

    /**
     * Builds a node of type [Node] from a [SerializableNode].
     *
     * @param node the [SerializableNode] to build the [Node] from
     * @return the [Node] built from the [SerializableNode], or null if the [SerializableNode] is null
     */
    abstract fun buildNode(node: SerializableNode?): Node?

    /**
     * Deserializes metadata of type [M] from a [Metadata] object.
     *
     * @param metadata the [Metadata] object to deserialize the metadata from
     * @return the deserialized metadata of type [M]
     */
    abstract fun deserializeMetadata(metadata: Metadata): M

    /**
     * Serializes metadata of type [M] into a [Metadata] object.
     *
     * @param node the node to serialize the metadata from
     * @return the serialized metadata as a [Metadata] object
     */
    abstract fun serializeMetadata(node: Node): Metadata
}