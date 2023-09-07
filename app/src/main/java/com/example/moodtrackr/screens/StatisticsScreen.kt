package com.example.moodtrackr.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.List
import androidx.compose.material3.*
import androidx.compose.runtime.*
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
import com.example.moodtrackr.repositories.interfaces.IMoodEntriesRepository
import java.time.LocalDate

@Composable
fun StatisticsScreen(
    navController: NavController,
    moodEntriesRepository: IMoodEntriesRepository
) {
    val statisticsCalculator = MoodEntryStatisticsCalculator()

    var selectedTimeFrame by remember { mutableStateOf(TimeFrame.LastWeek) }
    var statistics by remember { mutableStateOf(emptyList<IndicatorStats>()) }
    var isDropdownOpen by remember { mutableStateOf(false) }

    fun calculateStatistics() {
        val endDate = LocalDate.now()
        val startDate = when (selectedTimeFrame) {
            TimeFrame.LastWeek -> endDate.minusWeeks(1)
            TimeFrame.LastMonth -> endDate.minusMonths(1)
            TimeFrame.LastYear -> endDate.minusYears(1)
            TimeFrame.AllTime -> LocalDate.MIN
        }

        val moodEntriesInRange = moodEntriesRepository.getMoodEntriesInRange(startDate, endDate)
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