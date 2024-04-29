package com.modilebev.beatheaven.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier


@Composable
fun DrawBackground(colorScheme: Themes) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = colorScheme.BackgroundColor)
    )
}
