package app.view

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.geometry.Offset

class ScreenDrag(
    x: Float,
    y: Float
) {
    var x by mutableStateOf(x)
    var y by mutableStateOf(y)
}

class ScreenScale(
    scale: Float,
    posRelScale: Offset
) {
    var scale by mutableStateOf(scale)
    var posRelXYScale by mutableStateOf(posRelScale)
}