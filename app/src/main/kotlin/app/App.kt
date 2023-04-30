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
import app.view.ScreenDrag
import app.view.ScreenScale
import app.view.graph.Graph
import app.view.model.Node
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
                val tree = remember { getTree() }
                val screenDrag = remember { ScreenDrag(0f, 0f) }
                val screenScale = remember { ScreenScale(1f, Offset(0f, 0f)) }


                Box(Modifier.fillMaxSize().background(MaterialTheme.colorScheme.surface)) {
                    Graph(tree, 50.dp, screenDrag, screenScale)
                    GraphControls(
                        Modifier.padding(10.dp).align(Alignment.TopEnd).clip(
                            RoundedCornerShape(10.dp)
                        ).background(Color.LightGray), screenDrag, screenScale
                    )
                }
            }
        }
    }
}


@Composable
fun GraphControls(modifier: Modifier, screenDrag: ScreenDrag, screenScale: ScreenScale) {
    Box(modifier) {
        Column(Modifier.padding(10.dp).width(210.dp).verticalScroll(rememberScrollState())) {
            HiddenSettings(
                "Tree operations",
                hidden = false
            ) {
                Column {
                    InputField({ print(it) }, Icons.Default.Add)
                    InputField({ print(it) }, Icons.Default.Remove)
                    InputField({ print(it) }, Icons.Default.Search)
                }
            }

            HiddenSettings(
                "Save tree to...",
                hidden = true
            ) {
                Column {
                    Button(
                        {}, Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.tertiary)
                    ) {
                        Text("Json")
                    }
                    Button(
                        {}, Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.tertiary)
                    ) {
                        Text("Neo4j")
                    }
                    Button(
                        {}, Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.tertiary)
                    ) {
                        Text("Postgresql")
                    }
                }
            }


            HiddenSettings(
                "Load tree from...",
                hidden = true
            ) {
                Column {
                    Button(
                        {}, Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.tertiary)
                    ) {
                        Text("Json")
                    }
                    Button(
                        {}, Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.tertiary)
                    ) {
                        Text("Neo4j")
                    }
                    Button(
                        {}, Modifier.fillMaxWidth(),
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
        IconButton(
            onClick = { action(text) },
            modifier = Modifier.background(
                color = MaterialTheme.colorScheme.tertiary,
                shape = RoundedCornerShape(8.dp)
            ).size(50.dp),
            content = {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = Color.White
                )
            }
        )
    }
}

fun getTree(): Node {
    var x = 10.dp
    val root = Node("abc", x, 150.dp)


    var cur = root
    var side = "left"
    for (i in 1..100) {
        x += 100.dp
        when (side) {
            "left" -> {
                cur.left = Node("$i", x, 150.dp)
                cur = cur.left!!
                side = "right"
            }

            "right" -> {
                cur.right = Node("$i", x, 150.dp)
                cur = cur.right!!
                side = "left"
            }
        }
    }

    return root
}

@Composable
fun HiddenSettings(text: String, hidden: Boolean = true, content: @Composable () -> Unit) {
    var expanded by remember { mutableStateOf(!hidden) }

    Button(
        modifier = Modifier.fillMaxWidth(),
        onClick = { expanded = !expanded }
    ) {
        Text(text)
    }

    Row(verticalAlignment = Alignment.CenterVertically) {
        if (expanded) {
            content()

        }
    }
}