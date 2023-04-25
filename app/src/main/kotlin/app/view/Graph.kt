package app.view

import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.material3.ButtonDefaults.textButtonColors
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.pointer.PointerEventType
import androidx.compose.ui.input.pointer.onPointerEvent
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import app.view.model.Node
import kotlin.math.max
import kotlin.math.min


@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun Graph(root: Node, nodeSize: Dp) {
    val screenDrag = remember { ScreenDrag(0f, 0f) }
    val screenScale = remember { ScreenScale(1f, Offset(0f, 0f)) }

    Box(modifier = Modifier.fillMaxSize().onPointerEvent(PointerEventType.Scroll) {
        screenDrag.x -= it.changes.first().scrollDelta.x * 50 // let's scroll faster brbrbr

        val scrollPos = it.changes.first().position
        val prevScale = screenScale.scale
        screenScale.scale = min(
            max(
                screenScale.scale - it.changes.first().scrollDelta.y / 15, // let's zoom slower
                0.5f // min scale
            ), 2f // max scale
        )

        val relScale = screenScale.scale / prevScale
        screenScale.posRelXYScale = Offset(
            screenScale.posRelXYScale.x * relScale + scrollPos.x * (1 - relScale),
            screenScale.posRelXYScale.y * relScale + scrollPos.y * (1 - relScale)
        )
    }.pointerInput(Unit) {
        detectDragGestures { change, dragAmount ->
            change.consume()
            screenDrag.x += dragAmount.x / screenScale.scale
            screenDrag.y += dragAmount.y / screenScale.scale
        }
    }) {
        Tree(
            rootNode = root, screenDrag = screenDrag, screenScale = screenScale, nodeSize = nodeSize
        )
        TextButton(
            modifier = Modifier.align(Alignment.TopEnd).zIndex(3f).padding(5.dp), onClick = {
                screenScale.scale = 1f
                screenScale.posRelXYScale = Offset(0f, 0f)
                screenDrag.x = 0f
                screenDrag.y = 0f
            }, colors = textButtonColors(
                containerColor = MaterialTheme.colorScheme.surface
            )
        ) { Text("Reset view", color = MaterialTheme.colorScheme.primary) }
    }
}