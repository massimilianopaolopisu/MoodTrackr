package com.example.moodtrackr.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.moodtrackr.enums.Routes
import com.example.moodtrackr.models.MoodEntry
import com.example.moodtrackr.utilities.DateUtilities
import java.time.LocalDate

@Composable
fun MoodEntrySummary(
    navController: NavController,
    moodEntry: MoodEntry?
) {
    val date = moodEntry?.date?: LocalDate.now()
    val route = "${Routes.EditMoodEntry}/${date}"

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = DateUtilities.getStringDateFromLocalDate(date),
                    style = MaterialTheme.typography.bodyLarge.copy(fontSize = 26.sp),
                    modifier = Modifier.weight(1f),
                    textAlign = TextAlign.Center
                )
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = "Edit Mood Entry",
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.clickable {
                        navController.navigate(route)
                    }
                )
            }
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
                    onClick = { navController.navigate(route) },
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

