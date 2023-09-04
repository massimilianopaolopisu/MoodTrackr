package com.example.moodtrackr

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.moodtrackr.enums.Routes
import com.example.moodtrackr.enums.ThemeMode
import com.example.moodtrackr.helpers.SqlDatabaseHelper
import com.example.moodtrackr.repositories.IApplicationPreferencesRepository
import com.example.moodtrackr.screens.EditMoodEntryScreen
import com.example.moodtrackr.screens.EditProfileScreen
import com.example.moodtrackr.screens.HomeScreen
import com.example.moodtrackr.screens.SettingsScreen
import com.example.moodtrackr.ui.theme.MoodTrackrTheme
import com.example.moodtrackr.utilities.DateUtilities
import com.example.moodtrackr.viewModels.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.time.LocalDate
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity @Inject constructor() : ComponentActivity() {
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MoodTrackrApp(LocalContext.current, viewModel)
        }
    }
}

@Composable
fun MoodTrackrApp(
    context: Context,
    viewModel: MainViewModel
) {
    init(context, viewModel.applicationPreferencesRepository)

    val themePreferences = viewModel.themePreferencesRepository.load()

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
        Content(viewModel)
    }
}

@Composable
fun Content(viewModel: MainViewModel) {
    val navController = rememberNavController()

    NavHost(navController, startDestination = Routes.Home.toString()) {
        composable(Routes.Home.toString()) {
            HomeScreen(navController, viewModel.profilePreferencesRepository, viewModel.moodEntriesRepository)
        }
        composable(Routes.EditProfile.toString()) {
            EditProfileScreen(navController, viewModel.profilePreferencesRepository)
        }
        composable(Routes.Settings.toString()) {
            SettingsScreen(navController, viewModel.themePreferencesRepository)
        }
        composable(
            route = "${Routes.EditMoodEntry}/{date}",
            arguments = listOf(navArgument("date") { type = NavType.StringType })
        ) {
            backStackEntry ->
            EditMoodEntryScreen(
                navController,
                viewModel.moodEntriesRepository,
                backStackEntry.arguments?.getString("date")?: DateUtilities.getStringDateFromLocalDate(LocalDate.now()))
        }
    }
}

private fun init(
    context: Context,
    applicationPreferencesRepository: IApplicationPreferencesRepository
){
    DateUtilities.initialize(context)
    val applicationPreferences = applicationPreferencesRepository.load()

    if(!applicationPreferences.sqlDatabaseExists) {
        val sqlDatabaseHelper = SqlDatabaseHelper(context)
        sqlDatabaseHelper.writableDatabase
    }
}