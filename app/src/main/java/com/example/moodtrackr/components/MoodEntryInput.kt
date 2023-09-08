package com.example.moodtrackr.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun MoodEntryInput(
    label: String,
    value: Int,
    onValueChange: (Int) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = label,
            color = MaterialTheme.colorScheme.onBackground
        )
        Text(
            text = value.toString(),
            color = MaterialTheme.colorScheme.onBackground,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
        )
        Slider(
            value = value.toFloat(),
            colors = SliderColors(
                thumbColor = MaterialTheme.colorScheme.primary,
                activeTrackColor = MaterialTheme.colorScheme.primary,
                activeTickColor = MaterialTheme.colorScheme.primary,
                inactiveTrackColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f),
                inactiveTickColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f),
                disabledThumbColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.3f),
                disabledActiveTrackColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.3f),
                disabledActiveTickColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.3f),
                disabledInactiveTrackColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.12f),
                disabledInactiveTickColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.12f)
            ),
            onValueChange = { newValue ->
                onValueChange(newValue.toInt())
            },
            valueRange = 1f..100f,
            steps = 100
        )
    }
}