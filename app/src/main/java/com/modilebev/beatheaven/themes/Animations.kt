package com.modilebev.beatheaven.themes

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember

@Composable
fun getAnimatedAlpha(): Float { // fadeIn Animation
    val alphaAnim = remember { Animatable(initialValue = 0f) }
    LaunchedEffect(key1 = true) {
        alphaAnim.animateTo(
            targetValue = 1f,
            animationSpec = tween(
                durationMillis = 2000,
                delayMillis = 0
            )
        )
    }
    return alphaAnim.value
}

@Composable
fun getAnimatedScale(): Float { //sizeIn Animation
    val scaleAnim = remember { Animatable(initialValue = 0f) }
    LaunchedEffect(key1 = true) {
        scaleAnim.animateTo(
            targetValue = 1f,
            animationSpec = tween(
                durationMillis = 500,
                delayMillis = 100
            )
        )
    }
    return scaleAnim.value
}

fun animateButton() {

}
