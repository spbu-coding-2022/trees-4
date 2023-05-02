package app.view

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun HiddenSettings(text: String, hidden: Boolean = true, content: @Composable () -> Unit) {
    var expanded by remember { mutableStateOf(!hidden) }

    Button(modifier = Modifier.fillMaxWidth(), onClick = { expanded = !expanded }) {
        Text(text)
    }

    Row(verticalAlignment = Alignment.CenterVertically) {
        if (expanded) {
            content()
        }
    }
}