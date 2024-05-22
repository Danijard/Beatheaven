package com.modilebev.beatheaven

import android.util.Log
import androidx.annotation.NonNull
import io.flutter.embedding.android.FlutterActivity
import io.flutter.embedding.engine.FlutterEngine
import io.flutter.plugin.common.MethodChannel
import android.annotation.SuppressLint
import android.media.AudioFormat
import android.media.AudioRecord
import android.media.MediaRecorder
import com.microsoft.signalr.HubConnectionBuilder
import kotlinx.coroutines.*
import java.util.Base64

class MainActivity: FlutterActivity() {
    private val CHANNEL = "com.modilebev.beatheaven/Kotlin"

    override fun configureFlutterEngine(@NonNull flutterEngine: FlutterEngine) {
        super.configureFlutterEngine(flutterEngine)
        MethodChannel(flutterEngine.dartExecutor.binaryMessenger, CHANNEL).setMethodCallHandler {
            // Note: this method is invoked on the main thread.
                call, result ->
            if (call.method == "tryRecording") {
                Log.d("SignalR", "tryRecording")
                tryRecording(this)
                result.success(null)
            } else {
                Log.d("SignalR", "NotImplemented")
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
        hubConnection.start().blockingAwait()

        // Параметры для записи аудио
        val sampleRate = 44100
        val channelConfig = AudioFormat.CHANNEL_IN_MONO
        val audioFormat = AudioFormat.ENCODING_PCM_16BIT
        val bufferSize = AudioRecord.getMinBufferSize(sampleRate, channelConfig, audioFormat)
        Log.d("AAAAA", "$bufferSize")
        // Создание AudioRecord
        @SuppressLint("MissingPermission")
        val audioRecord = AudioRecord(
            MediaRecorder.AudioSource.MIC,
            sampleRate,
            channelConfig,
            audioFormat,
            bufferSize
        )

        // Начало записи
        audioRecord.startRecording()
        Log.d("SignalR", "Recording started")

        // Короутина для записи аудио
        val recordingJob = launch {
            val recordingDuration = 10 // Записываем 10 секунд
            val audioData = ByteArray(bufferSize)
            //recordingDuration * sampleRate / bufferSize = 125
            for (i in 0 until 40) {
                val readBytes = audioRecord.read(audioData, 0, audioData.size)
                if (readBytes > 0) {
                    val dataChunk = audioData.copyOf(readBytes)
                    val encodedString = Base64.getEncoder().encodeToString(dataChunk)
                    Log.d("SignalR", "send bytes")
                    Log.d("SignalR", encodedString)
                    hubConnection.invoke("SendBytes", encodedString)
                }
                delay(1000) // Отправляем каждые 1 секунду
            }
        }

        // Ожидание завершения записи
        recordingJob.join()

        // Остановка записи
        audioRecord.stop()
        audioRecord.release()
        Log.d("SignalR", "Recording stopped")

        hubConnection.on("RecognizedResults", { data: String ->
            run {
                Log.d("SignalR", data)
            }
        }, String::class.java)

        delay(1000)
        hubConnection.invoke("GetRecognizedResult").blockingAwait()
        delay(1000)

        Log.d("SignalR", "close")
        hubConnection.stop().blockingAwait()
    }
}