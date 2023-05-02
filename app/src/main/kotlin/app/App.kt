import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowPosition
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import app.view.TreeChoiceDialog
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
                TreeChoiceDialog()
            }
        }
    }
}