package com.example.moodtrackr.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.moodtrackr.extensions.capitalizeFirstLetter
import com.example.moodtrackr.models.IndicatorStats

@Composable
fun IndicatorStatsCard(indicatorStats: IndicatorStats) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation()
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = "Indicator: ${indicatorStats.indicatorName.capitalizeFirstLetter()}",
                style = MaterialTheme.typography.titleMedium
            )

            Text(
                text = "Minimum Value: ${indicatorStats.min?.value ?: "N/A"} (${indicatorStats.min?.date ?: "N/A"})",
                style = MaterialTheme.typography.labelMedium
            )

            Text(
                text = "Average Value: ${indicatorStats.average ?: "N/A"}",
                style = MaterialTheme.typography.labelMedium
            )

            Text(
                text = "Maximum Value: ${indicatorStats.max?.value ?: "N/A"} (${indicatorStats.max?.date ?: "N/A"})",
                style = MaterialTheme.typography.labelMedium
            )
        }
    }
}