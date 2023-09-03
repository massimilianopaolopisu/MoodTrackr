package com.example.moodtrackr

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.moodtrackr.enums.Routes
import com.example.moodtrackr.enums.ThemeMode
import com.example.moodtrackr.helpers.SqlDatabaseHelper
import com.example.moodtrackr.repositories.ApplicationPreferencesRepository
import com.example.moodtrackr.repositories.IThemePreferencesRepository
import com.example.moodtrackr.screens.EditProfileScreen
import com.example.moodtrackr.screens.HomeScreen
import com.example.moodtrackr.screens.SettingsScreen
import com.example.moodtrackr.ui.theme.MoodTrackrTheme
import com.example.moodtrackr.utilities.DateUtilities
import com.example.moodtrackr.viewModels.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity @Inject constructor() : ComponentActivity() {
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MoodTrackrApp(
                LocalContext.current,
                viewModel.themePreferencesRepository
            )
        }
    }
}

@Composable
fun MoodTrackrApp(
    context: Context,
    themePreferencesRepository: IThemePreferencesRepository
) {
    init(context)

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

@Composable
fun Content() {
    val navController = rememberNavController()

    NavHost(navController, startDestination = Routes.Home.toString()) {
        composable(Routes.Home.toString()) {
            HomeScreen(navController)
        }
        composable(Routes.EditProfile.toString()) {
            EditProfileScreen(navController)
        }
        composable(Routes.Settings.toString()) {
            SettingsScreen(navController)
        }
    }
}

private fun init(context: Context){
    DateUtilities.initialize(context)
    val applicationPreferencesRepository = ApplicationPreferencesRepository(context)
    val applicationPreferences = applicationPreferencesRepository.load()

    if(!applicationPreferences.sqlDatabaseExists) {
        val sqlDatabaseHelper = SqlDatabaseHelper(context)
        sqlDatabaseHelper.writableDatabase
    }
}