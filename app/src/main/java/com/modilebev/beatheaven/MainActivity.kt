package com.modilebev.beatheaven

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.modilebev.beatheaven.ui.CreateButton
import com.modilebev.beatheaven.ui.DefaultTheme
import com.modilebev.beatheaven.ui.DrawBackground
import com.modilebev.beatheaven.ui.DrawParams
import com.modilebev.beatheaven.ui.Themes

class MainActivity : ComponentActivity() {
    companion object {
        var screenWidth: Float = 0f
        var screenHeight: Float = 0f
        var screenDensity: Float = 0f
        var colorScheme: Themes = DefaultTheme
    }
    override fun onCreate(savedInstanceState: Bundle?) {

        screenWidth = resources.displayMetrics.widthPixels.toFloat()
        screenHeight = resources.displayMetrics.heightPixels.toFloat()

        super.onCreate(savedInstanceState)
        setContent {
            DrawBackground()
            CreateButton()
            DrawParams()
        }
    }
}

val widthPx: Float = MainActivity.screenWidth
val heightPx: Float = MainActivity.screenHeight
val density: Float = MainActivity.screenDensity
val colorScheme: Themes = MainActivity.colorScheme
