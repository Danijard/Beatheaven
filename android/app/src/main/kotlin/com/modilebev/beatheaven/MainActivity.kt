package com.modilebev.beatheaven

import android.util.Log
import io.flutter.embedding.android.FlutterActivity
import io.flutter.embedding.engine.FlutterEngine
import io.flutter.plugin.common.MethodChannel

class MainActivity: FlutterActivity() {
    private val CHANNEL = "com.modilebev.beatheaven/Kotlin"

    override fun configureFlutterEngine(flutterEngine: FlutterEngine) {
        super.configureFlutterEngine(flutterEngine)
        methodChannel = MethodChannel(flutterEngine.dartExecutor.binaryMessenger, CHANNEL)

        methodChannel.setMethodCallHandler { call, result ->
            when (call.method) {
                "tryRecording" -> {
                    if (!checkRecordAudioPermission(this)) {
                        Log.d("mytag", "PERMISSION_DENIED")
                        requestRecordAudioPermission(this)
                        result.success(null)
                    } else {
                        Log.d("mytag", "tryRecording")
                        result.success(recordingAndAsking(this))
                    }
                }

                else -> {
                    result.notImplemented()
                }
            }
        }
    }
}

