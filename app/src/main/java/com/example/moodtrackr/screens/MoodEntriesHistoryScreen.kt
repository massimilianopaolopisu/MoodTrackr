package com.example.moodtrackr.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.moodtrackr.enums.Routes
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
    var showDeleteConfirmDialog by remember { mutableStateOf(false) }
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
                    color = Color.Black),
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
                            .colors(
                                titleContentColor = Color.Black,
                                navigationContentColor = Color.Black,
                                headlineContentColor = Color.Black
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
                            .colors(
                                titleContentColor = Color.Black,
                                navigationContentColor = Color.Black,
                                headlineContentColor = Color.Black
                            )
                    )
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Show only inserted days",
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

        selectedDate = if(showOnlyInsertedDays) {
            DateUtilities.getLocalDateFromMillis(datePickerStateHighlighted.selectedDateMillis?: 0)
        } else {
            DateUtilities.getLocalDateFromMillis(datePickerStateAll.selectedDateMillis?: 0)
        }

        if (showDeleteConfirmDialog) {
            AlertDialog(
                onDismissRequest = {
                    showDeleteConfirmDialog = false
                },
                title = { Text("Confirm") },
                text = { Text("Are you sure to remove $selectedDate Mood Entry?") },
                confirmButton = {
                    Button(
                        onClick = {
                            moodEntriesRepository.deleteMoodEntry(selectedDate)
                            showDeleteConfirmDialog = false
                        }
                    ) {
                        Text("Yes")
                    }
                },
                dismissButton = {
                    Button(
                        onClick = {
                            showDeleteConfirmDialog = false
                        }
                    ) {
                        Text("No")
                    }
                }
            )
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Icon(
                    imageVector = Icons.Default.KeyboardArrowLeft,
                    contentDescription = "Back",
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier
                        .clickable {
                            navController.popBackStack()
                        }
                        .align(Alignment.CenterVertically)
                        .weight(1f)
                )
                Icon(
                    imageVector = Icons.Default.Info,
                    contentDescription = "View",
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier
                        .clickable {
                            navController.navigate("${Routes.ViewMoodEntry}/${selectedDate}")
                        }
                        .align(Alignment.CenterVertically)
                        .weight(1f)
                )
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = "Update",
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier
                        .clickable {
                            navController.navigate("${Routes.EditMoodEntry}/${selectedDate}")
                        }
                        .align(Alignment.CenterVertically)
                        .weight(1f)
                )
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Delete",
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier
                        .clickable {
                            showDeleteConfirmDialog = true
                        }
                        .align(Alignment.CenterVertically)
                        .weight(1f)
                )
            }
        }
    }
}