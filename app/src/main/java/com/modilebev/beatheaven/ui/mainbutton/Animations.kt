package com.modilebev.beatheaven.ui.mainbutton

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember

@Composable
fun getAnimatedAlpha(): Float { //!fadeIn Animation
    val alphaState = remember { Animatable(initialValue = 0f) }
    LaunchedEffect(key1 = true) {
        alphaState.animateTo(
            targetValue = 1f,
            animationSpec = tween(
                durationMillis = 2000,
                delayMillis = 0
            )
        )
    }
    return alphaState.value
}

@Composable
fun getAnimatedScale(): Float { //!sizeIn Animation
    val scaleState = remember { Animatable(initialValue = 0f) }
    LaunchedEffect(key1 = true) {
        scaleState.animateTo(
            targetValue = 1f,
            animationSpec = tween(
                durationMillis = 500,
                delayMillis = 100,
            )
        )
    }
    return scaleState.value
}