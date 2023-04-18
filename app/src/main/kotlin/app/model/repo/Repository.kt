package app.model.repo

import app.model.bst.BinarySearchTree
import app.model.bst.node.BinTreeNode
import app.model.repo.serialization.SerializableNode
import app.model.repo.serialization.SerializableTree
import app.model.repo.serialization.strategy.SerializationStrategy

/**
 * An abstract class representing a repository for binary search trees.
 * @param E the type of element stored in the tree
 * @param Node the type of node used in the tree
 * @param BST the type of binary search tree stored in the repository
 * @property strategy the serialization strategy used to serialize and deserialize the tree.
 * @constructor creates a new Repository with the given serialization strategy.
 */
abstract class Repository<E : Comparable<E>,
        Node : BinTreeNode<E, Node>,
        BST : BinarySearchTree<E, Node>>(
    protected val strategy: SerializationStrategy<E, Node, *>
) {
    /**
     * Converts a node to a serializable node using the serialization strategy.
     * @param Node the node to convert.
     * @return the serializable node.
     */
    protected fun Node.toSerializableNode(): SerializableNode {
        return SerializableNode(
            strategy.serializeValue(this.value),
            strategy.serializeMetadata(this),
            left?.toSerializableNode(),
            right?.toSerializableNode()
        )
    }

    /**
     * Converts a binary search tree to a serializable tree using the serialization strategy.
     * @param verboseName the verbose name of the tree.
     * @return the serializable tree.
     */
    protected fun BST.toSerializableTree(verboseName: String): SerializableTree {
        return SerializableTree(
            verboseName = verboseName,
            typeOfTree = strategy.typeOfTree,
            root = this.root?.toSerializableNode()
        )
    }

    /**
     * Saves a binary search tree to the repository.
     * @param verboseName the verbose name of the tree.
     * @param tree the tree to save.
     */
    abstract fun save(verboseName: String, tree: BST)

    /**
     * Loads a binary search tree from the repository by its verbose name.
     * @param verboseName the verbose name of the tree.
     * @param factory a factory function to create a new instance of the tree.
     * @return the loaded tree.
     */
    abstract fun loadByVerboseName(verboseName: String, factory: () -> BST): BST

    /**
     * Deletes a binary search tree from the repository by its verbose name.
     * @param verboseName the verbose name of the tree to delete.
     */
    abstract fun deleteByVerboseName(verboseName: String)
}