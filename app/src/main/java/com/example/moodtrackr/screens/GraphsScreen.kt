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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.moodtrackr.components.LineChart
import com.example.moodtrackr.components.bars.MainBottomBar
import com.example.moodtrackr.components.bars.TitleTopBar
import com.example.moodtrackr.enums.TimeFrame
import com.example.moodtrackr.extensions.capitalizeFirstLetter
import com.example.moodtrackr.helpers.ActivityHelper
import com.example.moodtrackr.models.MoodEntry
import com.example.moodtrackr.utilities.DateUtilities
import com.example.moodtrackr.viewModels.MainViewModel
import com.github.mikephil.charting.components.XAxis.XAxisPosition
import java.time.LocalDate
import kotlin.reflect.KProperty1
import kotlin.reflect.full.memberProperties
import kotlin.reflect.full.starProjectedType

@Composable
fun GraphsScreen(
    navController: NavController,
    viewModel: MainViewModel
) {
    var isTimeFrameMenuExpanded by remember { mutableStateOf(false) }
    var isPropertiesMenuExpanded by remember { mutableStateOf(false) }
    var selectedTimeFrame by remember { mutableStateOf(TimeFrame.AllTime) }
    var selectedProperty by remember { mutableStateOf("overall") }

    val moodEntries = viewModel.moodEntriesRepository.getMoodEntriesInRange(
        DateUtilities.getStartDateFromTimeFrame(selectedTimeFrame),
        LocalDate.now()
    )

    val indicatorListNames = MoodEntry::class.memberProperties
        .filterIsInstance<KProperty1<MoodEntry, Int>>()
        .filter { it.returnType == Int::class.starProjectedType && !it.name.startsWith("_") }
        .map { it.name }

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
            TitleTopBar(navController,"Graphs")

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        isTimeFrameMenuExpanded = true
                    },
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.List,
                    contentDescription = "OpenTimeFrameSelection",
                    tint = MaterialTheme.colorScheme.onBackground,
                )
                Text(
                    text = "Open time frame selection list",
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.onBackground
                )
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        isPropertiesMenuExpanded = true
                    },
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.List,
                    contentDescription = "OpenPropertiesSelection",
                    tint = MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier
                )
                Text(
                    text = "Open properties selection list",
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.onBackground
                )
            }

            DropdownMenu(
                expanded = isTimeFrameMenuExpanded,
                onDismissRequest = { isTimeFrameMenuExpanded = false },
                modifier = Modifier.padding(8.dp)
            ) {
                TimeFrame.values().forEach { timeFrame ->
                    DropdownMenuItem(
                        text = { Text(text = timeFrame.displayName) },
                        onClick = {
                            selectedTimeFrame = timeFrame
                            isTimeFrameMenuExpanded = false
                        }
                    )
                }
            }

            DropdownMenu(
                expanded = isPropertiesMenuExpanded,
                onDismissRequest = { isPropertiesMenuExpanded = false },
                modifier = Modifier.padding(8.dp)
            ) {
                indicatorListNames.forEach { propertyName ->
                    DropdownMenuItem(
                        text = { Text(text = propertyName.capitalizeFirstLetter()?:"Unknown") },
                        onClick = {
                            selectedProperty = propertyName
                            isPropertiesMenuExpanded = false
                        }
                    )
                }
            }
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .align(Alignment.TopCenter)
                .padding(top = 90.dp)
                .padding(bottom = 45.dp)
        ) {
            if (moodEntries.isNotEmpty()) {
                val chartModifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
                LineChart(
                    modifier = chartModifier,
                    data = viewModel.graphManager.getLineData(selectedProperty, moodEntries),
                    description = selectedTimeFrame.displayName,
                    backgroundColor = Color.White,
                    legendEnabled = true,
                    xAxisPosition = XAxisPosition.BOTTOM,
                    yAxisMinimum = 0f,
                    yAxisEnabled = false,
                    xAxisConvertMillisToDate = true,
                    xAxisLabelCount = 4
                    )

                Spacer(modifier = Modifier.height(16.dp))
            } else {
                Text(text = "No mood entries available for the selected period.")
            }
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