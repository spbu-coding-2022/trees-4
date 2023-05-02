package app.view.graph

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import app.view.DrawableNode
import app.view.ScreenDrag
import app.view.ScreenScale
import app.view.defaultNodeSize

@Composable
fun GraphLine(
    modifier: Modifier = Modifier,
    start: DrawableNode,
    end: DrawableNode,
    screenDrag: ScreenDrag,
    screenScale: ScreenScale
) {
    Canvas(modifier = modifier.fillMaxSize()) {
        drawLine(
            start = Offset(
                ((start.x + defaultNodeSize / 2).toPx() + screenDrag.x) * screenScale.scale + screenScale.posRelXYScale.x,
                ((start.y + defaultNodeSize / 2).toPx() + screenDrag.y) * screenScale.scale + screenScale.posRelXYScale.y,
            ),
            end = Offset(
                ((end.x + defaultNodeSize / 2).toPx() + screenDrag.x) * screenScale.scale + screenScale.posRelXYScale.x,
                ((end.y + defaultNodeSize / 2).toPx() + screenDrag.y) * screenScale.scale + screenScale.posRelXYScale.y,
            ),
            strokeWidth = 2f * screenScale.scale,
            color = Color.Black
        )
    }
}