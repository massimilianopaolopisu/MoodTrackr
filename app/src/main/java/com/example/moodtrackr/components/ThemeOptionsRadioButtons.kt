package com.example.moodtrackr.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.moodtrackr.enums.ThemeMode

@Composable
fun ThemeOptionsRadioButtons(
    options: List<Pair<String, ThemeMode>>,
    selectedOption: ThemeMode,
    onOptionSelected: (ThemeMode) -> Unit
) {
    options.forEach { (label, themeMode) ->
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    onOptionSelected(themeMode)
                }
                .padding(vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            RadioButton(
                selected = selectedOption == themeMode,
                onClick = { onOptionSelected(themeMode) }
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = label,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.weight(1f)
            )
        }
    }
}