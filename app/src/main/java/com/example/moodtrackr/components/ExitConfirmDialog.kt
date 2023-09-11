package com.example.moodtrackr.components

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun ExitConfirmDialog(
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Confirm Exit") },
        text = { Text("Are you sure you want to exit the app?") },
        confirmButton = {
            Button(
                onClick = {
                    android.os.Process.killProcess(android.os.Process.myPid())
                }
            ) {
                Text("Yes")
            }
        },
        dismissButton = {
            Button(
                onClick = {
                    onDismiss()
                }
            ) {
                Text("No")
            }
        }
    )
}