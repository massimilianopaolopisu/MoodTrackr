package com.example.moodtrackr.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.moodtrackr.components.MoodEntryInput
import com.example.moodtrackr.components.SaveBottomBar
import com.example.moodtrackr.models.MoodEntry
import com.example.moodtrackr.repositories.interfaces.IMoodEntriesRepository
import com.example.moodtrackr.repositories.interfaces.ISave
import com.example.moodtrackr.utilities.DateUtilities
import java.time.LocalDate

@Composable
fun EditMoodEntryScreen(
    navController: NavController,
    moodEntriesRepository: IMoodEntriesRepository,
    date: String?
) {
    val dateParsed = if (date.isNullOrBlank()) DateUtilities.getStringDateFromLocalDate(LocalDate.now()) else date
    val moodEntryDate = DateUtilities.getLocalDateFromStringDate(dateParsed)
    val moodEntry = moodEntriesRepository.getMoodEntry(moodEntryDate) ?: MoodEntry()
    moodEntry.date = moodEntryDate

    var happiness by remember { mutableIntStateOf(moodEntry.happiness) }
    var love by remember { mutableIntStateOf(moodEntry.love) }
    var energy by remember { mutableIntStateOf(moodEntry.energy) }
    var health by remember { mutableIntStateOf(moodEntry.health) }
    var anger by remember { mutableIntStateOf(moodEntry.anger) }
    var stress by remember { mutableIntStateOf(moodEntry.stress) }
    var sleep by remember { mutableIntStateOf(moodEntry.sleep) }
    var depression by remember { mutableIntStateOf(moodEntry.depression) }
    var notes by remember { mutableStateOf(moodEntry.notes) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.TopCenter)
        ) {
            Text(
                text = "Edit Mood Entry",
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    color = Color.Black),
                modifier = Modifier
                    .padding(bottom = 16.dp)
                    .align(Alignment.CenterHorizontally)
            )

            Text(
                text = DateUtilities.getStringDateFromLocalDate(moodEntry.date),
                style = MaterialTheme.typography.bodyLarge.copy(fontSize = 26.sp),
                modifier = Modifier
                    .weight(1f)
                    .align(Alignment.CenterHorizontally),
                textAlign = TextAlign.Center
            )

        }

        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .align(Alignment.TopCenter)
                .padding(top = 80.dp)
                .padding(bottom = 45.dp)
        ) {
            item {
                MoodEntryInput(
                    label = "Happiness",
                    value = happiness,
                    onValueChange = { happiness = it }
                )

                MoodEntryInput(
                    label = "Love",
                    value = love,
                    onValueChange = { love = it }
                )

                MoodEntryInput(
                    label = "Energy",
                    value = energy,
                    onValueChange = { energy = it }
                )

                MoodEntryInput(
                    label = "Health",
                    value = health,
                    onValueChange = { health = it }
                )

                MoodEntryInput(
                    label = "Anger",
                    value = anger,
                    onValueChange = { anger = it }
                )

                MoodEntryInput(
                    label = "Stress",
                    value = stress,
                    onValueChange = { stress = it }
                )

                MoodEntryInput(
                    label = "Sleep",
                    value = sleep,
                    onValueChange = { sleep = it }
                )

                MoodEntryInput(
                    label = "Depression",
                    value = depression,
                    onValueChange = { depression = it }
                )

                TextField(
                    value = notes,
                    onValueChange = { notes = it },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    label = { Text("Notes") },
                    keyboardOptions = KeyboardOptions.Default.copy(
                        imeAction = ImeAction.Done
                    )
                )
            }
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
        ){
            @Suppress("UNCHECKED_CAST")
            val saveHandlerAndObjectPairList: List<Pair<ISave<Any>, Any>> = listOf(
                moodEntriesRepository as ISave<Any> to MoodEntry(
                    date = moodEntryDate,
                    happiness = happiness,
                    love = love,
                    energy = energy,
                    health = health,
                    anger = anger,
                    stress = stress,
                    sleep = sleep,
                    depression = depression,
                    notes = notes
                ) as Any
            )

            SaveBottomBar(
                navController = navController,
                saveHandlerAndObjectPairList = saveHandlerAndObjectPairList
            )
        }
    }
}
