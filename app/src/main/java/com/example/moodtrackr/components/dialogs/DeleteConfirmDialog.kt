package com.example.moodtrackr.components.dialogs

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.example.moodtrackr.repositories.interfaces.IMoodEntriesRepository
import java.time.LocalDate

@Composable
fun DeleteConfirmDialog(
    onDismiss: () -> Unit,
    onConfirm: () -> Unit,
    moodEntriesRepository: IMoodEntriesRepository,
    selectedDate: LocalDate
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Confirm") },
        text = { Text("Are you sure to remove $selectedDate Mood Entry?") },
        confirmButton = {
            Button(
                onClick = {
                    moodEntriesRepository.deleteMoodEntry(selectedDate)
                    onDismiss()
                    onConfirm()
                }
            ) {
                Text("Yes")
            }
        },
        dismissButton = {
            Button(
                onClick = onDismiss
            ) {
                Text("No")
            }
        }
    )
}