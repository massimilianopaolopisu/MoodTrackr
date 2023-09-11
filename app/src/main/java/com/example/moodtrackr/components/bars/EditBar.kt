package com.example.moodtrackr.components.bars

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.moodtrackr.components.dialogs.DeleteConfirmDialog
import com.example.moodtrackr.enums.Routes
import com.example.moodtrackr.repositories.interfaces.IMoodEntriesRepository
import java.time.LocalDate

@Composable
fun EditBar(
    navController: NavController,
    moodEntriesRepository: IMoodEntriesRepository,
    localDate: LocalDate,
){
    val editRoute = "${Routes.EditMoodEntry}/$localDate"
    var showDeleteConfirmDialog by remember { mutableStateOf(false) }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 20.dp),
        horizontalArrangement = Arrangement.End,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = Icons.Default.Edit,
            contentDescription = "Edit",
            tint = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier
                .clickable {
                    navController.navigate(editRoute)
                }
                .padding(horizontal = 10.dp)
        )

        Icon(
            imageVector = Icons.Default.Delete,
            contentDescription = "Delete",
            tint = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier
                .clickable {
                    showDeleteConfirmDialog = true
                }
        )

        if (showDeleteConfirmDialog) {
            DeleteConfirmDialog(
                onDismiss = { showDeleteConfirmDialog = false },
                moodEntriesRepository = moodEntriesRepository,
                selectedDate = localDate,
                onConfirm = { navController.navigate(Routes.Home.toString()) })
        }
    }
}