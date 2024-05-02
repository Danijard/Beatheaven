package com.modilebev.beatheaven.client
import android.annotation.SuppressLint
import android.media.AudioFormat
import android.media.AudioRecord
import android.media.MediaRecorder
import com.modilebev.beatheaven.MainActivity
import com.modilebev.beatheaven.checkPermissions
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.WebSocket
import okhttp3.WebSocketListener
import okio.ByteString
import okio.ByteString.Companion.toByteString
import android.os.Handler
import android.os.Looper

private const val sampleRate = 44100
private const val channelConfig = AudioFormat.CHANNEL_IN_MONO
private const val audioFormat = AudioFormat.ENCODING_PCM_16BIT
private val bufferSize = AudioRecord.getMinBufferSize(sampleRate, channelConfig, audioFormat)

private var isRecording = false
private var audioRecord: AudioRecord? = null
private var webSocket: WebSocket? = null

private const val webSocketUrl = "ws://192.168.0.239:1111"
private val request = Request.Builder().url(webSocketUrl).build()

fun tryRecording(activity: MainActivity) {
    if (checkPermissions(activity)) {
        if (!isRecording){
            @SuppressLint("MissingPermission")
            audioRecord = AudioRecord(
                MediaRecorder.AudioSource.MIC,
                sampleRate,
                channelConfig,
                audioFormat,
                bufferSize
            )

            webSocket = OkHttpClient().newWebSocket(request, object : WebSocketListener() {
                override fun onOpen(webSocket: WebSocket, response: Response) {
                    // Запуск записи после установки соединения
                    audioRecord?.startRecording()
                    isRecording = true
                    val buffer = ByteArray(bufferSize)

                    val handler = Handler(Looper.getMainLooper())
                    handler.postDelayed({
                        if (audioRecord?.recordingState == AudioRecord.RECORDSTATE_RECORDING) {
                            audioRecord?.stop()
                            webSocket.close(EchoWebSocketListener.NORMAL_CLOSURE_STATUS, "End of stream")
                            isRecording = false
                        }
                    }, 10000) // Остановка записи через 10 секунд

                    while (audioRecord?.recordingState == AudioRecord.RECORDSTATE_RECORDING) {
                        val bytesRead = audioRecord?.read(buffer, 0, bufferSize)
                        if (bytesRead != null && bytesRead > 0) {
                            webSocket.send(buffer.toByteString(0, bytesRead))
                        }
                    }
                }

                override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
                    // Обработка ошибки
                    t.printStackTrace()
                }
            })
        } else {
            // Остановка записи при повторном нажатии кнопки
            audioRecord?.stop()
            webSocket?.close(EchoWebSocketListener.NORMAL_CLOSURE_STATUS, "End of stream")
            isRecording = false
        }
    }
}

class EchoWebSocketListener : WebSocketListener() {
    override fun onOpen(webSocket: WebSocket, response: Response) {
        webSocket.send("Knock, knock!")
        webSocket.send("Hello!")
        webSocket.close(NORMAL_CLOSURE_STATUS, "Goodbye!")
    }

    override fun onMessage(webSocket: WebSocket, text: String) {
        println("Receiving: $text")
    }

    override fun onMessage(webSocket: WebSocket, bytes: ByteString) {
        println("Receiving: " + bytes.hex())
    }

    override fun onClosing(webSocket: WebSocket, code: Int, reason: String) {
        webSocket.close(NORMAL_CLOSURE_STATUS, null)
        println("Closing: $code $reason")
    }

    override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
        t.printStackTrace()
    }

    companion object {
        const val NORMAL_CLOSURE_STATUS = 1000
    }
}
