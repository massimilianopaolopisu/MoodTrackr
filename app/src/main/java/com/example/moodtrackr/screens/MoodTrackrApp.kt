package com.example.moodtrackr.screens

import android.content.Context
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import com.example.moodtrackr.components.Navigation
import com.example.moodtrackr.enums.ThemeMode
import com.example.moodtrackr.helpers.SqlDatabaseHelper
import com.example.moodtrackr.repositories.interfaces.IApplicationPreferencesRepository
import com.example.moodtrackr.utilities.DateUtilities
import com.example.moodtrackr.viewModels.MainViewModel

@Composable
fun MoodTrackrApp(
    context: Context,
    viewModel: MainViewModel
) {
    init(context, viewModel.applicationPreferencesRepository)

    val themePreferences = viewModel.themePreferencesRepository.load()

    viewModel.darkModeEnabled = when (themePreferences.themeMode) {
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

    viewModel.dynamicColorsEnabled = themePreferences.dynamicColorsEnabled
    viewModel.lockOrientationEnabled = themePreferences.lockOrientationEnabled

    Navigation(viewModel)
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