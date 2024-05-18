package com.modilebev.beatheaven

import android.Manifest
import android.app.Application
import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.modilebev.beatheaven.ui.CreateButton
import com.modilebev.beatheaven.ui.DefaultTheme
import com.modilebev.beatheaven.ui.DrawBackground
import com.modilebev.beatheaven.ui.DrawParams
import com.modilebev.beatheaven.ui.Themes
import com.modilebev.beatheaven.client.tryRecording

class MainActivity : ComponentActivity() {
    companion object {
        var screenWidth: Float = 0f
        var screenHeight: Float = 0f
        var screenDensity: Float = 0f
        var colorScheme: Themes = DefaultTheme
    }

    private var isRecording = false

    override fun onCreate(savedInstanceState: Bundle?) {
        screenWidth = resources.displayMetrics.widthPixels.toFloat()
        screenHeight = resources.displayMetrics.heightPixels.toFloat()
        screenDensity = resources.displayMetrics.density

        super.onCreate(savedInstanceState)
        setContent {
            DrawBackground()
            CreateButton(this)
            DrawParams()
        }
    }

    fun toggleRecording() {
        if (checkPermissions(this)) {
            tryRecording(this)
            isRecording = !isRecording
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        // Дополнительная логика при уничтожении активности (если необходимо)
    }
}

fun checkPermissions(activity: MainActivity): Boolean {
    Log.d("mytag", "Checking permissions")
    val permission = ContextCompat.checkSelfPermission(
        Beatheaven.applicationContext(),
        Manifest.permission.RECORD_AUDIO
    )
    if (permission != PackageManager.PERMISSION_GRANTED) {
        Log.d("mytag", "Permission not granted")
        ActivityCompat.requestPermissions(
            activity,
            arrayOf(Manifest.permission.RECORD_AUDIO),
            REQUEST_RECORD_AUDIO_PERMISSION
        )
        return false
    } else {
        Log.d("mytag", "Permission granted")
        return true
    }
}

class Beatheaven : Application() {
    init {
        instance = this
    }

    companion object {
        private var instance: Beatheaven? = null

        fun applicationContext(): Context {
            return instance!!.applicationContext
        }
    }
}

private const val REQUEST_RECORD_AUDIO_PERMISSION = 200

val widthPx: Float = MainActivity.screenWidth
val heightPx: Float = MainActivity.screenHeight
val density: Float = MainActivity.screenDensity
val colorScheme: Themes = MainActivity.colorScheme
