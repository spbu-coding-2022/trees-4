package app.model.repo.serialization.strategy

import app.model.bst.node.AVLNode
import app.model.bst.node.AVLTreeNode
import app.model.repo.serialization.Metadata
import app.model.repo.serialization.SerializableNode
import app.model.repo.serialization.SerializableValue
import app.model.repo.serialization.TypeOfTree

class AVLTreeStrategy<E : Comparable<E>>(
    serializeValue: (E) -> SerializableValue,
    deserializeValue: (SerializableValue) -> E
) : SerializationStrategy<E, AVLTreeNode<E>, Int>(serializeValue, deserializeValue) {
    override val typeOfTree: TypeOfTree = TypeOfTree.AVL_TREE

    override fun buildNode(node: SerializableNode?): AVLTreeNode<E>? = node?.let {
        AVLNode(
            value = deserializeValue(node.value),
            left = buildNode(node.left),
            right = buildNode(node.right),
            height = deserializeMetadata(node.metadata)
        )
    }

    override fun deserializeMetadata(metadata: Metadata) = metadata.value.toInt()

    override fun serializeMetadata(node: AVLTreeNode<E>) = Metadata(node.height.toString())
}