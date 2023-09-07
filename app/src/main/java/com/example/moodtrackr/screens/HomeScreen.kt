package com.example.moodtrackr.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.moodtrackr.components.MainBottomBar
import com.example.moodtrackr.components.MoodEntrySummary
import com.example.moodtrackr.enums.Routes
import com.example.moodtrackr.repositories.interfaces.IMoodEntriesRepository
import com.example.moodtrackr.repositories.interfaces.IProfilePreferencesRepository
import java.time.LocalDate

@Composable
fun HomeScreen(
    navController: NavController,
    profilePreferencesRepository: IProfilePreferencesRepository,
    moodEntriesRepository: IMoodEntriesRepository,
    date: LocalDate
) {
    val name = getName(profilePreferencesRepository)
    val moodEntry = moodEntriesRepository.getMoodEntry(date)

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .align(Alignment.TopCenter)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Text(
                    text = "Hello $name, how do you feel today?",
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp,
                        color = Color.Black),
                    modifier = Modifier
                        .padding(bottom = 16.dp)
                        .align(Alignment.CenterVertically)
                        .weight(1f)
                )
            }

            MoodEntrySummary(
                navController = navController,
                moodEntry = moodEntry,
                date = date,
                origin = Routes.Home.toString(),
                allowChangeDate = false
            )
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
        ) {
            MainBottomBar(
                navController = navController
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