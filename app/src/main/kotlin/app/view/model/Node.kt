package app.view.model

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.unit.Dp


class Node(
    val value: String,
    x: Dp,
    y: Dp,
    left: Node? = null,
    right: Node? = null
) {
    var x by mutableStateOf(x)
    var y by mutableStateOf(y)
    var left by mutableStateOf(left)
    var right by mutableStateOf(right)

    override fun toString(): String = value
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Node) return false

        return hashCode() == other.hashCode()
    }

    override fun hashCode(): Int {
        var result = value.hashCode()
        result = 31 * result + (left?.hashCode() ?: 0)
        result = 31 * result + (right?.hashCode() ?: 0)
        return result
    }
}