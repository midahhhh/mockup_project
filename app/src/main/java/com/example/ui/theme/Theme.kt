package com.example.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val LightColorScheme = lightColorScheme(
    primary = ClinicalPrimary,
    onPrimary = ClinicalOnPrimary,
    primaryContainer = ClinicalPrimaryContainer,
    onPrimaryContainer = ClinicalOnPrimaryContainer,
    secondary = ClinicalSecondary,
    onSecondary = ClinicalOnSecondary,
    secondaryContainer = ClinicalSecondaryContainer,
    onSecondaryContainer = ClinicalOnSecondaryContainer,
    tertiary = ClinicalTertiary,
    onTertiary = ClinicalOnTertiary,
    tertiaryContainer = ClinicalTertiaryContainer,
    onTertiaryContainer = ClinicalOnTertiaryContainer,
    error = ClinicalError,
    onError = ClinicalOnError,
    errorContainer = ClinicalErrorContainer,
    onErrorContainer = ClinicalOnErrorContainer,
    background = ClinicalBackground,
    onBackground = ClinicalOnBackground,
    surface = ClinicalSurface,
    onSurface = ClinicalOnSurface,
    surfaceVariant = ClinicalSurfaceContainerHighest,
    onSurfaceVariant = ClinicalOnSurfaceVariant,
    outline = ClinicalOutline,
    outlineVariant = ClinicalOutlineVariant,
    inverseSurface = ClinicalInverseSurface,
    inverseOnSurface = ClinicalInverseOnSurface,
    inversePrimary = ClinicalPrimaryFixedDim,
    surfaceTint = ClinicalPrimary
)

private val DarkColorScheme = darkColorScheme(
    primary = ClinicalPrimaryFixedDim,
    onPrimary = ClinicalOnPrimaryFixed,
    primaryContainer = ClinicalPrimary,
    onPrimaryContainer = ClinicalOnPrimaryContainer,
    secondary = ClinicalSecondaryFixedDim,
    onSecondary = ClinicalOnSecondaryFixed,
    secondaryContainer = ClinicalSecondary,
    onSecondaryContainer = ClinicalOnSecondaryContainer,
    tertiary = ClinicalTertiaryFixedDim,
    onTertiary = ClinicalOnTertiaryFixed,
    tertiaryContainer = ClinicalTertiary,
    onTertiaryContainer = ClinicalOnTertiaryContainer,
    error = ClinicalError,
    onError = ClinicalOnError,
    errorContainer = ClinicalErrorContainer,
    onErrorContainer = ClinicalOnErrorContainer,
    background = ClinicalInverseSurface,
    onBackground = ClinicalInverseOnSurface,
    surface = ClinicalInverseSurface,
    onSurface = ClinicalInverseOnSurface,
    surfaceVariant = ClinicalSecondary,
    onSurfaceVariant = ClinicalSurfaceContainerHigh,
    outline = ClinicalOutlineVariant,
    outlineVariant = ClinicalOutline
)

@Composable
fun MyApplicationTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false, // Preserve brand clinical palette
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
