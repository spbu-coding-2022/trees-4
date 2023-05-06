package app.view.model

import androidx.compose.foundation.layout.*
import androidx.compose.ui.awt.ComposeWindow
import app.model.bst.AVLTree
import app.model.bst.BSTree
import app.model.bst.BinarySearchTree
import app.model.bst.RBTree
import app.model.bst.node.AVLTreeNode
import app.model.bst.node.BinSearchTreeNode
import app.model.bst.node.BinTreeNode
import app.model.bst.node.RedBlackTreeNode
import app.model.repo.JsonRepo
import app.model.repo.serialization.SerializableTree
import app.model.repo.serialization.SerializableValue
import app.model.repo.serialization.TypeOfTree
import app.model.repo.serialization.strategy.AVLTreeStrategy
import app.model.repo.serialization.strategy.BSTreeStrategy
import app.model.repo.serialization.strategy.RBTreeStrategy
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import java.awt.FileDialog
import java.io.File

@Suppress("UNCHECKED_CAST")
fun <N : BinTreeNode<String, N>, BST : BinarySearchTree<String, N>> exportTreeToJson(
    tree: BST,
    typeOfTree: TypeOfTree,
) {
    val file = selectJsonFile(Mode.EXPORT) ?: return
    when (typeOfTree) {
        TypeOfTree.BINARY_SEARCH_TREE -> {
            val strategy = BSTreeStrategy({ SerializableValue(it) }, { it.value })
            JsonRepo(
                strategy,
                file
            ).save(
                file.name, tree as
                        BinarySearchTree<String, BinSearchTreeNode<String>>
            )
        }

        TypeOfTree.RED_BLACK_TREE -> {
            val strategy = RBTreeStrategy({ SerializableValue(it) }, { it.value })
            JsonRepo(
                strategy,
                file
            ).save(
                file.name, tree as
                        BinarySearchTree<String, RedBlackTreeNode<String>>
            )
        }

        TypeOfTree.AVL_TREE -> {
            val strategy = AVLTreeStrategy({ SerializableValue(it) }, { it.value })
            JsonRepo(
                strategy,
                file
            ).save(
                file.name, tree as
                        BinarySearchTree<String, AVLTreeNode<String>>
            )
        }
    }
}

enum class Mode {
    IMPORT,
    EXPORT
}

@Suppress("UNCHECKED_CAST")
fun <N : BinTreeNode<String, N>, BST : BinarySearchTree<String, N>> importTreeFromJson(currentEditorType: TypeOfTree): BST? {
    try {
        val file = selectJsonFile(Mode.IMPORT) ?: return null
        val serializableTree = Json.decodeFromString<SerializableTree>(file.readText())
        if (currentEditorType != serializableTree.typeOfTree) return null //can load only same types of trees
        return when (serializableTree.typeOfTree) {
            TypeOfTree.BINARY_SEARCH_TREE -> {
                val factory = { BSTree<String>() }
                val strategy = BSTreeStrategy({ SerializableValue(it) }, { it.value })
                JsonRepo(strategy, file).loadByVerboseName(file.name, factory) as BST
            }

            TypeOfTree.RED_BLACK_TREE -> {
                val factory = { RBTree<String>() }
                val strategy = RBTreeStrategy({ SerializableValue(it) }, { it.value })
                JsonRepo(strategy, file).loadByVerboseName(file.name, factory) as BST
            }

            TypeOfTree.AVL_TREE -> {
                val factory = { AVLTree<String>() }
                val strategy = AVLTreeStrategy({ SerializableValue(it) }, { it.value })
                JsonRepo(strategy, file).loadByVerboseName(file.name, factory) as BST
            }
        }
    } catch (c: Exception) {
        return null
    }
}


fun selectJsonFile(mode: Mode): File? {
    val fd = if (mode == Mode.IMPORT) {
        FileDialog(ComposeWindow(), "Choose file to import", FileDialog.LOAD)
    } else {
        FileDialog(ComposeWindow(), "Choose file to export", FileDialog.SAVE)
    }
    fd.file = "tree.json"
    fd.isVisible = true
    val fileString = fd.directory + fd.file
    if (fileString != "nullnull") {
        return File(fileString)
    }
    return null
}