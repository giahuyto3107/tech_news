package com.example.tech_news.core.theme

import android.app.UiModeManager
import android.content.Context
import android.content.res.Configuration
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext

enum class ThemeMode {
    System,
    Light,
    Dark,
}

internal val TechNewsLightColorScheme = lightColorScheme(
    primary = LightPrimary,
    onPrimary = LightOnPrimary,
    primaryContainer = LightPrimaryContainer,
    onPrimaryContainer = LightOnPrimaryContainer,
    secondary = Color(0xFF4F6357),
    onSecondary = Color(0xFFFFFFFF),
    secondaryContainer = Color(0xFFD2E8D9),
    onSecondaryContainer = Color(0xFF0C1F15),
    tertiary = Color(0xFF3C6472),
    onTertiary = Color(0xFFFFFFFF),
    tertiaryContainer = Color(0xFFC0E9FA),
    onTertiaryContainer = Color(0xFF001F29),
    background = LightSurface,
    onBackground = LightOnSurface,
    surface = LightSurface,
    onSurface = LightOnSurface,
    surfaceVariant = LightSurfaceContainerHigh,
    onSurfaceVariant = LightOnSurfaceVariant,
    surfaceContainerLow = LightSurfaceContainerLow,
    surfaceContainerHigh = LightSurfaceContainerHigh,
    surfaceContainerHighest = LightSurfaceContainerHighest,
    outline = LightOutline,
    outlineVariant = LightOutlineVariant,
    error = LightError,
    errorContainer = LightErrorContainer,
    onErrorContainer = LightOnErrorContainer,
)

internal val TechNewsDarkColorScheme = darkColorScheme(
    primary = DarkPrimary,
    onPrimary = DarkOnPrimary,
    primaryContainer = DarkPrimaryContainer,
    onPrimaryContainer = DarkOnPrimaryContainer,
    secondary = Color(0xFFB6CCBD),
    onSecondary = Color(0xFF22382B),
    secondaryContainer = Color(0xFF384F40),
    onSecondaryContainer = Color(0xFFD2E8D9),
    tertiary = Color(0xFFA5CDDD),
    onTertiary = Color(0xFF073542),
    tertiaryContainer = Color(0xFF234C59),
    onTertiaryContainer = Color(0xFFC0E9FA),
    background = DarkSurface,
    onBackground = DarkOnSurface,
    surface = DarkSurface,
    onSurface = DarkOnSurface,
    surfaceVariant = DarkSurfaceContainerHigh,
    onSurfaceVariant = DarkOnSurfaceVariant,
    surfaceContainerLow = DarkSurfaceContainerLow,
    surfaceContainerHigh = DarkSurfaceContainerHigh,
    surfaceContainerHighest = DarkSurfaceContainerHighest,
    outline = DarkOutline,
    outlineVariant = DarkOutlineVariant,
    error = DarkError,
    errorContainer = DarkErrorContainer,
    onErrorContainer = DarkOnErrorContainer,
)

internal val TechNewsHighContrastLightColorScheme = TechNewsLightColorScheme.copy(
    primary = Color(0xFF0D3727),
    onPrimary = Color.White,
    onSurface = Color.Black,
    onSurfaceVariant = Color(0xFF252A27),
    outline = Color(0xFF3F4741),
    outlineVariant = Color(0xFF626A64),
)

internal val TechNewsHighContrastDarkColorScheme = TechNewsDarkColorScheme.copy(
    primary = Color(0xFFD5F8E3),
    onPrimary = Color(0xFF002115),
    onSurface = Color.White,
    onSurfaceVariant = Color(0xFFE8F0EA),
    outline = Color(0xFFCBD4CD),
    outlineVariant = Color(0xFF9CA59E),
)

@Composable
fun Tech_newsTheme(
    themeMode: ThemeMode = ThemeMode.System,
    dynamicColor: Boolean = false,
    highContrast: Boolean = isSystemHighContrastEnabled(),
    content: @Composable () -> Unit
) {
    val context = LocalContext.current
    val configuration = LocalConfiguration.current

    val darkTheme = when (themeMode) {
        ThemeMode.System -> isSystemInDarkTheme()
        ThemeMode.Light -> false
        ThemeMode.Dark -> true
    }

    val colorScheme = when {
        highContrast && darkTheme -> TechNewsHighContrastDarkColorScheme
        highContrast -> TechNewsHighContrastLightColorScheme
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S && darkTheme ->
            dynamicDarkColorScheme(context)
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S ->
            dynamicLightColorScheme(context)
        darkTheme -> TechNewsDarkColorScheme
        else -> TechNewsLightColorScheme
    }

    val dimensions = if (configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
        PhoneLandscapeDimens
    } else {
        CompactDimens
    }

    CompositionLocalProvider(LocalTechNewsDimens provides dimensions) {
        MaterialTheme(
            colorScheme = colorScheme,
            typography = TechNewsTypography,
            content = content
        )
    }
}

@Composable
private fun isSystemHighContrastEnabled(): Boolean {
    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.UPSIDE_DOWN_CAKE) return false
    val uiModeManager = LocalContext.current.getSystemService(Context.UI_MODE_SERVICE) as? UiModeManager
    return (uiModeManager?.contrast ?: 0f) >= 0.5f
}