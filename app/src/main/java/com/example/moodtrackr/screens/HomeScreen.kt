package com.example.moodtrackr.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.moodtrackr.enums.Routes
import com.example.moodtrackr.repositories.IProfilePreferencesRepository

@Composable
fun HomeScreen(
    navController: NavController,
    profilePreferencesRepository: IProfilePreferencesRepository
) {
    val name = getName(profilePreferencesRepository)

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "Hello $name",
            style = MaterialTheme.typography.titleMedium.copy(
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                textAlign = TextAlign.Center
            ),
            modifier = Modifier.weight(1f)
        )

        Button(
            onClick = { navController.navigate(Routes.Settings.toString()) },
            modifier = Modifier
                .height(48.dp)
                .background(
                    color = Color.Transparent,
                    shape = MaterialTheme.shapes.medium
                ),
            colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
            contentPadding = PaddingValues(0.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Settings,
                contentDescription = null,
                tint = Color.Black
            )
        }
    }
}

fun getName(profilePreferencesRepository: IProfilePreferencesRepository): String {
    val profile = profilePreferencesRepository.load()
    var name = profile.name

    if (name.isBlank())
        name = "user"

    return name
}