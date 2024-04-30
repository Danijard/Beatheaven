package com.modilebev.beatheaven.ui

import android.app.Activity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat


@Composable
fun DrawBackground(colorScheme: Themes) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = colorScheme.BackgroundColor)
    )
    val view = LocalView.current
    SideEffect {
        val window = (view.context as Activity).window
        window.statusBarColor = colorScheme.BackgroundColor.toArgb()
    }
}
