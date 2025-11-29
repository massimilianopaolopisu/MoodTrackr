package com.example.moodtrackr.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun MoodIndicatorRow(
    label: String,
    value: Int,
    max: Int = 100,
    useDynamicColors: Boolean = false,
    progressColor: Color? = null
) {

    val progress = value.toFloat() / max
    var color = progressColor ?: MaterialTheme.colorScheme.onBackground

    if (progressColor == null && useDynamicColors) {
        color = if (value < 50) Color.Red else Color.Green
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = label,
            color = MaterialTheme.colorScheme.onBackground,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier
                .weight(3f)
                .padding(end = 8.dp)
        )
        LinearProgressIndicator(
            progress = progress,
            modifier = Modifier.weight(5f),
            color = color
        )
        Text(
            text = value.toString(),
            color = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier
                .weight(1f),
            textAlign = TextAlign.End
        )
    }
}