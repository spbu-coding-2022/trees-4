package app.view.graph

import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.Dp
import app.view.ScreenDrag
import app.view.ScreenScale
import app.view.model.Node


@Composable
fun Tree(
    rootNode: Node?,
    nodeSize: Dp,
    screenDrag: ScreenDrag,
    screenScale: ScreenScale,
) {
    val stack = mutableListOf<Node?>()
    stack.add(rootNode)
    var currentParent: Node? = null

    while (stack.isNotEmpty()) {
        val currentNode = stack.removeLast()

        currentNode?.let { node ->
            currentParent?.let { parent ->
                GraphLine(
                    start = parent,
                    end = node,
                    nodeSize = nodeSize,
                    screenDrag = screenDrag,
                    screenScale = screenScale
                )
            }

            stack.add(node.right)
            stack.add(node.left)
            currentParent = node

            GraphNode(
                node = node,
                screenDrag = screenDrag,
                screenScale = screenScale,
                nodeSize = nodeSize
            )
        }
    }
}