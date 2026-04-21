package com.desapp.navegacion

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily

@Composable
fun AppTheme(content: @Composable () -> Unit) {
    val sfFontFamily = FontFamily.SansSerif
    val typography = Typography(
        displayLarge = TextStyle(fontFamily = sfFontFamily),
        displayMedium = TextStyle(fontFamily = sfFontFamily),
        displaySmall = TextStyle(fontFamily = sfFontFamily),
        headlineLarge = TextStyle(fontFamily = sfFontFamily),
        headlineMedium = TextStyle(fontFamily = sfFontFamily),
        headlineSmall = TextStyle(fontFamily = sfFontFamily),
        titleLarge = TextStyle(fontFamily = sfFontFamily),
        titleMedium = TextStyle(fontFamily = sfFontFamily),
        titleSmall = TextStyle(fontFamily = sfFontFamily),
        bodyLarge = TextStyle(fontFamily = sfFontFamily),
        bodyMedium = TextStyle(fontFamily = sfFontFamily),
        bodySmall = TextStyle(fontFamily = sfFontFamily),
        labelLarge = TextStyle(fontFamily = sfFontFamily),
        labelMedium = TextStyle(fontFamily = sfFontFamily),
        labelSmall = TextStyle(fontFamily = sfFontFamily)
    )
    MaterialTheme(
        colorScheme = darkColorScheme(),
        typography = typography,
        content = content
    )
}
