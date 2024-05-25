package com.modilebev.beatheaven

import android.annotation.SuppressLint
import android.media.AudioFormat
import android.media.AudioRecord
import android.media.MediaRecorder
import android.util.Log
import com.microsoft.signalr.HubConnectionBuilder
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.lang.Thread.sleep
import java.util.Base64

fun recordingAndAsking(activity: MainActivity): List<String> {
    Log.d("SignalR", "tryRecording")

    val apiKey = "7p8AAB2e1db2"
    val uri = "ws://77.91.70.64:9432/api/v1/recognize"
    var output = listOf("")

    val sampleRate = 44100
    val channelConfig = AudioFormat.CHANNEL_IN_MONO
    val audioFormat = AudioFormat.ENCODING_PCM_16BIT
    val bufferSize = AudioRecord.getMinBufferSize(sampleRate, channelConfig, audioFormat)

    val hubConnection = HubConnectionBuilder.create(uri)
        .withHeader("X-API-Key", apiKey)
        .build()

    Log.d("SignalR", "open")
    CoroutineScope(Dispatchers.IO).launch {
        try {
            hubConnection.start().blockingAwait()

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
                    delay(100)
                }
            }

            recordingJob.join()

            audioRecord.stop()
            audioRecord.release()
            Log.d("SignalR", "Recording stopped")

            hubConnection.on("RecognizedResults", { data: String ->
                Log.d("SignalR", "RecognizedResults1")
                output = dataHandleToFirstTrackInfo(data)
                Log.d("SignalR", "RecognizedResults2")
            }, String::class.java)

            delay(100)
            try {
                hubConnection.invoke("GetRecognizedResult").blockingAwait()
            } catch (e: Exception) {
                Log.e("SignalR", "Error getting recognized result", e)
            }
            delay(100)

            Log.d("SignalR", "close")
            hubConnection.stop().blockingAwait()
        } catch (e: Exception) {
            Log.e("SignalR", "Error with SignalR connection", e)
        }
    }
    sleep(6000)
    Log.d("SignalR", "output")
    return output
}

fun sendFoundSound(sound: List<String>) {
    Log.d("SignalR", "sendFoundSound1")
    try {
        methodChannel.invokeMethod("foundSound", sound)
    } catch (e: Exception) {
        Log.e("DartInvoke", "Error sending found sound", e)
    }
    Log.d("SignalR", "sendFoundSound2")
}
