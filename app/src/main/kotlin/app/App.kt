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
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.*
import app.model.bst.BinarySearchTree
import app.model.bst.node.BinTreeNode
import app.view.*
import app.view.graph.Graph
import app.view.model.AVLTreeEditor
import app.view.model.TreeEditor
import java.awt.Dimension

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
                TreeEditorView(
                    AVLTreeEditor()
                )
            }
        }
    }
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

    Box(Modifier.fillMaxSize().background(MaterialTheme.colorScheme.surface)) {
        Graph(drawableTree, screenDrag, screenScale)
        GraphControls(
            editor, drawableTree, Modifier.padding(10.dp).align(Alignment.TopEnd).clip(
                RoundedCornerShape(10.dp)
            ).background(Color.LightGray), screenDrag, screenScale
        )
    }
}

@Composable
fun <N : BinTreeNode<String, N>, BST : BinarySearchTree<String, N>> GraphControls(
    editor: TreeEditor<N, BST>,
    tree: DrawableTree?,
    modifier: Modifier,
    screenDrag: ScreenDrag,
    screenScale: ScreenScale
) {
    Box(modifier) {
        Column(Modifier.padding(10.dp).width(210.dp).verticalScroll(rememberScrollState())) {
            HiddenSettings(
                "Tree operations", hidden = false
            ) {
                Column {
                    InputField({ tree?.root = editor.addToTree(it) }, Icons.Default.Add)
                    InputField({ tree?.root = editor.removeFromTree(it) }, Icons.Default.Remove)
                    InputField({ tree?.root = editor.findNodeInTree(it) ?: tree?.root }, Icons.Default.Search)
                    Row {
                        Button(
                            {

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
            onValueChange = { text = it },
            modifier = Modifier.width(150.dp).height(50.dp).padding(end = 5.dp),
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