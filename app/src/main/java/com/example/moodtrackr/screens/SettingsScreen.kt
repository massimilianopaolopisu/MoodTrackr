package com.example.moodtrackr.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Face
import androidx.compose.material3.Card
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.moodtrackr.components.NavBottomBar
import com.example.moodtrackr.components.ThemeOptionsRadioButtons
import com.example.moodtrackr.enums.Routes
import com.example.moodtrackr.enums.ThemeMode
import com.example.moodtrackr.models.ThemePreferences
import com.example.moodtrackr.viewModels.MainViewModel

@Composable
fun SettingsScreen(
    navController: NavController,
    viewModel: MainViewModel
) {
    val themePreferences = viewModel.themePreferencesRepository.load()

    var selectedTheme by remember { mutableStateOf(themePreferences.themeMode) }
    var dynamicColorsEnabled by remember { mutableStateOf(themePreferences.dynamicColorsEnabled) }
    val isSystemInDarkMode = isSystemInDarkTheme()
    var darkMode by remember { mutableStateOf(isSystemInDarkMode) }
    var lockOrientationEnabled by remember { mutableStateOf(themePreferences.lockOrientationEnabled) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.TopCenter)
        ) {
            Text(
                text = "Settings",
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    color = MaterialTheme.colorScheme.onBackground
                ),
                modifier = Modifier
                    .padding(bottom = 16.dp)
                    .align(Alignment.CenterHorizontally)
                    .weight(1f)
            )
        }
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .align(Alignment.TopCenter)
                .padding(top = 25.dp)
                .padding(bottom = 45.dp)
        ) {
            item {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            navController.navigate(Routes.EditProfile.toString())
                        }
                        .padding(top = 16.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp),
                        contentAlignment = Alignment.CenterStart
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = "Edit Profile",
                                modifier = Modifier
                                    .weight(1f)
                                    .padding(end = 16.dp),
                                fontSize = 16.sp
                            )
                            Icon(
                                imageVector = Icons.Default.Face,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.onBackground
                            )
                        }
                    }
                }
            }

            item {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp),
                        contentAlignment = Alignment.CenterStart
                    ) {
                        Column(
                            verticalArrangement = Arrangement.spacedBy(8.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(
                                text = "Theme",
                                fontSize = 16.sp
                            )
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
                                    viewModel.themePreferencesRepository.save(
                                        ThemePreferences(
                                            selectedTheme,
                                            dynamicColorsEnabled,
                                            lockOrientationEnabled
                                        )
                                    )
                                }
                            )
                        }
                    }
                }
            }

            item {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(
                            modifier = Modifier.weight(1f),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Text(
                                text = "Dynamic Colors",
                                fontSize = 16.sp
                            )
                        }

                        Switch(
                            checked = dynamicColorsEnabled,
                            onCheckedChange = { enabled ->
                                dynamicColorsEnabled = enabled
                                viewModel.themePreferencesRepository.save(
                                    ThemePreferences(
                                        selectedTheme,
                                        dynamicColorsEnabled,
                                        lockOrientationEnabled
                                    )
                                )
                            }
                        )
                    }
                }
            }

            item {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(
                            modifier = Modifier.weight(1f),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Text(
                                text = "Lock orientation",
                                fontSize = 16.sp
                            )
                        }

                        Switch(
                            checked = lockOrientationEnabled,
                            onCheckedChange = { enabled ->
                                lockOrientationEnabled = enabled
                                viewModel.themePreferencesRepository.save(
                                    ThemePreferences(
                                        selectedTheme,
                                        dynamicColorsEnabled,
                                        lockOrientationEnabled
                                    )
                                )
                            }
                        )
                    }
                }
            }

            item {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            viewModel.dataImporterExporterStrategy.export(null)
                        }
                        .padding(top = 16.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp),
                        contentAlignment = Alignment.CenterStart
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = "Export settings and data",
                                modifier = Modifier
                                    .weight(1f)
                                    .padding(end = 16.dp),
                                fontSize = 16.sp
                            )
                            Icon(
                                imageVector = Icons.Default.ArrowForward,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.onBackground
                            )
                        }
                    }
                }
            }

            item {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            viewModel.dataImporterExporterStrategy.import(null)
                        }
                        .padding(top = 16.dp, bottom = 16.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp),
                        contentAlignment = Alignment.CenterStart
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = "Import settings and data",
                                modifier = Modifier
                                    .weight(1f)
                                    .padding(end = 16.dp),
                                fontSize = 16.sp
                            )
                            Icon(
                                imageVector = Icons.Default.ArrowBack,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.onBackground
                            )
                        }
                    }
                }
            }
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
        ) {
            NavBottomBar(navController)
        }
    }
}