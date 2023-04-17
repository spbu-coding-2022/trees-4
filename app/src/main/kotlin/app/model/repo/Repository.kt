package app.model.repo

import app.model.bst.BinarySearchTree
import app.model.bst.node.BinTreeNode
import app.model.repo.serialization.SerializableNode
import app.model.repo.serialization.strategy.SerializationStrategy

abstract class Repository<E : Comparable<E>,
        Node : BinTreeNode<E, Node>,
        BST : BinarySearchTree<E, Node>>(
    protected val strategy: SerializationStrategy<E, Node, *>
) {
    protected fun Node.toSerializableNode(): SerializableNode {
        return SerializableNode(
            strategy.serializeValue(this.value),
            strategy.serializeMetadata(this),
            left?.toSerializableNode(),
            right?.toSerializableNode()
        )
    }

    abstract fun save(tree: BST)
    abstract fun load(factory: () -> BST): BST
}