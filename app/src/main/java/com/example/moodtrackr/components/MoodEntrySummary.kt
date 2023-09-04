package com.example.moodtrackr.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.moodtrackr.enums.Routes
import com.example.moodtrackr.models.MoodEntry
import java.time.LocalDate

@Composable
fun MoodEntrySummary(
    navController: NavController,
    moodEntry: MoodEntry?
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(
            text = LocalDate.now().toString(),
            style = MaterialTheme.typography.bodyLarge.copy(fontSize = 26.sp),
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
        )

        Spacer(modifier = Modifier.height(8.dp))

        val route = "${Routes.EditMoodEntry}/${LocalDate.now()}"

        if (moodEntry == null) {
            Button(
                onClick = {  navController.navigate(route) },
                modifier = Modifier.align(Alignment.CenterHorizontally)
            ) {
                Text("Add Mood Entry")
            }
        } else {
            Button(
                onClick = {  navController.navigate(route) },
                modifier = Modifier.align(Alignment.CenterHorizontally)
            ) {
                Text("Edit Mood Entry")
            }
            MoodIndicator(
                label = "Happiness",
                progress = moodEntry.happiness.toFloat() / 100,
                value = moodEntry.happiness.toString()
            )

            MoodIndicator(
                label = "Anger",
                progress = moodEntry.anger.toFloat() / 100,
                value = moodEntry.anger.toString()
            )

            MoodIndicator(
                label = "Love",
                progress = moodEntry.love.toFloat() / 100,
                value = moodEntry.love.toString()
            )

            MoodIndicator(
                label = "Stress",
                progress = moodEntry.stress.toFloat() / 100,
                value = moodEntry.stress.toString()
            )

            MoodIndicator(
                label = "Energy",
                progress = moodEntry.energy.toFloat() / 100,
                value = moodEntry.energy.toString()
            )

            MoodIndicator(
                label = "Sleep",
                progress = moodEntry.sleep.toFloat() / 100,
                value = moodEntry.sleep.toString()
            )

            MoodIndicator(
                label = "Health",
                progress = moodEntry.health.toFloat() / 100,
                value = moodEntry.health.toString()
            )

            MoodIndicator(
                label = "Depression",
                progress = moodEntry.depression.toFloat() / 100,
                value = moodEntry.depression.toString()
            )

            MoodTextIndicator(
                label = "Notes",
                text = moodEntry.notes
            )
        }
    }
}