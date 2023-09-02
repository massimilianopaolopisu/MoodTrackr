package com.example.moodtrackr.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
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
import com.example.moodtrackr.components.SaveBottomBar
import com.example.moodtrackr.components.ThemeOptionsRadioButtons
import com.example.moodtrackr.enums.Routes
import com.example.moodtrackr.enums.ThemeMode
import com.example.moodtrackr.models.ThemePreferences
import com.example.moodtrackr.repositories.ISave
import com.example.moodtrackr.repositories.ThemePreferencesRepository
import com.example.moodtrackr.ui.theme.MoodTrackrTheme

@Composable
fun SettingsScreen(navController: NavController) {
    val context = LocalContext.current
    val themePreferencesRepository = ThemePreferencesRepository(context)
    val themePreferences = themePreferencesRepository.load()

    var selectedTheme by remember { mutableStateOf(themePreferences.themeMode) }
    var dynamicColorsEnabled by remember { mutableStateOf(themePreferences.dynamicColorsEnabled) }
    val isSystemInDarkMode = isSystemInDarkTheme()
    var darkMode by remember { mutableStateOf(isSystemInDarkMode) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.TopCenter)
        ) {
            Text(
                text = "Settings",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(bottom = 16.dp)
            )
        }

        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .align(Alignment.TopCenter)
                .padding(top = 25.dp)
        ) {
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            navController.navigate(Routes.EditProfile.toString())
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

                ThemeOptionsRadioButtons(
                    options = listOf(
                        "System Default" to ThemeMode.System,
                        "Light" to ThemeMode.Light,
                        "Dark" to ThemeMode.Dark
                    ),
                    selectedOption = selectedTheme,
                    onOptionSelected = { themeMode ->
                        selectedTheme = themeMode

                        darkMode = when (themeMode) {
                            ThemeMode.System -> {
                                isSystemInDarkMode
                            }

                            ThemeMode.Light -> {
                                false
                            }

                            ThemeMode.Dark -> {
                                true
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
                        }
                    )
                }
            }
        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
        ) {
            @Suppress("UNCHECKED_CAST")
            val saveHandlerAndObjectPairList: List<Pair<ISave<Any>, Any>> = listOf<Pair<ISave<Any>, Any>>(
                themePreferencesRepository as ISave<Any> to ThemePreferences(selectedTheme, dynamicColorsEnabled) as Any
            )

            SaveBottomBar(navController, saveHandlerAndObjectPairList)
        }
    }

    MoodTrackrTheme(darkTheme = darkMode, dynamicColor = dynamicColorsEnabled){

    }
}