package com.example.moodtrackr.screens

import androidx.compose.foundation.gestures.animateScrollBy
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.moodtrackr.components.FloatingScrollButton
import com.example.moodtrackr.components.MoodEntryEditCard
import com.example.moodtrackr.components.MoodEntryNoteEditCard
import com.example.moodtrackr.components.bars.DateBar
import com.example.moodtrackr.components.bars.SaveBottomBar
import com.example.moodtrackr.components.bars.TitleTopBar
import com.example.moodtrackr.enums.Routes
import com.example.moodtrackr.models.MoodEntry
import com.example.moodtrackr.repositories.interfaces.ISave
import com.example.moodtrackr.utilities.DateUtilities
import com.example.moodtrackr.viewModels.MainViewModel
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import java.time.LocalDate

@Composable
fun EditMoodEntryScreen(
    navController: NavController,
    viewModel: MainViewModel,
    date: String?
) {
    val dateParsed = if (date.isNullOrBlank()) DateUtilities.getStringDateFromLocalDate(LocalDate.now()) else date
    val moodEntryDate = DateUtilities.getLocalDateFromStringDate(dateParsed)
    val moodEntry = viewModel.moodEntriesRepository.getMoodEntry(moodEntryDate) ?: MoodEntry()
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

    var isButtonVisible by remember { mutableStateOf(true) }

    val coroutineScope = rememberCoroutineScope()
    val localDensityCurrent = LocalDensity.current
    val lazyListState = rememberLazyListState()

    LaunchedEffect(lazyListState) {
        snapshotFlow { lazyListState.firstVisibleItemIndex }
            .distinctUntilChanged()
            .collect { visibleItemIndex ->
                isButtonVisible = visibleItemIndex < lazyListState.layoutInfo.visibleItemsInfo.size - 1
            }
    }

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
            TitleTopBar(navController, "Edit Mood Entry")

            DateBar(
                navController = navController,
                localDate = moodEntryDate,
                origin = Routes.EditMoodEntry.toString(),
                showButtons = true
            )
        }

        LazyColumn(
            state = lazyListState,
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .align(Alignment.TopCenter)
                .padding(top = 80.dp)
                .padding(bottom = 45.dp)
        ) {
            item {
                MoodEntryEditCard(label = "Happiness", value = happiness) { newValue ->
                    happiness = newValue
                }
            }

            item {
                MoodEntryEditCard(label = "Love", value = love) { newValue ->
                    love = newValue
                }
            }

            item {
                MoodEntryEditCard(label = "Energy", value = energy) { newValue ->
                    energy = newValue
                }
            }

            item {
                MoodEntryEditCard(label = "Health", value = health) { newValue ->
                    health = newValue
                }
            }

            item {
                MoodEntryEditCard(label = "Anger", value = anger) { newValue ->
                    anger = newValue
                }
            }

            item {
                MoodEntryEditCard(label = "Stress", value = stress) { newValue ->
                    stress = newValue
                }
            }

            item {
                MoodEntryEditCard(label = "Sleep", value = sleep) { newValue ->
                    sleep = newValue
                }
            }

            item {
                MoodEntryEditCard(label = "Depression", value = depression) { newValue ->
                    depression = newValue
                }
            }

            item {
                MoodEntryNoteEditCard(label = "Notes", value = notes) { newValue ->
                    notes = newValue
                }
            }
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
        ) {
            @Suppress("UNCHECKED_CAST")
            val saveHandlerAndObjectPairList: List<Pair<ISave<Any>, Any>> = listOf(
                viewModel.moodEntriesRepository as ISave<Any> to MoodEntry(
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

            Row(
                modifier = Modifier
                    .padding(bottom = 25.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                if (isButtonVisible) {
                    FloatingScrollButton(
                        modifier = Modifier
                            .padding(bottom = 16.dp),
                        onClick = {
                            val distanceToScroll = with(localDensityCurrent) { 200.dp.toPx() }

                            coroutineScope.launch {
                                lazyListState.animateScrollBy(distanceToScroll)
                            }
                        }
                    )
                }
            }

            SaveBottomBar(
                navController = navController,
                saveHandlerAndObjectPairList = saveHandlerAndObjectPairList
            )
        }
    }
}