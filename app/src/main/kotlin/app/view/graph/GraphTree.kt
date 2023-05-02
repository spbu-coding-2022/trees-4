package app.view.graph

import androidx.compose.runtime.Composable
import app.view.DrawableNode
import app.view.ScreenDrag
import app.view.ScreenScale


@Composable
fun Tree(
    rootNode: DrawableNode?,
    screenDrag: ScreenDrag,
    screenScale: ScreenScale,
    parent: DrawableNode? = null
) {
    rootNode?.let {
        parent?.let { parent ->
            GraphLine(
                start = parent,
                end = it,
                screenDrag = screenDrag,
                screenScale = screenScale
            )
        }

        Tree(rootNode.left, screenDrag, screenScale, it)
        Tree(rootNode.right, screenDrag, screenScale, it)

        GraphNode(node = rootNode, screenDrag = screenDrag, screenScale = screenScale)
    }
}