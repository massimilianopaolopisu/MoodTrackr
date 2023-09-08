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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.moodtrackr.enums.Routes

@Composable
fun NavBottomBar(navController: NavController) {
    val icons = listOf(
        Triple(Icons.Default.KeyboardArrowLeft, "Back", "Back"),
        Triple(Icons.Default.ExitToApp, "Exit", "Exit"),
        Triple(Icons.Default.Home, "Home", Routes.Home.toString())
    )

    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        for ((icon, contentDescription, route) in icons) {
            Icon(
                imageVector = icon,
                contentDescription = contentDescription,
                tint = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier
                    .clickable {
                        when (route) {
                            "Exit" -> {
                                android.os.Process.killProcess(android.os.Process.myPid())
                            }

                            "Back" -> {
                                navController.popBackStack()
                            }

                            else -> {
                                navController.navigate(route)
                            }
                        }
                    }
                    .align(Alignment.CenterVertically)
                    .weight(1f)
            )
        }
    }
}