import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowPosition
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import app.view.Graph
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
                Box(Modifier.fillMaxSize().background(MaterialTheme.colorScheme.surface)) {
                    Row(
                        modifier = Modifier.padding(20.dp)
                    ) {
                        Surface(
                            modifier = Modifier.fillMaxSize().weight(4f).padding(
                                1.dp
                            ),
                            shape = MaterialTheme.shapes.medium,
                            color = Color.White
                        ) {
                            Graph(tree, 50.dp)
                        }

                    }
                }

            }
        }
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

