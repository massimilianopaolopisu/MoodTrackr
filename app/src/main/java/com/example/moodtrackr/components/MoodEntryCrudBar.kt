package com.example.moodtrackr.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.KeyboardArrowLeft
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
    returnRoute: String? = null
) {
    var showDeleteConfirmDialog by remember { mutableStateOf(false) }
    val viewRoute = "${Routes.ViewMoodEntry}/${selectedDate}"
    val editRoute = "${Routes.EditMoodEntry}/${selectedDate}"

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

                        if(!returnRoute.isNullOrBlank())
                            navController.navigate(returnRoute)
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
        Icon(
            imageVector = Icons.Default.KeyboardArrowLeft,
            contentDescription = "Back",
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier
                .clickable {
                    navController.popBackStack()
                }
                .align(Alignment.CenterVertically)
                .weight(1f)
        )
        Icon(
            imageVector = Icons.Default.ExitToApp,
            contentDescription = "Exit",
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier
                .clickable {
                    android.os.Process.killProcess(android.os.Process.myPid())
                }
                .align(Alignment.CenterVertically)
                .weight(1f)
        )
        Icon(
            imageVector = Icons.Default.Home,
            contentDescription = "Home",
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier
                .clickable {
                    navController.navigate(Routes.Home.toString())
                }
                .align(Alignment.CenterVertically)
                .weight(1f)
        )
        Icon(
            imageVector = Icons.Default.Info,
            contentDescription = "View",
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier
                .clickable {
                    navController.navigate(viewRoute)
                }
                .align(Alignment.CenterVertically)
                .weight(1f)
        )
        Icon(
            imageVector = Icons.Default.Edit,
            contentDescription = "Update",
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier
                .clickable {
                    navController.navigate(editRoute)
                }
                .align(Alignment.CenterVertically)
                .weight(1f)
        )
        Icon(
            imageVector = Icons.Default.Delete,
            contentDescription = "Delete",
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier
                .clickable {
                    showDeleteConfirmDialog = true
                }
                .align(Alignment.CenterVertically)
                .weight(1f)
        )
    }
}