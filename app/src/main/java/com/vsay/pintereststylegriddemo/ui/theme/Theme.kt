package com.vsay.pintereststylegriddemo.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorPalette = darkColors(
    primary = Purple200,
    primaryVariant = Purple700,
    secondary = Teal200,
    onPrimary = Black,
    onSecondary = Black,
    // Other default colors to override
    background = Black,
    surface = Black,
    // onBackground = Color.White,
    // onSurface = Color.White,
)

private val LightColorPalette = lightColors(
    primary = Purple500,
    primaryVariant = Purple700,
    secondary = Teal200,
    onPrimary = White,
    onSecondary = Black,
    // Other default colors to override
    background = White,
    surface = White,
    // onBackground = Color.Black,
    // onSurface = Color.Black,
)

@Composable
fun PinterestStyleGridDemoTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}
