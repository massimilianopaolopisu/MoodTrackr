package com.example.moodtrackr.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsControllerCompat
import com.example.moodtrackr.viewModels.MainViewModel

@Composable
fun MoodTrackrTheme(
        viewModel: MainViewModel,
        content: @Composable () -> Unit
) {
    val context = LocalContext.current

    val colorScheme = when {
        viewModel.themePreferences.dynamicColorsEnabled && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            if (viewModel.themePreferences.darkMode || isSystemInDarkTheme()) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        (viewModel.themePreferences.darkMode || isSystemInDarkTheme()) -> darkColorScheme
        else -> lightColorScheme
    }
    val view = LocalView.current

    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.primary.toArgb()

            val controller = WindowCompat.getInsetsController(window, view)
            controller.systemBarsBehavior =
                WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
            controller.isAppearanceLightStatusBars = !viewModel.themePreferences.darkMode

            window.navigationBarColor = colorScheme.primary.toArgb()
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                window.navigationBarDividerColor = Color.Transparent.toArgb()
            }
        }
    }

    MaterialTheme(
            colorScheme = colorScheme,
            typography = Typography,
            content = {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colorScheme.background)
                ) {
                    content()
                }
            }
    )
}