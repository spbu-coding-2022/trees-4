package app.view.graph

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle

@Composable
fun GraphNodeText(
    modifier: Modifier = Modifier,
    text: String,
    scaleProvider: () -> Float,
    style: TextStyle = MaterialTheme.typography.bodyMedium,
) {
    Text(
        modifier = modifier,
        text = if (text.length > 4) text.substring(0, 4) + ".." else text,
        color = MaterialTheme.colorScheme.onPrimary,
        style = style.copy(
            fontSize = style.fontSize * scaleProvider(),
            lineHeight = style.lineHeight * scaleProvider()
        )
    )
}
