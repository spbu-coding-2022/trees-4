package app.view

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

class DrawableTree(
    root: DrawableNode?
) {
    var root by mutableStateOf(root)
}