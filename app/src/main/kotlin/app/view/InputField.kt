package app.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InputField(action: (String) -> Unit, icon: ImageVector) {
    var text by remember { mutableStateOf("") }

    Row(Modifier.padding(5.dp)) {
        OutlinedTextField(
            value = text,
            onValueChange = { it ->
                text = it.slice(0..Integer.min(10, it.length - 1))
            },
            modifier = Modifier.width(150.dp).height(50.dp).padding(end = 5.dp),
            maxLines = 1
        )
        IconButton(onClick = { action(text) }, modifier = Modifier.background(
            color = MaterialTheme.colorScheme.tertiary, shape = RoundedCornerShape(8.dp)
        ).size(50.dp), content = {
            Icon(
                imageVector = icon, contentDescription = null, tint = Color.White
            )
        })
    }
}