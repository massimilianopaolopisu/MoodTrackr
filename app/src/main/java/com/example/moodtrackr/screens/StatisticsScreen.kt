package com.example.moodtrackr.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.List
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.moodtrackr.components.IndicatorStatsCard
import com.example.moodtrackr.components.NavBottomBar
import com.example.moodtrackr.enums.TimeFrame
import com.example.moodtrackr.logic.statistics.MoodEntryStatisticsCalculator
import com.example.moodtrackr.models.IndicatorStats
import com.example.moodtrackr.utilities.DateUtilities
import com.example.moodtrackr.viewModels.MainViewModel
import java.time.LocalDate

@Composable
fun StatisticsScreen(
    navController: NavController,
    viewModel: MainViewModel
) {
    val statisticsCalculator = MoodEntryStatisticsCalculator()

    var selectedTimeFrame by remember { mutableStateOf(TimeFrame.LastWeek) }
    var statistics by remember { mutableStateOf(emptyList<IndicatorStats>()) }
    var isDropdownOpen by remember { mutableStateOf(false) }

    fun calculateStatistics() {
        val startDate = DateUtilities.getStartDateFromTimeFrame(selectedTimeFrame)
        val moodEntriesInRange = viewModel.moodEntriesRepository.getMoodEntriesInRange(startDate, LocalDate.now())
        statistics = statisticsCalculator.calculateStats(moodEntriesInRange)
    }

    LaunchedEffect(selectedTimeFrame) {
        calculateStatistics()
    }

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
            Text(
                text = "Statistics",
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    color = Color.Black
                ),
                modifier = Modifier
                    .padding(bottom = 16.dp)
                    .align(Alignment.CenterHorizontally)
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.List,
                    contentDescription = "OpenTimeFrameSelection",
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.clickable {
                        isDropdownOpen = true
                    }
                )
                Text(
                    text = "Open time frame selection list",
                    style = MaterialTheme.typography.labelMedium,
                )
            }
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
                if (isDropdownOpen) {
                    DropdownMenu(
                        expanded = true,
                        onDismissRequest = { isDropdownOpen = false },
                        modifier = Modifier.padding(8.dp)
                    ) {
                        TimeFrame.values().forEach { timeFrame ->
                            DropdownMenuItem(
                                text = { Text(text = timeFrame.displayName) },
                                onClick = {
                                    selectedTimeFrame = timeFrame
                                    calculateStatistics()
                                }
                            )
                        }
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))

                if (statistics.isNotEmpty()) {
                    statistics.forEach { indicatorStats ->
                        IndicatorStatsCard(indicatorStats)
                        Spacer(modifier = Modifier.height(16.dp))
                    }
                } else {
                    Text(text = "No statistics available for the selected period.")
                }
            }
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
        ) {
            NavBottomBar(
                navController = navController
            )
        }
    }
}