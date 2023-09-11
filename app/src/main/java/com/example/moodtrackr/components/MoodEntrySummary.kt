package com.example.moodtrackr.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import com.example.moodtrackr.components.bars.DateBar
import com.example.moodtrackr.components.bars.EditBar
import com.example.moodtrackr.enums.Routes
import com.example.moodtrackr.models.MoodEntry
import com.example.moodtrackr.repositories.interfaces.IMoodEntriesRepository
import java.time.LocalDate

@Composable
fun MoodEntrySummary(
    navController: NavController,
    moodEntriesRepository: IMoodEntriesRepository,
    moodEntry: MoodEntry?,
    date: LocalDate,
    origin: String?,
    allowChangeDate: Boolean
) {
    val editRoute = "${Routes.EditMoodEntry}/$date"

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

            if (moodEntry != null){
                EditBar(
                    navController = navController,
                    moodEntriesRepository = moodEntriesRepository,
                    localDate = date
                )
            }
            else{
                Row(
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(top = 20.dp),
                    )
                {
                    Button(
                        onClick = { navController.navigate(editRoute) }
                    ) {
                        Text("Add Mood Entry")
                    }
                }
            }
        }

        if (moodEntry != null) {
            LazyColumn (
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .align(Alignment.TopCenter)
                    .padding(top = 90.dp)
                    .padding(bottom = 45.dp)
            ) {
                item {
                    MoodIndicatorRow("Overall", moodEntry.overall, useDynamicColors = true )
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

