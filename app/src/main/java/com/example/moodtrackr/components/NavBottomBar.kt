package com.example.moodtrackr.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.moodtrackr.enums.Routes

@Composable
fun NavBottomBar(navController: NavController) {
    val icons = listOf(
        Triple(Icons.Default.KeyboardArrowLeft, "Back", null),
        Triple(Icons.Default.ExitToApp, "Exit", null),
        Triple(Icons.Default.Home, "Home", Routes.Home.toString())
    )

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        for ((icon, contentDescription, route) in icons) {
            Icon(
                imageVector = icon,
                contentDescription = contentDescription,
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier
                    .clickable {
                        route?.let {
                            navController.navigate(it)
                        } ?: run {
                            if (contentDescription == "Exit") {
                                android.os.Process.killProcess(android.os.Process.myPid())
                            }
                        }
                    }
            )
        }
    }
}