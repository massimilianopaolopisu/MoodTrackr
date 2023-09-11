package com.example.moodtrackr.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.moodtrackr.enums.Routes
import com.example.moodtrackr.repositories.interfaces.IMoodEntriesRepository
import java.time.LocalDate

@Composable
fun MoodEntryCrudBar(
    navController: NavController,
    moodEntriesRepository: IMoodEntriesRepository,
    selectedDate: LocalDate,
    returnRoute: String = ""
) {
    val viewRoute = "${Routes.ViewMoodEntry}/$selectedDate"
    val editRoute = "${Routes.EditMoodEntry}/$selectedDate"
    var showDeleteConfirmDialog by remember { mutableStateOf(false) }

    val icons = listOf(
        Triple(Icons.AutoMirrored.Filled.KeyboardArrowLeft, "Back") {
            navController.popBackStack()
        },
        Triple(Icons.Default.Home, "Home") {
            navController.navigate(Routes.Home.toString())
        },
        Triple(Icons.Default.Info, "View") {
            navController.navigate(viewRoute)
        },
        Triple(Icons.Default.Edit, "Update") {
            navController.navigate(editRoute)
        },
        Triple(Icons.Default.Delete, "Delete") {
            showDeleteConfirmDialog = true
        },
        Triple(Icons.AutoMirrored.Filled.ExitToApp, "Exit") {
            android.os.Process.killProcess(android.os.Process.myPid())
        }
    )

    if (showDeleteConfirmDialog) {
        AlertDialog(
            onDismissRequest = {
                showDeleteConfirmDialog = false
            },
            title = { Text("Confirm") },
            text = { Text("Are you sure to remove $selectedDate Mood Entry?") },
            confirmButton = {
                Button(
                    onClick = {
                        moodEntriesRepository.deleteMoodEntry(selectedDate)
                        showDeleteConfirmDialog = false

                        if (returnRoute.isNotBlank()) {
                            navController.navigate(returnRoute)
                        }
                    }
                ) {
                    Text("Yes")
                }
            },
            dismissButton = {
                Button(
                    onClick = {
                        showDeleteConfirmDialog = false
                    }
                ) {
                    Text("No")
                }
            }
        )
    }

    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        for ((icon, contentDescription, action) in icons) {
            Icon(
                imageVector = icon,
                contentDescription = contentDescription,
                tint = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier
                    .clickable {
                        action()
                    }
                    .align(Alignment.CenterVertically)
                    .weight(1f)
            )
        }
    }
}