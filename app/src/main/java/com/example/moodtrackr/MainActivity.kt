package com.example.moodtrackr

import android.content.Context
import android.content.pm.ActivityInfo
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
import com.example.moodtrackr.repositories.interfaces.IApplicationPreferencesRepository
import com.example.moodtrackr.screens.EditMoodEntryScreen
import com.example.moodtrackr.screens.EditProfileScreen
import com.example.moodtrackr.screens.HomeScreen
import com.example.moodtrackr.screens.MoodEntriesHistoryScreen
import com.example.moodtrackr.screens.SettingsScreen
import com.example.moodtrackr.screens.ViewMoodEntryScreen
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
        requestedOrientation = getOrientationFromPreferences()

        setContent {
            MoodTrackrApp(LocalContext.current, viewModel)
        }
    }

    private fun getOrientationFromPreferences(): Int{
        val themePreferences = viewModel.themePreferencesRepository.load()

        return if(themePreferences.lockOrientationEnabled)
            ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        else
            ActivityInfo.SCREEN_ORIENTATION_FULL_SENSOR
    }
}

@Composable
fun MoodTrackrApp(
    context: Context,
    viewModel: MainViewModel
) {
    init(context, viewModel.applicationPreferencesRepository)

    val themePreferences = viewModel.themePreferencesRepository.load()

    //Activity.setRequestedOrientation() = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

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
            HomeScreen(
                navController,
                viewModel.profilePreferencesRepository,
                viewModel.moodEntriesRepository,
                LocalDate.now())
        }
        composable(
            route = "${Routes.Home}/{date}",
            arguments = listOf(navArgument("date") { type = NavType.StringType })
        ){
                backStackEntry ->
            HomeScreen(
                navController,
                viewModel.profilePreferencesRepository,
                viewModel.moodEntriesRepository,
                DateUtilities.getLocalDateFromStringDate(backStackEntry.arguments?.getString("date")?: "")
            )
        }
        composable(Routes.EditProfile.toString()) {
            EditProfileScreen(navController, viewModel.profilePreferencesRepository)
        }
        composable(Routes.Settings.toString()) {
            SettingsScreen(navController, viewModel.themePreferencesRepository, viewModel.dataImporterExporterStrategy)
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
        composable(Routes.MoodEntriesHistory.toString()) {
            MoodEntriesHistoryScreen(navController, viewModel.moodEntriesRepository)
        }
        composable(
            route = "${Routes.ViewMoodEntry}/{date}",
            arguments = listOf(navArgument("date") { type = NavType.StringType })
        ) {
                backStackEntry ->
            ViewMoodEntryScreen(
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