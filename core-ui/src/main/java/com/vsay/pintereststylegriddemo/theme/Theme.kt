package com.vsay.pintereststylegriddemo.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

// Assuming these colors are defined in your Color.kt and are still relevant for M3
// You might need to adjust these or use the M3 Material Theme Builder for a new palette.
private val DarkColorScheme = darkColorScheme(
    primary = Purple200,
    primaryContainer = Purple700, // Mapped from primaryVariant
    secondary = Teal200,
    onPrimary = Black,
    onSecondary = Black,
    background = Black,
    surface = Black,
    // onBackground = White, // Ensure these are defined if uncommented
    // onSurface = White,
)

private val LightColorScheme = lightColorScheme(
    primary = Purple500,
    primaryContainer = Purple700, // Mapped from primaryVariant
    secondary = Teal200,
    onPrimary = White,
    onSecondary = Black,
    background = White,
    surface = White,
    // onBackground = Black,
    // onSurface = Black,
)

@Composable
fun PinterestStyleGridDemoTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) {
        DarkColorScheme
    } else {
        LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography, // Assuming Typography.kt is M3 compatible or will be updated
        shapes = Shapes,       // Assuming Shapes.kt is M3 compatible or will be updated
        content = content
    )
}
