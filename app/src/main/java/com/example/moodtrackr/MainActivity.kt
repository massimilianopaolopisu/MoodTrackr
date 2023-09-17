package com.example.moodtrackr

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
        viewModel.mainActivity = this

        window.decorView.post {
            window.setBackgroundDrawableResource(android.R.color.transparent)
        }

        setContent {
            MoodTrackrApp(viewModel)
        }
    }
}