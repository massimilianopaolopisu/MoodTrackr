package com.example.moodtrackr.ui.theme

import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.ui.graphics.Color

val Purple40 = Color(0xFF6650a4)
val PurpleGrey40 = Color(0xFF625b71)
val Pink40 = Color(0xFF7D5260)

val darkColorScheme = darkColorScheme(
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

val lightColorScheme = lightColorScheme(
    primary = Purple40,
    secondary = PurpleGrey40,
    tertiary = Pink40
)