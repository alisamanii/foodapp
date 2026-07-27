package com.example.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val LightColorScheme = lightColorScheme(
    primary = FoodgoRedPrimary,
    onPrimary = Color.White,
    primaryContainer = FoodgoRedPrimary,
    onPrimaryContainer = Color.White,
    secondary = FoodgoInkDark,
    onSecondary = Color.White,
    background = FoodgoPageBackground,
    onBackground = FoodgoInkDark,
    surface = FoodgoSurfaceWhite,
    onSurface = FoodgoInkDark,
    surfaceVariant = FoodgoChipInactive,
    onSurfaceVariant = FoodgoTextBody,
    outline = FoodgoBorderLight
)

private val DarkColorScheme = darkColorScheme(
    primary = FoodgoRedPrimary,
    onPrimary = Color.White,
    secondary = FoodgoInkDark,
    background = FoodgoInkDark,
    surface = Color(0xFF1E1E1E),
    onSurface = Color.White
)

@Composable
fun FoodgoTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}

@Composable
fun MyApplicationTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    FoodgoTheme(darkTheme = darkTheme, content = content)
}
