package com.modilebev.beatheaven.ui

import androidx.compose.ui.graphics.Color

class Themes constructor(
    BackgroundColor: Color,
    MainButtonGradientColors: List<Color>,
    MainButtonSoftLightGradientColors: List<Color>) {

    val BackgroundColor = Color(0xFF051622)

    val MainButtonGradientColors = listOf(Color(0xFF5AB758), Color(0xFF1A9F96))

    val MainButtonSoftLightGradientColors = listOf(Color(0xFF5AB758).copy(alpha = 0.25f), Color.White.copy(alpha = 0f))
}

val DefaultTheme = Themes(
    BackgroundColor = Color(0xFF051622),
    MainButtonGradientColors = listOf(Color(0xFF5AB758), Color(0xFF1A9F96)),
    MainButtonSoftLightGradientColors = listOf(Color(0xFF5AB758).copy(alpha = 0.25f), Color.White.copy(alpha = 0f)),
)

