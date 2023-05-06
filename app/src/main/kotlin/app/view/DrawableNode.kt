package app.view

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp


class DrawableNode(
    val value: String,
    x: Dp = 0.dp,
    y: Dp = 0.dp,
    left: DrawableNode? = null,
    right: DrawableNode? = null,
    val color: Color = Color.DarkGray
) {
    var x by mutableStateOf(x)
    var y by mutableStateOf(y)
    var left by mutableStateOf(left)
    var right by mutableStateOf(right)
}