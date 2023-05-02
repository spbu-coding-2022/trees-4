package app.view

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import app.view.model.AVLTreeEditor
import app.view.model.BSTreeEditor
import app.view.model.RBTreeEditor
import app.view.model.TreeEditor


@Composable
fun TreeChoiceDialog() {
    var showDialog by remember { mutableStateOf(true) }
    var chosenTreeEditor: TreeEditor<*, *> by remember { mutableStateOf(BSTreeEditor()) }
    if (showDialog) {
        Dialog(
            onCloseRequest = { showDialog = false }
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = "Choose tree")
                Spacer(modifier = Modifier.height(16.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Button(
                        modifier = Modifier.padding(horizontal = 8.dp),
                        onClick = {
                            showDialog = false
                            chosenTreeEditor = BSTreeEditor()
                        }
                    ) {
                        Text(text = "BSTree")
                    }
                    Button(
                        modifier = Modifier.padding(horizontal = 8.dp),
                        onClick = {
                            showDialog = false
                            chosenTreeEditor = AVLTreeEditor()
                        }
                    ) {
                        Text(text = "AVLTree")
                    }
                    Button(
                        modifier = Modifier.padding(horizontal = 8.dp),
                        onClick = {
                            showDialog = false
                            chosenTreeEditor = RBTreeEditor()
                        }
                    ) {
                        Text(text = "RBTree")
                    }
                }
            }
        }
    }

    TreeEditorView(chosenTreeEditor)
}