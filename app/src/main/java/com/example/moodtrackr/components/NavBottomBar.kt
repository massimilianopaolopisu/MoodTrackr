package com.example.moodtrackr.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.moodtrackr.enums.Routes

@Composable
fun NavBottomBar(navController: NavController) {
    var showExitConfirmDialog by remember { mutableStateOf(false) }

    val icons = listOf(
        Triple(Icons.AutoMirrored.Filled.KeyboardArrowLeft, "Back") {
            navController.popBackStack()
        },
        Triple(Icons.Default.Home, "Home") {
            navController.navigate(Routes.Home.toString())
        },
        Triple(Icons.AutoMirrored.Filled.ExitToApp, "Exit") {
            showExitConfirmDialog = true
        }
    )

    if (showExitConfirmDialog) {
        ExitConfirmDialog(
            onDismiss = {
                showExitConfirmDialog = false
            }
        )
    }

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceAround
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