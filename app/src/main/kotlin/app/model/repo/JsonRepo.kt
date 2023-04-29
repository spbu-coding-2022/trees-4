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
    strategy: SerializationStrategy<E, Node, *>
) : Repository<E, Node, BST>(strategy) {
    private val savingDirectory: String
        get() {
            val sC = File.separatorChar
            return "app${sC}src${sC}main${sC}resources${sC}"
        }

    override fun save(verboseName: String, tree: BST) {
        try {
            val json = Json.encodeToString(tree.toSerializableTree(verboseName))
            val file = File("$savingDirectory$verboseName.json")
            file.writeText(json)
        } catch (e: Exception) {
            throw IllegalArgumentException("Impossible to save tree with provided verbose name")
        }
    }

    override fun loadByVerboseName(verboseName: String, factory: () -> BST): BST {
        try {
            val file = File("$savingDirectory$verboseName.json")
            val json = file.readText()
            val serializableTree = Json.decodeFromString<SerializableTree>(json)
            return factory().apply { root = strategy.buildNode(serializableTree.root) }
        } catch (e: Exception) {
            throw IllegalArgumentException("Impossible to load tree with provided arguments")
        }
    }

    override fun deleteByVerboseName(verboseName: String) {
        try {
            val file = File("$savingDirectory$verboseName.json")
            file.delete()
        } catch (e: Exception) {
            throw IllegalArgumentException("Impossible to delete tree with provided verbose name")
        }
    }
}