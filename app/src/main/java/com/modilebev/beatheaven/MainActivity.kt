package com.modilebev.beatheaven

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.modilebev.beatheaven.ui.DefaultTheme
import com.modilebev.beatheaven.ui.DrawBackground
import com.modilebev.beatheaven.ui.DrawButton
import com.modilebev.beatheaven.ui.DrawParams

class MainActivity : ComponentActivity() {
    companion object {
        var screenWidth: Float = 0f
        var screenHeight: Float = 0f
    }
    override fun onCreate(savedInstanceState: Bundle?) {

        screenWidth = resources.displayMetrics.widthPixels.toFloat()
        screenHeight = resources.displayMetrics.heightPixels.toFloat()

        val colorScheme = DefaultTheme
        super.onCreate(savedInstanceState)
        setContent {
            DrawBackground(colorScheme)
            DrawButton(colorScheme)
            DrawParams(colorScheme)
        }
    }
}

val widthPx: Float = MainActivity.screenWidth
val heightPx: Float = MainActivity.screenHeight
