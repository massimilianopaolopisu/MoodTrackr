package com.example.moodtrackr.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.moodtrackr.components.MoodEntryCrudBar
import com.example.moodtrackr.models.DatePickerAllSelectableDates
import com.example.moodtrackr.models.DatePickerSelectableDates
import com.example.moodtrackr.repositories.interfaces.IMoodEntriesRepository
import com.example.moodtrackr.utilities.DateUtilities
import java.time.LocalDate

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MoodEntriesHistoryScreen(
    navController: NavController,
    moodEntriesRepository: IMoodEntriesRepository
) {
    val moodEntryList = moodEntriesRepository.getAllMoodEntries()
    val datesToHighlight = moodEntryList.map { it.date }.toSet()
    val highlightedDates = remember { datesToHighlight }

    var selectedDate by remember { mutableStateOf(LocalDate.now()) }
    var showOnlyInsertedDays by remember { mutableStateOf(true) }

    val datePickerStateHighlighted = rememberDatePickerState(
        DateUtilities.getMillisFromLocalDate(selectedDate),
        selectableDates = DatePickerSelectableDates(highlightedDates)
    )

    val datePickerStateAll = rememberDatePickerState(
        DateUtilities.getMillisFromLocalDate(selectedDate),
        selectableDates = DatePickerAllSelectableDates()
    )

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
                text = "Mood Entries History",
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    color = MaterialTheme.colorScheme.onBackground
                ),
                modifier = Modifier
                    .padding(bottom = 16.dp)
                    .align(Alignment.CenterHorizontally)
                    .weight(1f)
            )
        }

        LazyColumn (
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .align(Alignment.TopCenter)
                .padding(top = 25.dp)
                .padding(bottom = 45.dp)
        ) {
            item {
                if(showOnlyInsertedDays) {
                    DatePicker(
                        state = datePickerStateHighlighted,
                        modifier = Modifier
                            .fillMaxWidth(),
                        showModeToggle = false,
                        colors = DatePickerDefaults
                            .colors(titleContentColor = MaterialTheme.colorScheme.onBackground,
                                navigationContentColor = MaterialTheme.colorScheme.onBackground,
                                headlineContentColor = MaterialTheme.colorScheme.onBackground
                            )
                    )
                }
                else{
                    DatePicker(
                        state = datePickerStateAll,
                        modifier = Modifier
                            .fillMaxWidth(),
                        showModeToggle = false,
                        colors = DatePickerDefaults
                            .colors(titleContentColor = MaterialTheme.colorScheme.onBackground,
                                navigationContentColor = MaterialTheme.colorScheme.onBackground,
                                headlineContentColor = MaterialTheme.colorScheme.onBackground
                            )
                    )
                }
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "Show only inserted days",
                            color = MaterialTheme.colorScheme.onBackground,
                            modifier = Modifier
                                .weight(1f)
                        )
                        Switch(
                            checked = showOnlyInsertedDays,
                            onCheckedChange = { enabled ->
                                showOnlyInsertedDays = enabled
                            }
                        )
                    }
                }
            }
        }

        selectedDate = if(showOnlyInsertedDays) {
            DateUtilities.getLocalDateFromMillis(datePickerStateHighlighted.selectedDateMillis?: 0)
        } else {
            DateUtilities.getLocalDateFromMillis(datePickerStateAll.selectedDateMillis?: 0)
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
        ) {
            MoodEntryCrudBar(
                navController = navController,
                moodEntriesRepository = moodEntriesRepository,
                selectedDate = selectedDate
            )
        }
    }
}