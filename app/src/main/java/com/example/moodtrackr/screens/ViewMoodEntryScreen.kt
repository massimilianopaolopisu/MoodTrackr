package com.example.moodtrackr.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.moodtrackr.components.MoodEntrySummary
import com.example.moodtrackr.components.bars.MainBottomBar
import com.example.moodtrackr.components.bars.TitleTopBar
import com.example.moodtrackr.enums.Routes
import com.example.moodtrackr.utilities.DateUtilities
import com.example.moodtrackr.viewModels.MainViewModel

@Composable
fun ViewMoodEntryScreen(
    navController: NavController,
    viewModel: MainViewModel,
    date: String?
) {
    val dateParsed = DateUtilities.getLocalDateFromStringDate(date)
    val moodEntry = viewModel.moodEntriesRepository.getMoodEntry(dateParsed)

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .align(Alignment.TopCenter)
        ) {
            TitleTopBar(navController, "Mood Entry")

            MoodEntrySummary(
                navController = navController,
                moodEntriesRepository = viewModel.moodEntriesRepository,
                moodEntry = moodEntry,
                date = dateParsed,
                origin = Routes.ViewMoodEntry.toString(),
                allowChangeDate = true
            )
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
        ) {
            MainBottomBar(navController = navController)
        }
    }
}