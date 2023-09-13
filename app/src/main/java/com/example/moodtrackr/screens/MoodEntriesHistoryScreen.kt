package com.example.moodtrackr.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.moodtrackr.components.bars.MainBottomBar
import com.example.moodtrackr.components.bars.TitleTopBar
import com.example.moodtrackr.enums.Routes
import com.example.moodtrackr.models.DatePickerAllSelectableDates
import com.example.moodtrackr.models.DatePickerSelectableDates
import com.example.moodtrackr.utilities.DateUtilities
import com.example.moodtrackr.viewModels.MainViewModel
import java.time.LocalDate

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MoodEntriesHistoryScreen(
    navController: NavController,
    viewModel: MainViewModel
) {
    val moodEntryList = viewModel.moodEntriesRepository.getAllMoodEntries()
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

    if (showOnlyInsertedDays && selectedDate != LocalDate.now()) {
        LaunchedEffect(selectedDate) {
            navController.navigate("${Routes.ViewMoodEntry}/$selectedDate")
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
            TitleTopBar(navController, "Mood Entries History")
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
            MainBottomBar(navController)
        }
    }
}