package app.model.repo.serialization.strategy

import app.model.bst.node.BinTreeNode
import app.model.repo.serialization.Metadata
import app.model.repo.serialization.SerializableNode
import app.model.repo.serialization.SerializableValue
import app.model.repo.serialization.TypeOfTree

interface SerializationStrategy<E : Comparable<E>,
        Node : BinTreeNode<E, Node>> {
    val typeOfTree: TypeOfTree
    fun collectMetadata(node: Node): Metadata
    fun collectValue(node: Node): SerializableValue
    fun buildNode(node: SerializableNode): Node
}