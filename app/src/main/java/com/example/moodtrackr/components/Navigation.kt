package com.example.moodtrackr.components

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.moodtrackr.enums.Routes
import com.example.moodtrackr.screens.EditMoodEntryScreen
import com.example.moodtrackr.screens.EditProfileScreen
import com.example.moodtrackr.screens.HomeScreen
import com.example.moodtrackr.screens.MoodEntriesHistoryScreen
import com.example.moodtrackr.screens.SettingsScreen
import com.example.moodtrackr.screens.StatisticsScreen
import com.example.moodtrackr.screens.ViewMoodEntryScreen
import com.example.moodtrackr.utilities.DateUtilities
import com.example.moodtrackr.viewModels.MainViewModel
import java.time.LocalDate

@Composable
fun Navigation(viewModel: MainViewModel) {
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
            SettingsScreen(navController, viewModel)
        }
        composable(
            route = "${Routes.EditMoodEntry}/{date}",
            arguments = listOf(navArgument("date") { type = NavType.StringType })
        ) {
                backStackEntry ->
            EditMoodEntryScreen(
                navController,
                viewModel.moodEntriesRepository,
                backStackEntry.arguments?.getString("date")?: DateUtilities.getStringDateFromLocalDate(
                    LocalDate.now()))
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
                backStackEntry.arguments?.getString("date")?: DateUtilities.getStringDateFromLocalDate(
                    LocalDate.now()))
        }
        composable(Routes.Statistics.toString()) {
            StatisticsScreen(navController, viewModel.moodEntriesRepository)
        }
    }
}