package com.modilebev.beatheaven.ui

import androidx.compose.ui.graphics.Color

class Themes (
    BackgroundColor: Color,
    PrimaryColor: Color,
    SecondaryColor: Color,
    MainButtonGradientColor: Color,
) {
    val BackgroundColor = BackgroundColor
    val PrimaryColor = PrimaryColor
    val SecondaryColor = SecondaryColor
    val MainButtonGradientColors = listOf(MainButtonGradientColor, PrimaryColor)
    val MainButtonSoftLightGradientColors = listOf(MainButtonGradientColor.copy(alpha = 0.33f), Color.White.copy(alpha = 0f))
}

val DefaultTheme = Themes(
    BackgroundColor = Color(0xFF051622),
    PrimaryColor = Color(0xFF1A9F96),
    SecondaryColor = Color(0xFFDEB992),
    MainButtonGradientColor = Color(0xFF5AB758)
)

