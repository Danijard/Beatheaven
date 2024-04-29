package com.modilebev.beatheaven

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import com.modilebev.beatheaven.ui.DefaultTheme
import com.modilebev.beatheaven.ui.DrawBackground
import com.modilebev.beatheaven.ui.DrawButton
import com.modilebev.beatheaven.ui.DrawParams

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        val colorScheme = DefaultTheme
        super.onCreate(savedInstanceState)
        setContent {
            DrawBackground(colorScheme)
            DrawButton(colorScheme)
            DrawParams(colorScheme)
        }
    }
}