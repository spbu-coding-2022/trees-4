package app.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import app.view.graph.Graph
import app.view.graph.GraphControls
import app.view.model.TreeEditor


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