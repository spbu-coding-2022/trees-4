package app.model.repo.serialization

import kotlinx.serialization.Serializable

/**
 * An enum representing the type of tree being serialized or deserialized.
 *
 * @property savingName the name of the tree type used for serialization
 */
@Serializable
enum class TypeOfTree(val savingName: String) {
    BINARY_SEARCH_TREE("BINARY_SEARCH_TREE"),
    RED_BLACK_TREE("RED_BLACK_TREE"),
    AVL_TREE("AVL_TREE")
}

/**
 * A serializable node in a tree data structure.
 *
 * @property value the value stored in the node
 * @property metadata the metadata associated with the node
 * @property left the left child of the node
 * @property right the right child of the node
 */
@Serializable
class SerializableNode(
    val value: SerializableValue,
    val metadata: Metadata,
    val left: SerializableNode? = null,
    val right: SerializableNode? = null,
)

/**
 * A serializable tree data structure.
 *
 * @property verboseName the verbose name of the tree
 * @property typeOfTree the type of tree being serialized or deserialized
 * @property root the root node of the tree
 */
@Serializable
class SerializableTree(
    val verboseName: String,
    val typeOfTree: TypeOfTree,
    val root: SerializableNode?
)

/**
 * A metadata value (actually a string) for a serializable node.
 *
 * @property value the value of the metadata
 */
@Serializable
@JvmInline
value class Metadata(val value: String)

/**
 * A serializable value (actually a string) stored in a tree node.
 *
 * @property value the value of the serializable value
 */
@Serializable
@JvmInline
value class SerializableValue(val value: String)