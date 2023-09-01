package com.example.moodtrackr.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.moodtrackr.enums.ThemeMode
import com.example.moodtrackr.models.ThemePreferences
import com.example.moodtrackr.repositories.ThemePreferencesRepository

@Composable
fun SettingsScreen(navController: NavController) {
    val context = LocalContext.current
    val themePreferencesRepository = ThemePreferencesRepository(context)
    val themePreferences = themePreferencesRepository.load()

    var selectedTheme by remember { mutableStateOf(themePreferences.themeMode) }
    var dynamicColorsEnabled by remember { mutableStateOf(themePreferences.dynamicColorsEnabled) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Settings",
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        LazyColumn {
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            navController.navigate("editProfile")
                        }
                        .padding(vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Edit Profile",
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.weight(1f)
                    )
                    Icon(
                        imageVector = Icons.Default.ArrowForward,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
            }

            item {
                Text(
                    text = "Theme",
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(top = 16.dp)
                )
                Spacer(modifier = Modifier.height(8.dp))

                ThemeOptionRadioButton(
                    options = listOf(
                        "System Default" to ThemeMode.System,
                        "Light" to ThemeMode.Light,
                        "Dark" to ThemeMode.Dark
                    ),
                    selectedOption = selectedTheme,
                    onOptionSelected = { themeMode ->
                        selectedTheme = themeMode

                        when (themeMode) {
                            ThemeMode.System -> {
                                //TODO
                            }
                            ThemeMode.Light -> {
                                //TODO
                            }
                            ThemeMode.Dark -> {
                                //TODO
                            }
                        }
                    }
                )
            }

            item {
                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Dynamic Colors",
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.weight(1f)
                    )
                    Switch(
                        checked = dynamicColorsEnabled,
                        onCheckedChange = { enabled ->
                            dynamicColorsEnabled = enabled

                            if (enabled) {
                                //TODO
                            } else {
                                //TODO
                            }
                        }
                    )
                }
            }
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Button(
                    onClick = {
                        navController.navigate("home")
                    },
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth()
                ) {
                    Text("Back")
                }

                Button(
                    onClick = {
                        themePreferencesRepository.save(ThemePreferences(selectedTheme, dynamicColorsEnabled))
                        navController.navigate("home")
                    },
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth()
                ) {
                    Text("Save")
                }
            }
        }
    }

}

@Composable
fun ThemeOptionRadioButton(
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