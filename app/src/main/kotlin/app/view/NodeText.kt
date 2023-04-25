package app.view

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle

@Composable
fun NodeText(
    modifier: Modifier = Modifier,
    text: String,
    scaleProvider: () -> Float,
    style: TextStyle = MaterialTheme.typography.bodyMedium,
) {
    Text(
        modifier = modifier,
        text = text,
        color = MaterialTheme.colorScheme.onPrimary,
        style = style.copy(
            fontSize = style.fontSize * scaleProvider(),
            lineHeight = style.lineHeight * scaleProvider()
        )
    )
}
