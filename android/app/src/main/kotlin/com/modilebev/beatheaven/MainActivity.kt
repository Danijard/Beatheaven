package com.modilebev.beatheaven

import android.Manifest
import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.content.pm.PackageManager
import android.media.AudioFormat
import android.media.AudioRecord
import android.media.MediaRecorder
import android.os.Bundle
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.microsoft.signalr.HubConnectionBuilder
import io.flutter.embedding.android.FlutterActivity
import io.flutter.embedding.engine.FlutterEngine
import io.flutter.plugin.common.MethodChannel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.Base64


private const val REQUEST_RECORD_AUDIO_PERMISSION = 200

class MainActivity: FlutterActivity() {
    private val CHANNEL = "com.modilebev.beatheaven/Kotlin"

    override fun configureFlutterEngine(flutterEngine: FlutterEngine) {
        super.configureFlutterEngine(flutterEngine)
        MethodChannel(flutterEngine.dartExecutor.binaryMessenger, CHANNEL).setMethodCallHandler {
            // Note: this method is invoked on the main thread.
                call, result ->
            if (call.method == "tryRecording") {
                if (!checkPermissions(this)) {
                    Log.d("mytag", "PERMISSION_DENIED")
                    ActivityCompat.requestPermissions(
                        this,
                        arrayOf(Manifest.permission.RECORD_AUDIO),
                        REQUEST_RECORD_AUDIO_PERMISSION
                    )
                    result.success(null)
                } else {
                    Log.d("mytag", "tryRecording")
                    tryRecording(this)
                    result.success(null)
                }
            }  else  {
                Log.d("mytag", "PERMISSION_DENIED")
                result.notImplemented()
            }
        }
    }
}



fun tryRecording(activity: MainActivity) {
    Log.d("SignalR", "tryRecording")

    val apiKey = "7p8AAB2e1db2"
    val uri = "ws://77.91.70.64:9432/api/v1/recognize"

    val hubConnection = HubConnectionBuilder.create(uri)
        .withHeader("X-API-Key", apiKey)
        .build()

    Log.d("SignalR", "open")
    CoroutineScope(Dispatchers.IO).launch {
        try {
            hubConnection.start().blockingAwait()

            val sampleRate = 44100
            val channelConfig = AudioFormat.CHANNEL_IN_MONO
            val audioFormat = AudioFormat.ENCODING_PCM_16BIT
            val bufferSize = AudioRecord.getMinBufferSize(sampleRate, channelConfig, audioFormat)
            Log.d("AAAAA", "$bufferSize")

            if (bufferSize == AudioRecord.ERROR || bufferSize == AudioRecord.ERROR_BAD_VALUE) {
                Log.e("AudioRecord", "Error creating AudioRecord")
                return@launch
            }

            @SuppressLint("MissingPermission")
            val audioRecord = AudioRecord(
                MediaRecorder.AudioSource.MIC,
                sampleRate,
                channelConfig,
                audioFormat,
                bufferSize
            )

            audioRecord.startRecording()
            Log.d("SignalR", "Recording started")

            val recordingJob = launch {
                val audioData = ByteArray(bufferSize)
                for (i in 0 until 40) {
                    val readBytes = audioRecord.read(audioData, 0, audioData.size)
                    if (readBytes > 0) {
                        val dataChunk = audioData.copyOf(readBytes)
                        val encodedString = Base64.getEncoder().encodeToString(dataChunk)
                        Log.d("SignalR", "send bytes")
                        Log.d("SignalR", encodedString)
                        try {
                            hubConnection.invoke("SendBytes", encodedString)
                        } catch (e: Exception) {
                            Log.e("SignalR", "Error sending bytes", e)
                        }
                    }
                    delay(1000)
                }
            }

            recordingJob.join()

            audioRecord.stop()
            audioRecord.release()
            Log.d("SignalR", "Recording stopped")

            hubConnection.on("RecognizedResults", { data: String ->
                run {
                    Log.d("SignalR", data)
                }
            }, String::class.java)

            delay(1000)
            try {
                hubConnection.invoke("GetRecognizedResult").blockingAwait()
            } catch (e: Exception) {
                Log.e("SignalR", "Error getting recognized result", e)
            }
            delay(1000)

            Log.d("SignalR", "close")
            hubConnection.stop().blockingAwait()
        } catch (e: Exception) {
            Log.e("SignalR", "Error with SignalR connection", e)
        }
    }
}

fun checkPermissions(activity: MainActivity): Boolean {
    val permission = ContextCompat.checkSelfPermission(
        activity,
        Manifest.permission.RECORD_AUDIO
    )
    return permission == PackageManager.PERMISSION_GRANTED
}