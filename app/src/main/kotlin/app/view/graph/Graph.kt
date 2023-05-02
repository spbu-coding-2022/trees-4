package app.view.graph

import DrawableTree
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.pointer.PointerEventType
import androidx.compose.ui.input.pointer.onPointerEvent
import androidx.compose.ui.input.pointer.pointerInput
import app.view.ScreenDrag
import app.view.ScreenScale
import kotlin.math.max
import kotlin.math.min


@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun Graph(root: DrawableTree?, screenDrag: ScreenDrag, screenScale: ScreenScale) {


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
            rootNode = root?.root, screenDrag = screenDrag, screenScale = screenScale
        )
    }
}