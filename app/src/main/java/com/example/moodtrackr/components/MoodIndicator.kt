package com.example.moodtrackr.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun MoodIndicator(
    label: String,
    progress: Float,
    value: String
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.widthIn(0.dp, 100.dp)
        )

        LinearProgressIndicator(
            progress = progress,
            modifier = Modifier
                .weight(1f)
                .height(4.dp)
                .padding(horizontal = 8.dp),
            color = MaterialTheme.colorScheme.primary
        )

        Text(
            text = value,
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.widthIn(0.dp, 40.dp)
        )
    }
}