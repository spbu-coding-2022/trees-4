package app.model.repo

import app.model.bst.BinarySearchTree
import app.model.bst.node.BinTreeNode
import app.model.repo.serialization.SerializableTree
import app.model.repo.serialization.strategy.SerializationStrategy
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.io.File

class JsonRepo<E : Comparable<E>,
        Node : BinTreeNode<E, Node>,
        BST : BinarySearchTree<E, Node>>(
    strategy: SerializationStrategy<E, Node, *>,
    private val file: File,
) : Repository<E, Node, BST>(strategy) {
    override fun save(verboseName: String, tree: BST) {
        try {
            val json = Json.encodeToString(tree.toSerializableTree(verboseName))
            file.writeText(json)
        } catch (e: Exception) {
            throw IllegalArgumentException("Impossible to save tree with provided verbose name")
        }
    }

    override fun loadByVerboseName(verboseName: String, factory: () -> BST): BST {
        try {
            val json = file.readText()
            val serializableTree = Json.decodeFromString<SerializableTree>(json)
            return factory().apply { root = strategy.buildNode(serializableTree.root) }
        } catch (e: Exception) {
            throw IllegalArgumentException("Impossible to load tree with provided arguments")
        }
    }

    override fun deleteByVerboseName(verboseName: String) {
        try {
            file.delete()
        } catch (e: Exception) {
            throw IllegalArgumentException("Impossible to delete tree with provided verbose name")
        }
    }
}