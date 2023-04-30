package app.view.graph

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import app.view.ScreenDrag
import app.view.ScreenScale
import app.view.model.Node

@Composable
fun GraphLine(
    modifier: Modifier = Modifier,
    start: Node,
    end: Node,
    nodeSize: Dp,
    screenDrag: ScreenDrag,
    screenScale: ScreenScale
) {
    Canvas(modifier = modifier.fillMaxSize()) {
        drawLine(
            start = Offset(
                ((start.x + nodeSize / 2).toPx() + screenDrag.x) * screenScale.scale + screenScale.posRelXYScale.x,
                ((start.y + nodeSize / 2).toPx() + screenDrag.y) * screenScale.scale + screenScale.posRelXYScale.y,
            ),
            end = Offset(
                ((end.x + nodeSize / 2).toPx() + screenDrag.x) * screenScale.scale + screenScale.posRelXYScale.x,
                ((end.y + nodeSize / 2).toPx() + screenDrag.y) * screenScale.scale + screenScale.posRelXYScale.y,
            ),
            strokeWidth = 2f * screenScale.scale,
            color = Color.Black
        )
    }
}