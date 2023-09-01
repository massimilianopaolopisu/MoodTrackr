package com.example.moodtrackr

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.moodtrackr.enums.ThemeMode
import com.example.moodtrackr.repositories.ThemePreferencesRepository
import com.example.moodtrackr.screens.EditProfileScreen
import com.example.moodtrackr.screens.HomeScreen
import com.example.moodtrackr.screens.SettingsScreen
import com.example.moodtrackr.ui.theme.MoodTrackrTheme

// MainActivity.kt
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MoodTrackrApp()
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun MoodTrackrApp() {
    val context = LocalContext.current

    val themePreferencesRepository = ThemePreferencesRepository(context)
    val themePreferences = themePreferencesRepository.load()

    val darkMode = when (themePreferences.themeMode) {
        ThemeMode.System -> {
            isSystemInDarkTheme()
        }

        ThemeMode.Light -> {
            false
        }

        ThemeMode.Dark -> {
            true
        }
    }

    MoodTrackrTheme(darkMode, themePreferences.dynamicColorsEnabled) {
        Content()
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Content() {
    val navController = rememberNavController()

    NavHost(navController, startDestination = "home") {
        composable("home") {
            HomeScreen(navController)
        }
        composable("editProfile") {
            EditProfileScreen(navController)
        }
        composable("settings") {
            SettingsScreen(navController)
        }
    }
}