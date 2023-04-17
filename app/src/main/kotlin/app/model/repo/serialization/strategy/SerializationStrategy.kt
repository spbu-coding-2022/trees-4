package app.model.repo.serialization.strategy

import app.model.bst.node.BinTreeNode
import app.model.repo.serialization.Metadata
import app.model.repo.serialization.SerializableNode
import app.model.repo.serialization.SerializableValue
import app.model.repo.serialization.TypeOfTree


abstract class SerializationStrategy<E : Comparable<E>,
        Node : BinTreeNode<E, Node>, M>(
    val serializeValue: (E) -> SerializableValue,
    val deserializeValue: (SerializableValue) -> E
) {
    abstract val typeOfTree: TypeOfTree
    abstract fun buildNode(node: SerializableNode?): Node?
    abstract fun deserializeMetadata(metadata: Metadata): M
    abstract fun serializeMetadata(node: Node): Metadata
}