package com.example.moodtrackr.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.moodtrackr.enums.Routes
import com.example.moodtrackr.models.MoodEntry
import java.time.LocalDate

@Composable
fun MoodEntrySummary(
    navController: NavController,
    moodEntry: MoodEntry?,
    date: LocalDate,
    origin: String?,
    allowChangeDate: Boolean
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            DateBar(
                navController = navController,
                localDate = date ,
                origin = origin,
                showButtons = allowChangeDate
            )
        }

        if (moodEntry == null) {
            Column (
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .align(Alignment.TopCenter)
                    .padding(top = 50.dp)
                    .padding(bottom = 45.dp)
            ) {
                Button(
                    onClick = { navController.navigate("${Routes.EditMoodEntry}/$date") },
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                ) {
                    Text("Add Mood Entry")
                }
            }
        } else {

            LazyColumn (
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .align(Alignment.TopCenter)
                    .padding(top = 50.dp)
                    .padding(bottom = 45.dp)
            ) {
                item {
                    MoodIndicatorRow("Overall", moodEntry.getOverallIndicator(), useDynamicColors = true )
                    MoodIndicatorRow("Happiness", moodEntry.happiness)
                    MoodIndicatorRow("Anger", moodEntry.anger)
                    MoodIndicatorRow("Love", moodEntry.love)
                    MoodIndicatorRow("Stress", moodEntry.stress)
                    MoodIndicatorRow("Energy", moodEntry.energy)
                    MoodIndicatorRow("Sleep", moodEntry.sleep)
                    MoodIndicatorRow("Health", moodEntry.health)
                    MoodIndicatorRow("Depression", moodEntry.depression)
                    MoodTextIndicator("Notes", moodEntry.notes)
                }
            }
        }
    }
}

