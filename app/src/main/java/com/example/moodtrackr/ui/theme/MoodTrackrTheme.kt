package com.example.moodtrackr.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
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

private val _darkColorScheme = darkColorScheme(
    primary = Color.Black,
    onPrimary = Color.White,
    primaryContainer = Color.DarkGray,
    onPrimaryContainer = Color.White,
    inversePrimary = Color.White,
    secondary = Color.DarkGray,
    onSecondary = Color.White,
    secondaryContainer = Color.Black,
    onSecondaryContainer = Color.White,
    tertiary = Color.Gray,
    onTertiary = Color.White,
    tertiaryContainer = Color.Black,
    onTertiaryContainer = Color.White,
    background = Color.DarkGray,
    onBackground = Color.White,
    surface = Color.DarkGray,
    onSurface = Color.White,
    surfaceVariant = Color.Gray,
    onSurfaceVariant = Color.White,
    surfaceTint = Color.Gray,
    inverseSurface = Color.White,
    inverseOnSurface = Color.Black,
    error = Color.Red,
    onError = Color.White,
    errorContainer = Color.Black,
    onErrorContainer = Color.Red,
    outline = Color.White,
    outlineVariant = Color.Gray,
    scrim = Color.Black.copy(alpha = 0.6f),
    surfaceBright = Color.LightGray,
    surfaceContainer = Color.Black,
    surfaceContainerHigh = Color.DarkGray,
    surfaceContainerHighest = Color.Black,
    surfaceContainerLow = Color.Black,
    surfaceContainerLowest = Color.Black,
    surfaceDim = Color.Black.copy(alpha = 0.4f)
)

private val _lightColorScheme = lightColorScheme(
        primary = Purple40,
        secondary = PurpleGrey40,
        tertiary = Pink40
)

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

        (viewModel.themePreferences.darkMode || isSystemInDarkTheme()) -> _darkColorScheme
        else -> _lightColorScheme
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