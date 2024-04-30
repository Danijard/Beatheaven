package com.modilebev.beatheaven.ui

import android.app.Activity
import android.content.Context
import android.graphics.Point
import android.util.DisplayMetrics
import android.view.WindowManager
import androidx.core.content.ContextCompat.getSystemService
import com.modilebev.beatheaven.heightPx

class ButtonsGroup {

    class Button {
        val text: String = ""
        val id: Int = 0
        val height: Float = heightPx / 30f
        val width: Float = heightPx / 30f
    }


    var buttons: List<Button> = listOf()


}