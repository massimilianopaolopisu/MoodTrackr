package com.example.moodtrackr

import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.example.moodtrackr.screens.MoodTrackrApp
import com.example.moodtrackr.viewModels.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity @Inject constructor() : ComponentActivity() {
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestedOrientation = getOrientationFromPreferences()

        setContent {
            MoodTrackrApp(viewModel)
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