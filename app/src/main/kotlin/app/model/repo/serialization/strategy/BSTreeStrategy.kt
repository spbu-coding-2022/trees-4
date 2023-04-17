package app.model.repo.serialization.strategy

import app.model.bst.node.BSNode
import app.model.bst.node.BinSearchTreeNode
import app.model.repo.serialization.Metadata
import app.model.repo.serialization.SerializableNode
import app.model.repo.serialization.SerializableValue
import app.model.repo.serialization.TypeOfTree

class BSTreeStrategy<E : Comparable<E>>(
    serializeValue: (E) -> SerializableValue,
    deserializeValue: (SerializableValue) -> E
) : SerializationStrategy<E, BinSearchTreeNode<E>, String>(serializeValue, deserializeValue) {
    override val typeOfTree: TypeOfTree = TypeOfTree.BINARY_SEARCH_TREE
    override fun buildNode(node: SerializableNode?): BinSearchTreeNode<E>? = node?.let {
        BSNode(
            value = deserializeValue(node.value),
            left = buildNode(node.left),
            right = buildNode(node.right)
        )
    }

    override fun deserializeMetadata(metadata: Metadata) = ""

    override fun serializeMetadata(node: BinSearchTreeNode<E>) = Metadata("")
}