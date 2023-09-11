package com.example.moodtrackr.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.navigation.NavController
import com.example.moodtrackr.R
import com.example.moodtrackr.enums.Routes
import java.time.LocalDate

@Composable
fun MainBottomBar(
    navController: NavController,
    date: LocalDate = LocalDate.now()
){
    val editRoute = "${Routes.EditMoodEntry}/$date"
    var showExitConfirmDialog by remember { mutableStateOf(false) }

    val icons = listOf(
        Triple(
            Icons.Default.Edit,
            "Edit",
            editRoute
        ),
        Triple(
            Icons.Default.DateRange,
            "Mood Entries History",
            Routes.MoodEntriesHistory.toString()
        ),
        Triple(
            ImageVector.vectorResource(id = R.drawable.ic_graph),
            "Graphs",
            Routes.Graphs.toString()
        ),
        Triple(
            ImageVector.vectorResource(id = R.drawable.ic_chart),
            "Statistics",
            Routes.Statistics.toString()
        ),
        Triple(
            Icons.Default.Settings,
            "Settings",
            Routes.Settings.toString()
        ),
        Triple(
            Icons.AutoMirrored.Filled.ExitToApp,
            "Exit",
            null
        )
    )

    if (showExitConfirmDialog) {
        ExitConfirmDialog(
            onDismiss = {
                showExitConfirmDialog = false
            }
        )
    }

    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        for ((icon, contentDescription, route) in icons) {
            val imageVector = icon as? ImageVector

            if (imageVector != null) {
                Icon(
                    imageVector = imageVector,
                    contentDescription = contentDescription,
                    tint = MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier
                        .clickable {
                            route?.let {
                                navController.navigate(it)
                            } ?: run {
                                showExitConfirmDialog = true
                            }
                        }
                        .align(Alignment.CenterVertically)
                        .weight(1f)
                )
            }
        }
    }
}