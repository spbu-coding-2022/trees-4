import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.*
import app.model.bst.BinarySearchTree
import app.model.bst.node.BinTreeNode
import app.view.*
import app.view.graph.Graph
import app.view.model.AVLTreeEditor
import app.view.model.BSTreeEditor
import app.view.model.RBTreeEditor
import app.view.model.TreeEditor
import java.awt.Dimension
import java.lang.Integer.min

fun main() {
    application {
        Window(
            onCloseRequest = ::exitApplication,
            title = "graph visualizer",
            state = rememberWindowState(
                position = WindowPosition(alignment = Alignment.Center),
                size = DpSize(800.dp, 800.dp),
            ),
        ) {
            window.minimumSize = Dimension(800, 800)
            MaterialTheme(

                colorScheme = MaterialTheme.colorScheme.copy(
                    surface = Color(red = 235, green = 235, blue = 237)
                )
            ) {
                TreeChoiceDialog()
            }
        }
    }
}

@Composable
fun TreeChoiceDialog() {
    var showDialog by remember { mutableStateOf(true) }
    var chosenTreeEditor: TreeEditor<*, *> by remember { mutableStateOf(BSTreeEditor()) }


    if (showDialog) {
        Dialog(
            onCloseRequest = { showDialog = false }
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                androidx.compose.material.Text(text = "Choose tree")
                Spacer(modifier = Modifier.height(16.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    androidx.compose.material.Button(
                        modifier = Modifier.padding(horizontal = 8.dp),
                        onClick = {
                            showDialog = false
                            chosenTreeEditor = BSTreeEditor()
                        }
                    ) {
                        androidx.compose.material.Text(text = "BSTree")
                    }
                    androidx.compose.material.Button(
                        modifier = Modifier.padding(horizontal = 8.dp),
                        onClick = {
                            showDialog = false
                            chosenTreeEditor = AVLTreeEditor()
                        }
                    ) {
                        androidx.compose.material.Text(text = "AVLTree")
                    }
                    androidx.compose.material.Button(
                        modifier = Modifier.padding(horizontal = 8.dp),
                        onClick = {
                            showDialog = false
                            chosenTreeEditor = RBTreeEditor()
                        }
                    ) {
                        androidx.compose.material.Text(text = "RBTree")
                    }
                }
            }
        }
    }

    TreeEditorView(chosenTreeEditor)
}


class DrawableTree(
    root: DrawableNode?
) {
    var root by mutableStateOf(root)
}

@Composable
fun TreeEditorView(editor: TreeEditor<*, *>) {
    val drawableTree by remember {
        mutableStateOf(
            DrawableTree(
                null
            )
        )
    }

    val screenDrag = remember { ScreenDrag(0f, 0f) }
    val screenScale = remember { ScreenScale(1f, Offset(0f, 0f)) }
    var graphViewWidth by remember { mutableStateOf(0) }

    Box(Modifier.fillMaxSize().background(MaterialTheme.colorScheme.surface).onSizeChanged {
        graphViewWidth = it.width
    }) {
        Graph(drawableTree, screenDrag, screenScale)
        GraphControls(
            editor, drawableTree, Modifier.padding(10.dp).align(Alignment.TopEnd).clip(
                RoundedCornerShape(10.dp)
            ).background(Color.LightGray), screenDrag, screenScale, graphViewWidth
        )
    }
}

@Composable
fun <N : BinTreeNode<String, N>, BST : BinarySearchTree<String, N>> GraphControls(
    editor: TreeEditor<N, BST>,
    tree: DrawableTree?,
    modifier: Modifier,
    screenDrag: ScreenDrag,
    screenScale: ScreenScale,
    width: Int,
) {
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
                                screenDrag.x = width / 2 - (defaultNodeSize / 2 + (tree?.root?.x ?: 0.dp)).value
                                screenDrag.y = defaultNodeSize.value
                                screenScale.scale = 1f
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
                        {},
                        Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.tertiary)
                    ) {
                        Text("Json")
                    }
                    Button(
                        {},
                        Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.tertiary)
                    ) {
                        Text("Neo4j")
                    }
                    Button(
                        {},
                        Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.tertiary)
                    ) {
                        Text("Postgresql")
                    }
                }
            }


            HiddenSettings(
                "Load tree from...", hidden = true
            ) {
                Column {
                    Button(
                        {},
                        Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.tertiary)
                    ) {
                        Text("Json")
                    }
                    Button(
                        {},
                        Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.tertiary)
                    ) {
                        Text("Neo4j")
                    }
                    Button(
                        {

                        },
                        Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.tertiary)
                    ) {
                        Text("Postgresql")
                    }
                }
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InputField(action: (String) -> Unit, icon: ImageVector) {
    var text by remember { mutableStateOf("") }

    Row(Modifier.padding(5.dp)) {
        OutlinedTextField(
            value = text,
            onValueChange = { it ->
                text = it.slice(0..min(10, it.length - 1))
            },
            modifier = Modifier.width(150.dp).height(50.dp).padding(end = 5.dp),
            maxLines = 1
        )
        IconButton(onClick = { action(text) }, modifier = Modifier.background(
            color = MaterialTheme.colorScheme.tertiary, shape = RoundedCornerShape(8.dp)
        ).size(50.dp), content = {
            Icon(
                imageVector = icon, contentDescription = null, tint = Color.White
            )
        })
    }
}

@Composable
fun HiddenSettings(text: String, hidden: Boolean = true, content: @Composable () -> Unit) {
    var expanded by remember { mutableStateOf(!hidden) }

    Button(modifier = Modifier.fillMaxWidth(), onClick = { expanded = !expanded }) {
        Text(text)
    }

    Row(verticalAlignment = Alignment.CenterVertically) {
        if (expanded) {
            content()
        }
    }
}