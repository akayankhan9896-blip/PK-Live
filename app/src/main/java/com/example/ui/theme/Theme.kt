package com.example.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val PKLiveColorScheme = darkColorScheme(
    primary = NeonPurple,
    onPrimary = Color.White,
    primaryContainer = DeepPurple,
    onPrimaryContainer = Color.White,
    secondary = NeonPink,
    onSecondary = Color.White,
    secondaryContainer = HotPink,
    onSecondaryContainer = Color.White,
    tertiary = NeonGold,
    onTertiary = Color.Black,
    background = BgDark,
    onBackground = TextPrimary,
    surface = BgCardDark,
    onSurface = TextPrimary,
    surfaceVariant = BgCardLightDark,
    onSurfaceVariant = TextSecondary,
    outline = BorderSubtle
)

@Composable
fun PKLiveTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = PKLiveColorScheme,
        typography = Typography,
        content = content
    )
}
