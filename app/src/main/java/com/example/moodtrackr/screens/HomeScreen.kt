package com.example.moodtrackr.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
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

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Hello $name",
            style = MaterialTheme.typography.titleMedium.copy(
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                textAlign = TextAlign.Center
            ),
            modifier = Modifier.padding(vertical = 8.dp)
        )

        Button(
            onClick = { navController.navigate(Routes.Settings.toString()) },
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
        ) {
            Text("Settings")
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