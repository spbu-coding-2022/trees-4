package app.model.repo.serialization

import kotlinx.serialization.Serializable

@Serializable
enum class TypeOfTree(val savingName: String) {
    BINARY_SEARCH_TREE("BINARY_SEARCH_TREE"),
    RED_BLACK_TREE("RED_BLACK_TREE"),
    AVL_TREE("AVL_TREE")
}

@Serializable
class SerializableNode(
    val value: SerializableValue,
    val metadata: Metadata,
    val left: SerializableNode? = null,
    val right: SerializableNode? = null,
)

@Serializable
class SerializableTree(
    val verboseName: String,
    val typeOfTree: TypeOfTree,
    val root: SerializableNode?
)

@Serializable
@JvmInline
value class Metadata(val value: String)

@Serializable
@JvmInline
value class SerializableValue(val value: String)