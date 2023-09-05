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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.moodtrackr.components.SaveBottomBar
import com.example.moodtrackr.components.ThemeOptionsRadioButtons
import com.example.moodtrackr.dataImportExport.interfaces.IDataImporterExporterStrategy
import com.example.moodtrackr.enums.Routes
import com.example.moodtrackr.enums.ThemeMode
import com.example.moodtrackr.models.ThemePreferences
import com.example.moodtrackr.repositories.interfaces.ISave
import com.example.moodtrackr.repositories.interfaces.IThemePreferencesRepository
import com.example.moodtrackr.ui.theme.MoodTrackrTheme

@Composable
fun SettingsScreen(
    navController: NavController,
    themePreferencesRepository: IThemePreferencesRepository,
    dataImporterExporterStrategy: IDataImporterExporterStrategy
) {
    val themePreferences = themePreferencesRepository.load()

    var selectedTheme by remember { mutableStateOf(themePreferences.themeMode) }
    var dynamicColorsEnabled by remember { mutableStateOf(themePreferences.dynamicColorsEnabled) }
    val isSystemInDarkMode = isSystemInDarkTheme()
    var darkMode by remember { mutableStateOf(isSystemInDarkMode) }

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
                    color = Color.Black),
                modifier = Modifier
                    .padding(bottom = 16.dp)
                    .align(Alignment.CenterHorizontally)
                    .weight(1f)
            )
        }

        LazyColumn (
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .align(Alignment.TopCenter)
                .padding(top = 25.dp)
                .padding(bottom = 45.dp)
        ) {
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            navController.navigate(Routes.EditProfile.toString())
                        }
                        .padding(top = 16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Edit Profile",
                        modifier = Modifier
                            .weight(1f)
                    )
                    Icon(
                        imageVector = Icons.Default.Face,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
            }

            item {
                Text(
                    text = "Theme",
                    modifier = Modifier
                        .padding(top = 16.dp)
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
                    }
                )
            }

            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Dynamic Colors",
                        modifier = Modifier
                            .weight(1f)
                    )
                    Switch(
                        checked = dynamicColorsEnabled,
                        onCheckedChange = { enabled ->
                            dynamicColorsEnabled = enabled
                        }
                    )
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            dataImporterExporterStrategy.export(null)
                        }
                        .padding(top = 16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Export settings and data",
                        modifier = Modifier
                            .weight(1f)
                    )
                    Icon(
                        imageVector = Icons.Default.ArrowForward,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            dataImporterExporterStrategy.import(null)
                        }
                        .padding(top = 16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Import settings and data",
                        modifier = Modifier
                            .weight(1f)
                    )
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary
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