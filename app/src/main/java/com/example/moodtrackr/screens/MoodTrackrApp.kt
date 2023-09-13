package com.example.moodtrackr.screens

import android.content.pm.ActivityInfo
import androidx.compose.runtime.Composable
import com.example.moodtrackr.components.Navigation
import com.example.moodtrackr.helpers.SqlDatabaseHelper
import com.example.moodtrackr.utilities.DateUtilities
import com.example.moodtrackr.viewModels.MainViewModel

@Composable
fun MoodTrackrApp(
    viewModel: MainViewModel
) {
    init(viewModel)

    Navigation(viewModel)
}

private fun init(
    viewModel: MainViewModel
){
    DateUtilities.initialize(viewModel.context)
    viewModel.applicationPreferences = viewModel.applicationPreferencesRepository.load()
    viewModel.themePreferences = viewModel.themePreferencesRepository.load()
    viewModel.profile = viewModel.profilePreferencesRepository.load()

    if(!viewModel.applicationPreferences.sqlDatabaseExists) {
        val sqlDatabaseHelper = SqlDatabaseHelper(viewModel.context)
        sqlDatabaseHelper.writableDatabase
    }

    viewModel.mainActivity?.requestedOrientation = if(viewModel.themePreferences.lockOrientationEnabled)
        ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
    else
        ActivityInfo.SCREEN_ORIENTATION_FULL_SENSOR
}