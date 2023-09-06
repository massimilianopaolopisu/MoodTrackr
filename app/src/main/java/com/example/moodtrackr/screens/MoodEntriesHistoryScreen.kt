package com.example.moodtrackr.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
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
    var selectedDate by remember { mutableStateOf(LocalDate.now()) }
    val datePicker = rememberDatePickerState(DateUtilities.getMillisFromLocalDate(selectedDate))

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
                DatePicker(
                    state = datePicker,
                    modifier = Modifier
                        .fillMaxWidth(),
                    showModeToggle = false,
                    colors = DatePickerDefaults
                        .colors(titleContentColor = Color.Black,
                            navigationContentColor = Color.Black,
                            headlineContentColor = Color.Black)
                )
            }
        }
        selectedDate = DateUtilities.getLocalDateFromMillis(datePicker.selectedDateMillis?: 0)

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
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add",
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier
                        .clickable {
                            navController.navigate("${Routes.EditMoodEntry}/${selectedDate}")
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
                    contentDescription = "Remove",
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier
                        .clickable {
                            navController.navigate("${Routes.EditMoodEntry}/${selectedDate}")
                        }
                        .align(Alignment.CenterVertically)
                        .weight(1f)
                )
            }
        }
    }
}