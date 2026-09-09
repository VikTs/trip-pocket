package com.example.trippocket.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val LightColorScheme = lightColorScheme(
    primary = Primary,
    secondary = PrimaryDark,
    background = Background,
    surface = Surface,
    onPrimary = Surface,
    onBackground = TextPrimary,
    onSurface = TextPrimary,
    primaryContainer = PrimaryLight,
    onPrimaryContainer = TextPrimary,
)

private val DarkColorScheme = darkColorScheme(
    primary = Primary,
    secondary = PrimaryDark,
    background = TextPrimary,
    surface = TextPrimary,
    onPrimary = Surface,
    onBackground = Surface,
    onSurface = Surface,
    primaryContainer = PrimaryDark,
    onPrimaryContainer = TextPrimary,
)

@Composable
fun TripPocketTheme(
    darkTheme: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) {
        DarkColorScheme
    } else {
        LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}