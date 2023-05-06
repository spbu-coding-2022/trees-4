package app.model.repo.serialization.strategy

import app.model.bst.node.AVLNode
import app.model.bst.node.AVLTreeNode
import app.model.repo.serialization.Metadata
import app.model.repo.serialization.SerializableNode
import app.model.repo.serialization.SerializableValue
import app.model.repo.serialization.TypeOfTree

/**
 * A serialization strategy for AVL tree data structures.
 *
 * @param <E> the type of elements stored in the AVL tree
 * @param serializeValue a function that serializes an element of type E to a SerializableValue
 * @param deserializeValue a function that deserializes a SerializableValue to an element of type E
 */
class AVLTreeStrategy<E : Comparable<E>>(
    serializeValue: (E) -> SerializableValue,
    deserializeValue: (SerializableValue) -> E
) : SerializationStrategy<E, AVLTreeNode<E>, Int>(serializeValue, deserializeValue) {

    /**
     * The type of tree that this strategy serializes and deserializes.
     */
    override val typeOfTree: TypeOfTree = TypeOfTree.AVL_TREE

    /**
     * Builds an AVL tree node from a serializable node.
     *
     * @param node the serializable node to build from
     * @return the AVL tree node that was built
     */
    override fun buildNode(node: SerializableNode?): AVLTreeNode<E>? = node?.let {
        AVLNode(
            value = deserializeValue(node.value),
            left = buildNode(node.left),
            right = buildNode(node.right),
            height = deserializeMetadata(node.metadata)
        )
    }

    /**
     * Deserializes the metadata for an AVL tree node.
     *
     * @param metadata the metadata to deserialize
     * @return the height of the AVL tree node
     */
    override fun deserializeMetadata(metadata: Metadata) = metadata.value.toInt()

    /**
     * Serializes the metadata for an AVL tree node.
     *
     * @param node the AVL tree node to serialize metadata for
     * @return a metadata object containing the height of the AVL tree node
     */
    override fun serializeMetadata(node: AVLTreeNode<E>) = Metadata(node.height.toString())
}