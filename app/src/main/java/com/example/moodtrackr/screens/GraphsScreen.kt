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
import androidx.compose.material.icons.filled.List
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
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.moodtrackr.components.LineChart
import com.example.moodtrackr.components.NavBottomBar
import com.example.moodtrackr.enums.TimeFrame
import com.example.moodtrackr.models.MoodEntry
import com.example.moodtrackr.utilities.DateUtilities
import com.example.moodtrackr.viewModels.MainViewModel
import com.github.mikephil.charting.components.XAxis.XAxisPosition
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import java.time.LocalDate
import kotlin.reflect.KProperty1
import kotlin.reflect.full.declaredMemberProperties
import kotlin.reflect.full.memberProperties
import kotlin.reflect.full.starProjectedType

private val indicatorListNames = MoodEntry::class.memberProperties
    .filterIsInstance<KProperty1<MoodEntry, Int>>()
    .filter { it.returnType == Int::class.starProjectedType && !it.name.startsWith("_") }
    .map { it.name }

@Composable
fun GraphsScreen(
    navController: NavController,
    viewModel: MainViewModel
) {
    var isTimeFrameMenuExpanded by remember { mutableStateOf(false) }
    var isPropertiesMenuExpanded by remember { mutableStateOf(false) }
    var selectedTimeFrame by remember { mutableStateOf(TimeFrame.LastWeek) }
    var selectedProperty by remember { mutableStateOf("Overall") }

    val moodEntries = viewModel.moodEntriesRepository.getMoodEntriesInRange(
        DateUtilities.getStartDateFromTimeFrame(selectedTimeFrame),
        LocalDate.now()
    )

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
                text = "Graph",
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
                        isTimeFrameMenuExpanded = true
                    }
                )
                Text(
                    text = "Open time frame selection list",
                    style = MaterialTheme.typography.labelMedium,
                )
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.List,
                    contentDescription = "OpenPropertiesSelection",
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.clickable {
                        isPropertiesMenuExpanded = true
                    }
                )
                Text(
                    text = "Open properties selection list",
                    style = MaterialTheme.typography.labelMedium,
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
                        text = { Text(text = propertyName) },
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
                    data = createLineData(selectedProperty, moodEntries),
                    description = selectedTimeFrame.displayName,
                    backgroundColor = Color.White,
                    legendEnabled = true,
                    xAxisPosition = XAxisPosition.BOTTOM,
                    yAxisMinimum = 0f,
                    yAxisEnabled = true,
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
            NavBottomBar(
                navController = navController
            )
        }
    }
}

fun createLineData(propertyName: String, moodEntries: List<MoodEntry>): LineData {
    val entries = mutableListOf<Entry>()

    moodEntries.forEachIndexed { _, moodEntry ->
        val value = getValueFromPropertyName(propertyName, moodEntry) ?: 0
        val dateInMillis = DateUtilities.getMillisFromLocalDate(moodEntry.date)
        entries.add(Entry(dateInMillis.toFloat(), value.toFloat()))
    }

    val dataSet = LineDataSet(entries, propertyName)
    dataSet.color = Color.Blue.toArgb()
    dataSet.lineWidth = 2f
    dataSet.setCircleColor(Color.Blue.toArgb())
    dataSet.setDrawValues(false)

    return LineData(dataSet)
}

fun getValueFromPropertyName(fieldName: String, moodEntry: MoodEntry): Int? {
    val getter = MoodEntry::class.declaredMemberProperties.find { it.name == fieldName }?.getter
    return getter?.call(moodEntry) as? Int
}