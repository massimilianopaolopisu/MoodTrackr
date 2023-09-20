package com.example.moodtrackr.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.moodtrackr.components.MoodEntrySummary
import com.example.moodtrackr.components.bars.MainBottomBar
import com.example.moodtrackr.components.bars.TitleTopBar
import com.example.moodtrackr.enums.Routes
import com.example.moodtrackr.helpers.ActivityHelper
import com.example.moodtrackr.utilities.DateUtilities
import com.example.moodtrackr.viewModels.MainViewModel

@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: MainViewModel,
    date: String?
) {
    val name = viewModel.profile.name
    val moodEntry = viewModel.moodEntriesRepository.getMoodEntry(DateUtilities.getLocalDateFromStringDate(date))

    ActivityHelper.resetWindowBackground(viewModel.mainActivity)

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
            TitleTopBar(navController, "Mood Trackr")

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.CenterHorizontally),
                horizontalArrangement = Arrangement.Center

            )
            {
                Text(
                    text = "Hello $name, how do you feel?",
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier
                        .padding(bottom = 16.dp)
                )
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.CenterHorizontally),
                horizontalArrangement = Arrangement.Center

            )
            {
                MoodEntrySummary(
                    navController = navController,
                    moodEntriesRepository = viewModel.moodEntriesRepository,
                    moodEntry = moodEntry,
                    date = DateUtilities.getLocalDateFromStringDate(date),
                    origin = Routes.Home.toString(),
                    allowChangeDate = true
                )
            }
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
        ) {
            MainBottomBar(
                navController = navController
            )
        }
    }
}