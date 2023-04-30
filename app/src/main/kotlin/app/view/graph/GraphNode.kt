package app.view.graph

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.Measurable
import androidx.compose.ui.layout.layout
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import app.view.NodeText
import app.view.ScreenDrag
import app.view.ScreenScale
import app.view.model.Node
import kotlin.math.roundToInt

@Composable
fun GraphNode(
    modifier: Modifier = Modifier,
    node: Node,
    nodeSize: Dp = 50.dp,
    screenDrag: ScreenDrag,
    screenScale: ScreenScale
) {
    Box(modifier = modifier
        .zIndex(2f)
        .layout { measurable: Measurable, _: Constraints ->
            val placeable = measurable.measure(
                Constraints.fixed(
                    (nodeSize * screenScale.scale).roundToPx(),
                    (nodeSize * screenScale.scale).roundToPx()
                )
            )

            layout(placeable.width, placeable.height) {
                placeable.placeRelative(
                    ((node.x.toPx() + screenDrag.x) * screenScale.scale + screenScale.posRelXYScale.x).roundToInt(),
                    ((node.y.toPx() + screenDrag.y) * screenScale.scale + screenScale.posRelXYScale.y).roundToInt(),
                )
            }
        }
        .background(
            color = MaterialTheme.colorScheme.primary,
            shape = CircleShape
        )

        .pointerInput(node.value, screenScale) {
            detectDragGestures { change, dragAmount ->
                change.consume()
                node.x += (dragAmount.x / screenScale.scale).toDp()
                node.y += (dragAmount.y / screenScale.scale).toDp()
            }
        }
    ) {
        NodeText(
            modifier = Modifier.align(Alignment.Center),
            text = node.value,
            scaleProvider = { screenScale.scale }
        )
    }
}