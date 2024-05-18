/*package com.modilebev.beatheaven.client

import android.annotation.SuppressLint
import android.media.AudioFormat
import android.media.AudioRecord
import android.media.MediaRecorder
import android.util.Log
import com.modilebev.beatheaven.MainActivity
import com.modilebev.beatheaven.checkPermissions
import kotlinx.coroutines.*
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import okio.ByteString
import java.io.IOException


import com.microsoft.signalr.HubConnectionBuilder

private const val sampleRate = 44100
private const val channelConfig = AudioFormat.CHANNEL_IN_MONO
private const val audioFormat = AudioFormat.ENCODING_PCM_16BIT
private val bufferSize = AudioRecord.getMinBufferSize(sampleRate, channelConfig, audioFormat)

private var isRecording = false
private var audioRecord: AudioRecord? = null

private const val restApiUrl = "http://176.124.220.92:9432/api/v1/track"
private const val apiKey = "7p8AAB2e1db2"
private const val wsUrl = "ws://176.124.220.92:9432/api/v1/recognize"
private var webSocket: WebSocket? = null

fun tryRecording(activity: MainActivity) {
    GlobalScope.launch {


        //TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
        fun main() {
            val connection = HubConnectionBuilder
                .create("ws://176.124.220.92:9432/api/v1/recognize")
                .withHeaders(mapOf("X-API-KEY" to "7p8AAB2e1db2"))
                .build()

            connection.start().blockingAwait()

            connection.on("Ping", {message: String ->
                run {
                    println("Message from : $message")
                }
            }, String::class.java)

            connection.invoke(
                "Ping"
            ).blockingAwait()

        }
    }

    if (checkPermissions(activity)) {
        if (!isRecording) {
            @SuppressLint("MissingPermission")
            audioRecord = AudioRecord(
                MediaRecorder.AudioSource.MIC,
                sampleRate,
                channelConfig,
                audioFormat,
                bufferSize
            )

            audioRecord?.startRecording()
            isRecording = true
            val buffer = ByteArray(bufferSize)

            GlobalScope.launch(Dispatchers.IO) {
                try {
                    while (audioRecord?.recordingState == AudioRecord.RECORDSTATE_RECORDING) {
                        val bytesRead = audioRecord?.read(buffer, 0, bufferSize)
                        if (bytesRead != null && bytesRead > 0) {
                            Log.i("AudioRecording", "Audio recorded in PCM format")

                            val requestBody = buffer.toRequestBody("application/octet-stream".toMediaType())
                            val request = Request.Builder()
                                .url(restApiUrl)
                                .addHeader("X-API-Key", apiKey)
                                .post(requestBody)
                                .build()

                            val client = OkHttpClient()
                            client.newCall(request).execute().use { response ->
                                if (!response.isSuccessful) {
                                    Log.e("AudioRecording", "Error occurred while sending data to server: ${response.message}")
                                } else {
                                    Log.i("AudioRecording", "Data successfully sent to server")
                                }
                            }
                        }
                    }
                    isRecording = false
                } catch (e: IOException) {
                    Log.e("AudioRecording", "Error occurred while recording audio or sending data to server", e)
                }
            }
        } else {
            audioRecord?.stop()
            isRecording = false
        }
    }
}

fun connectToWebSocket() {
    val client = OkHttpClient()
    val request = Request.Builder()
        .url(wsUrl)
        .addHeader("X-API-Key", apiKey)
        .build()

    webSocket = client.newWebSocket(request, object : WebSocketListener() {
        override fun onOpen(webSocket: WebSocket, response: okhttp3.Response) {
            Log.i("WebSocket", "WebSocket connection opened")
        }

        override fun onMessage(webSocket: WebSocket, text: String) {
            Log.i("WebSocket", "Received message: $text")
        }

        override fun onMessage(webSocket: WebSocket, bytes: ByteString) {
            Log.i("WebSocket", "Received bytes: ${bytes.size}")
        }

        override fun onFailure(webSocket: WebSocket, t: Throwable, response: okhttp3.Response?) {
            Log.e("WebSocket", "WebSocket connection failed", t)
        }

        override fun onClosed(webSocket: WebSocket, code: Int, reason: String) {
            Log.i("WebSocket", "WebSocket connection closed: $reason")
        }
    })

    client.dispatcher.executorService.shutdown()
}

fun sendAudioDataWebSocket(data: ByteArray) {
    webSocket?.send(ByteString.of(*data))
}

fun closeWebSocket() {
    webSocket?.close(1000, "Client closing connection")
}

fun getRecognizedResults() {
    webSocket?.send("GetRecognizeResult")
}*/

/*package com.modilebev.beatheaven.client

import android.annotation.SuppressLint
import android.media.AudioFormat
import android.media.AudioRecord
import android.media.MediaRecorder
import android.util.Log
import com.microsoft.signalr.HubConnectionBuilder
import com.modilebev.beatheaven.MainActivity
import com.modilebev.beatheaven.checkPermissions
import kotlinx.coroutines.*
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import okio.ByteString
import java.io.IOException

private const val sampleRate = 44100
private const val channelConfig = AudioFormat.CHANNEL_IN_MONO
private const val audioFormat = AudioFormat.ENCODING_PCM_16BIT
private val bufferSize = AudioRecord.getMinBufferSize(sampleRate, channelConfig, audioFormat)

private var isRecording = false
private var audioRecord: AudioRecord? = null

private const val restApiUrl = "http://176.124.220.92:9432/api/v1/track"
private const val apiKey = "7p8AAB2e1db2"
private const val wsUrl = "ws://176.124.220.92:9432/api/v1/recognize"
private var webSocket: WebSocket? = null

fun tryRecording(activity: MainActivity) {
    if (checkPermissions(activity)) {
        GlobalScope.launch {
            pingServer()
        }

        if (!isRecording) {
            @SuppressLint("MissingPermission")
            audioRecord = AudioRecord(
                MediaRecorder.AudioSource.MIC,
                sampleRate,
                channelConfig,
                audioFormat,
                bufferSize
            )

            audioRecord?.startRecording()
            isRecording = true
            val buffer = ByteArray(bufferSize)

            GlobalScope.launch(Dispatchers.IO) {
                try {
                    while (audioRecord?.recordingState == AudioRecord.RECORDSTATE_RECORDING) {
                        val bytesRead = audioRecord?.read(buffer, 0, bufferSize)
                        if (bytesRead != null && bytesRead > 0) {
                            Log.i("AudioRecording", "Audio recorded in PCM format")

                            val requestBody = buffer.toRequestBody("application/octet-stream".toMediaType())
                            val request = Request.Builder()
                                .url(restApiUrl)
                                .addHeader("X-API-Key", apiKey)
                                .post(requestBody)
                                .build()

                            val client = OkHttpClient()
                            client.newCall(request).execute().use { response ->
                                if (!response.isSuccessful) {
                                    Log.e("AudioRecording", "Error occurred while sending data to server: ${response.message}")
                                } else {
                                    Log.i("AudioRecording", "Data successfully sent to server")
                                }
                            }
                        }
                    }
                    isRecording = false
                } catch (e: IOException) {
                    Log.e("AudioRecording", "Error occurred while recording audio or sending data to server", e)
                }
            }
        } else {
            audioRecord?.stop()
            isRecording = false
        }
    }
}

suspend fun pingServer() {
    val connection = HubConnectionBuilder
        .create(wsUrl)
        .withHeaders(mapOf("X-API-KEY" to apiKey))
        .build()

    connection.start().blockingAwait()

    connection.on("Ping", { message: String ->
        run {
            Log.i("Ping", "Message from server: $message")
        }
    }, String::class.java)

    while (true) {
        connection.invoke("Ping").blockingAwait()
        delay(1000) // Пинг каждую секунду
    }
}

fun connectToWebSocket() {
    val client = OkHttpClient()
    val request = Request.Builder()
        .url(wsUrl)
        .addHeader("X-API-Key", apiKey)
        .build()

    webSocket = client.newWebSocket(request, object : WebSocketListener() {
        override fun onOpen(webSocket: WebSocket, response: okhttp3.Response) {
            Log.i("WebSocket", "WebSocket connection opened")
        }

        override fun onMessage(webSocket: WebSocket, text: String) {
            Log.i("WebSocket", "Received message: $text")
        }

        override fun onMessage(webSocket: WebSocket, bytes: ByteString) {
            Log.i("WebSocket", "Received bytes: ${bytes.size}")
        }

        override fun onFailure(webSocket: WebSocket, t: Throwable, response: okhttp3.Response?) {
            Log.e("WebSocket", "WebSocket connection failed", t)
        }

        override fun onClosed(webSocket: WebSocket, code: Int, reason: String) {
            Log.i("WebSocket", "WebSocket connection closed: $reason")
        }
    })

    client.dispatcher.executorService.shutdown()
}

fun sendAudioDataWebSocket(data: ByteArray) {
    webSocket?.send(ByteString.of(*data))
}

fun closeWebSocket() {
    webSocket?.close(1000, "Client closing connection")
}

fun getRecognizedResults() {
    webSocket?.send("GetRecognizeResult")
}*/

/*package com.modilebev.beatheaven.client

import android.annotation.SuppressLint
import android.media.AudioFormat
import android.media.AudioRecord
import android.media.MediaRecorder
import android.util.Log
import com.modilebev.beatheaven.MainActivity
import com.microsoft.signalr.HubConnectionBuilder
import kotlinx.coroutines.*
import okhttp3.*
import okio.ByteString
import okio.ByteString.Companion.encodeUtf8
import okio.ByteString.Companion.toByteString
import java.io.IOException

private const val sampleRate = 44100
private const val channelConfig = AudioFormat.CHANNEL_IN_MONO
private const val audioFormat = AudioFormat.ENCODING_PCM_16BIT
private val bufferSize = AudioRecord.getMinBufferSize(sampleRate, channelConfig, audioFormat)

private const val apiKey = "7p8AAB2e1db2"
private const val wsUrl = "ws://176.124.220.92:9432/api/v1/recognize"
private var webSocket: WebSocket? = null
private var isWebSocketOpen = false
private var isRecording = false
private var audioRecord: AudioRecord? = null

fun tryRecording(activity: MainActivity) {
    if (!isRecording) {
        startRecording()
    } else {
        stopRecording()
    }
}

@SuppressLint("MissingPermission")
private fun startRecording() {
    GlobalScope.launch(Dispatchers.IO) {
        if (!isWebSocketOpen) {
            connectToWebSocket()
            delay(1000) // Даем время для установки соединения
        }

        audioRecord = AudioRecord(
            MediaRecorder.AudioSource.MIC,
            sampleRate,
            channelConfig,
            audioFormat,
            bufferSize
        )

        audioRecord?.startRecording()
        isRecording = true
        val buffer = ByteArray(bufferSize)

        try {
            withTimeoutOrNull(10000) { // Автоматическая остановка через 12 секунд
                pingServer()
                while (isRecording) {
                    val bytesRead = audioRecord?.read(buffer, 0, buffer.size)
                    if (bytesRead != null && bytesRead > 0 && isWebSocketOpen) {
                        val byteString = buffer.toByteString(0, bytesRead)
                        val message = """{"arguments":["$byteString"],"invocationId":"0","target":"SendBytes","type":1}"""
                        webSocket?.send(message)
                        Log.i("AudioRecording", "Audio recorded and sent to server")
                    } else if (!isWebSocketOpen) {
                        Log.e("AudioRecording", "WebSocket is not open, stopping recording")
                        stopRecording()
                    }
                    delay(1000) // Отправка аудио данных каждую секунду
                }
            }
        } catch (e: Exception) {
            Log.e("AudioRecording", "Error occurred while recording audio or sending data to server", e)
        } finally {
            stopRecording() // Остановка записи после истечения времени или ошибки
            requestRecognitionResults()
        }
    }
}

private fun stopRecording() {
    isRecording = false
    audioRecord?.stop()
    audioRecord?.release()
    audioRecord = null
}

private fun connectToWebSocket() {
    if (isWebSocketOpen) {
        Log.w("WebSocket", "WebSocket is already open")
        return
    }

    val client = OkHttpClient()
    val request = Request.Builder()
        .url(wsUrl)
        .addHeader("X-API-Key", apiKey)
        .build()

    webSocket = client.newWebSocket(request, object : WebSocketListener() {
        override fun onOpen(webSocket: WebSocket, response: okhttp3.Response) {
            Log.i("WebSocket", "WebSocket connection opened")
            isWebSocketOpen = true
            GlobalScope.launch {
                while (isWebSocketOpen) {
                    sendHandshakeMessage(webSocket)
                    delay(15000) // Отправка handshake каждые 15 секунд
                }
            }
        }

        override fun onMessage(webSocket: WebSocket, text: String) {
            Log.i("WebSocket", "Received message: $text")
            if (text.contains("Audio received")) {
                Log.i("WebSocket", "Server confirmed audio reception")
            } else if (text.contains("RecognizeResult")) {
                Log.i("WebSocket", "Received track info: $text")
            }
        }

        override fun onMessage(webSocket: WebSocket, bytes: ByteString) {
            Log.i("WebSocket", "Received bytes: ${bytes.size}")
        }

        override fun onFailure(webSocket: WebSocket, t: Throwable, response: okhttp3.Response?) {
            Log.e("WebSocket", "WebSocket connection failed", t)
            isWebSocketOpen = false
            retryConnectToWebSocket()
        }

        override fun onClosed(webSocket: WebSocket, code: Int, reason: String) {
            Log.i("WebSocket", "WebSocket connection closed: $reason")
            isWebSocketOpen = false
        }
    })

    client.dispatcher.executorService.shutdown()
}

private fun sendHandshakeMessage(webSocket: WebSocket) {
    val handshakeMessage = """{"protocol":"json","version":1}\u001E"""
    val byteString = handshakeMessage.encodeUtf8()
    webSocket.send(byteString)
}

private fun retryConnectToWebSocket() {
    GlobalScope.launch {
        delay(2000) // Задержка перед повторной попыткой подключения
        connectToWebSocket()
    }
}

private fun requestRecognitionResults() {
    if (isWebSocketOpen) {
        val message = """{"arguments":[],"invocationId":"0","target":"GetRecognizeResult","type":1}"""
        webSocket?.send(message.encodeUtf8())
        Log.i("WebSocket", "NICE")
        closeWebSocketAfterDelay()
    } else {
        Log.e("WebSocket", "Attempted to get recognized results but WebSocket is not open")
    }
}

private fun closeWebSocketAfterDelay() {
    GlobalScope.launch {
        delay(2000) // Даем время серверу обработать запрос и отправить ответ
        closeWebSocket()
    }
}

private fun closeWebSocket() {
    if (isWebSocketOpen) {
        Log.i("WebSocket", "Attempting to close WebSocket")
        try {
            if (webSocket?.close(1000, "Client closing connection") == true) {
                Log.i("WebSocket", "WebSocket close initiated successfully")
            } else {
                Log.e("WebSocket", "Failed to initiate WebSocket close")
                retryCloseWebSocket()
            }
        } catch (e: Exception) {
            Log.e("WebSocket", "Exception during WebSocket close: ${e.message}")
            retryCloseWebSocket()
        }
    } else {
        Log.e("WebSocket", "Attempted to close WebSocket but it was not open")
    }
}

private fun retryCloseWebSocket() {
    Log.i("WebSocket", "Retrying WebSocket close")
    GlobalScope.launch {
        delay(1000) // Задержка перед повторной попыткой закрытия
        closeWebSocket()
    }
}

private fun getRecognizedResults() {
    if (isWebSocketOpen) {
        val message = """{"arguments":[],"invocationId":"0","target":"GetRecognizeResult","type":1}"""
        webSocket?.send(message.encodeUtf8())
    } else {
        Log.e("WebSocket", "Attempted to get recognized results but WebSocket is not open")
    }
}
suspend fun pingServer() {
    val connection = HubConnectionBuilder
        .create(wsUrl)
        .withHeaders(mapOf("X-API-KEY" to apiKey))
        .build()

    connection.start().blockingAwait()

    connection.on("Ping", { message: String ->
        run {
            Log.i("Ping", "Message from server: $message")
        }
    }, String::class.java)

    for (n in 1 .. 8) {
        connection.in
        delay(5000) // Пинг каждую секунду
    }
}*/
// tryRecording.kt
package com.modilebev.beatheaven.client

import android.provider.DocumentsContract
import android.util.Log
import com.microsoft.signalr.HubConnection
import com.microsoft.signalr.HubConnectionBuilder
import com.modilebev.beatheaven.MainActivity
import kotlinx.coroutines.*
import java.io.File
import java.io.FileInputStream
import java.io.InputStream
import java.util.Base64
import kotlin.io.*


fun tryRecording(activity: MainActivity) {
    val apiKey = "7p8AAB2e1db2"
    val uri = "ws://77.91.70.64:9432/api/v1/recognize"

    val hubConnection = HubConnectionBuilder.create(uri)
        .withHeader("X-API-Key", apiKey)
        .build()

    Log.d("SignalR", "open")
    CoroutineScope(Dispatchers.IO).launch {
        hubConnection.start().blockingAwait()

        for (i in 1 until 10) {
            val assetFileName = "sample_mp3_1_$i.mp3"
            val inputStream: InputStream? = activity.assets.open(assetFileName)

            val data = inputStream?.readBytes()
            if (inputStream != null) {
                //val dataChunkSize = inputStream.read()

                //val data = ByteArray(dataChunkSize)
                //inputStream.read(data, 0, dataChunkSize)
                val encodedString = Base64.getEncoder().encodeToString(data)
                Log.d("SignalR", "send bytes")
                Log.d("SignalR", encodedString)
                hubConnection.invoke("SendBytes", encodedString)
                delay(2000)
            } else {
                Log.e("SignalR", "File not found: $assetFileName")
            }
        }

        hubConnection.on("RecognizedResults", { data: String ->
            run {
                Log.d("SignalR", data)
            }
        }, String::class.java)

        delay(1000)
        hubConnection.invoke("GetRecognizeResult").blockingAwait()
        delay(1000)

        /*
        hubConnection.on("Ping") { test: Any ->
            Log.d("SignalR", "AAA")
        }

        hubConnection.send("Ping")
        */

        Log.d("SignalR", "close")
        hubConnection.stop().blockingAwait()
    }
}

public fun FileInputStream.readShort(): Short {
    val buffer = ByteArray(2)
    read(buffer, 0, 2)
    return ((buffer[1].toInt() and 0xFF) shl 8 or (buffer[0].toInt() and 0xFF)).toShort()
}

