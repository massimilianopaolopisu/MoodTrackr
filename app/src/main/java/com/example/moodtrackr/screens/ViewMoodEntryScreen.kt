package com.example.moodtrackr.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import com.example.moodtrackr.components.MoodEntrySummary
import com.example.moodtrackr.components.MoodEntryViewBar
import com.example.moodtrackr.enums.Routes
import com.example.moodtrackr.repositories.interfaces.IMoodEntriesRepository
import com.example.moodtrackr.utilities.DateUtilities

@Composable
fun ViewMoodEntryScreen(
    navController: NavController,
    moodEntriesRepository: IMoodEntriesRepository,
    date: String?
) {
    val dateParsed = DateUtilities.getLocalDateFromStringDate(date)
    val moodEntry = moodEntriesRepository.getMoodEntry(dateParsed)

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
            Text(
                text = "Mood Entry",
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    color = Color.Black),
                modifier = Modifier
                    .padding(bottom = 16.dp)
                    .align(Alignment.CenterHorizontally)
            )

            MoodEntrySummary(
                navController = navController,
                moodEntry = moodEntry,
                date = dateParsed,
                origin = Routes.ViewMoodEntry.toString(),
                allowChangeDate = true
            )
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
        ) {
            MoodEntryViewBar(
                navController = navController
            )
        }
    }
}