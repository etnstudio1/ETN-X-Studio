package com.etnstudio.user.ui.theme

import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorScheme = darkColorScheme(
    primary = Color(0xFF00E5FF),
    secondary = Color(0xFF00838F),
    background = Color(0xFF0B0E14),
    surface = Color(0xFF141A26),
    onPrimary = Color.Black,
    onBackground = Color.White
)

@Composable
fun ETNStudioUserTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = DarkColorScheme,
        content = content
    )
}
