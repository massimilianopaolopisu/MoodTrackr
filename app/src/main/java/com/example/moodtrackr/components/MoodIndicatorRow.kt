package com.example.moodtrackr.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun MoodIndicatorRow(label: String, progress: Int, max: Int = 100) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = label,
            modifier = Modifier
                .weight(3f)
                .padding(end = 8.dp)
        )
        LinearProgressIndicator(
            progress = progress.toFloat() / max,
            modifier = Modifier.weight(5f),
            color = MaterialTheme.colorScheme.primary
        )
        Text(
            text = progress.toString(),
            modifier = Modifier
                .weight(1f),
            textAlign = TextAlign.End
        )
    }
}