package app.model.repo

import app.model.bst.BinarySearchTree
import app.model.bst.node.BinTreeNode
import app.model.repo.serialization.SerializableTree
import app.model.repo.serialization.strategy.SerializationStrategy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import java.io.File

class JsonRepo<E : Comparable<E>,
        Node : BinTreeNode<E, Node>,
        BST : BinarySearchTree<E, Node>>(
    strategy: SerializationStrategy<E, Node, *>
) : Repository<E, Node, BST>(strategy) {
    private var builder: GsonBuilder = GsonBuilder()
    private var gson: Gson = builder.create()
    private val sC = File.separatorChar
    override fun save(verboseName: String, tree: BST) {
        try {
            val json = gson.toJson(tree.toSerializableTree(verboseName))
            val file = File("app${sC}src${sC}main${sC}resources${sC}$verboseName.json")
            file.writeText(json)
        } catch (e: Exception) {
            throw IllegalArgumentException("Impossible to save tree with provided verbose name")
        }
    }

    override fun loadByVerboseName(verboseName: String, factory: () -> BST): BST {
        try {
            val file = File("app${sC}src${sC}main${sC}resources${sC}$verboseName.json")
            val json = file.readText()
            val serializableTree = gson.fromJson(json, SerializableTree::class.java)
            return factory().apply { root = strategy.buildNode(serializableTree.root) }
        } catch (e: Exception) {
            throw IllegalArgumentException("Impossible to load tree with provided arguments")
        }
    }

    override fun deleteByVerboseName(verboseName: String) {
        try {
            val file = File("app${sC}src${sC}main${sC}resources${sC}$verboseName.json")
            file.delete()
        } catch (e: Exception) {
            throw IllegalArgumentException("Impossible to delete tree with provided verbose name")
        }
    }
}