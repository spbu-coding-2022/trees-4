package app.view.graph

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import app.model.bst.BinarySearchTree
import app.model.bst.node.BinTreeNode
import app.view.*
import app.view.model.TreeEditor
import app.view.model.exportTreeToJson
import app.view.model.importTreeFromJson


@Composable
fun <N : BinTreeNode<String, N>, BST : BinarySearchTree<String, N>> GraphControls(
    editor: TreeEditor<N, BST>,
    tree: DrawableTree?,
    modifier: Modifier,
    screenDrag: ScreenDrag,
    screenScale: ScreenScale,
) {

    val currentDensity = LocalDensity.current
    Box(modifier) {
        Column(Modifier.padding(10.dp).width(210.dp).verticalScroll(rememberScrollState())) {
            HiddenSettings(
                "Tree operations", hidden = false
            ) {
                Column {
                    InputField({ tree?.root = editor.addToTree(it) }, Icons.Default.Add)
                    InputField({ tree?.root = editor.removeFromTree(it) }, Icons.Default.Remove)
                    InputField({
                        tree?.root = editor.findNodeInTree(it)
                        editor.resetCoordinates(tree?.root)
                    }, Icons.Default.Search)
                    Row {
                        Button(
                            {
                                currentDensity.run {
                                    screenScale.scale = 1f
                                    screenDrag.x = 0f
                                    screenDrag.y = 0f
                                }

                            },
                            Modifier.weight(1f).fillMaxWidth(),
                            colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.tertiary)
                        ) {
                            Text("Reset view")
                        }
                        Button(
                            {
                                tree?.root = editor.toDrawableNode(editor.tree.root)
                                editor.resetCoordinates(tree?.root)
                            },
                            Modifier.weight(1f).fillMaxWidth(),
                            colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.tertiary)
                        ) {
                            Text("Reset tree")
                        }
                    }
                }
            }

            HiddenSettings(
                "Save tree to...", hidden = true
            ) {
                Column {
                    Button(
                        {
                            exportTreeToJson(editor.tree, editor.typeOfTree)
                        },
                        Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.tertiary)
                    ) {
                        Text("Json")
                    }
                }
            }

            HiddenSettings(
                "Load tree from...", hidden = true
            ) {
                Column {
                    Button(
                        {
                            val t = importTreeFromJson<N, BST>(editor.typeOfTree) ?: return@Button
                            editor.tree = t
                            tree?.root = editor.toDrawableNode(editor.tree.root)
                            editor.resetCoordinates(tree?.root)
                        },
                        Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.tertiary)
                    ) {
                        Text("Json")
                    }
                }
            }
        }
    }
}