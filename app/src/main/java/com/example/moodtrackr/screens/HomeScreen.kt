package com.example.moodtrackr.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.moodtrackr.components.MoodEntrySummary
import com.example.moodtrackr.enums.Routes
import com.example.moodtrackr.repositories.IMoodEntriesRepository
import com.example.moodtrackr.repositories.IProfilePreferencesRepository
import java.time.LocalDate

@Composable
fun HomeScreen(
    navController: NavController,
    profilePreferencesRepository: IProfilePreferencesRepository,
    moodEntriesRepository: IMoodEntriesRepository
) {
    val name = getName(profilePreferencesRepository)
    val moodEntry = moodEntriesRepository.getMoodEntry(LocalDate.now())

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Hello $name",
            style = MaterialTheme.typography.bodyMedium.copy(fontSize = 18.sp),
            modifier = Modifier.padding(bottom = 16.dp)
        )

        Text(
            text = "How do you feel today?",
            style = MaterialTheme.typography.bodyMedium.copy(fontSize = 18.sp)
        )

        MoodEntrySummary(
            moodEntry = moodEntry
        )

        Spacer(modifier = Modifier.weight(1f))

        Button(
            onClick = { navController.navigate(Routes.Settings.toString()) },
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
                .padding(top = 16.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Transparent,
                contentColor = MaterialTheme.colorScheme.primary
            )
        ) {
            Icon(
                imageVector = Icons.Default.Settings,
                contentDescription = "Settings",
                modifier = Modifier.padding(end = 8.dp)
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