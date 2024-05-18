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
import java.io.InputStream
import java.util.Base64

val apiKey = "7p8AAB2e1db2"
val uri = "ws://77.91.70.64:9432/api/v1/recognize"

fun tryRecording(activity: MainActivity) {

    val hubConnection = HubConnectionBuilder.create(uri)
        .withHeader("X-API-Key", apiKey)
        .build()

    Log.d("SignalR", "open")
    CoroutineScope(Dispatchers.IO).launch {
        hubConnection.start().blockingAwait()



        /** Код для преобразования аудиозаписи длиной 10 секунд в массив байтов, кодированный в строку
         *  и декодированный в байтовую строку Base64 **/
        // Параметры для записи аудио
        /*val sampleRate = 44100
        val channelConfig = AudioFormat.CHANNEL_IN_MONO
        val audioFormat = AudioFormat.ENCODING_PCM_16BIT
        val bufferSize = AudioRecord.getMinBufferSize(sampleRate, channelConfig, audioFormat)

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

        // Запись в течение 10 секунд
        //val recordingDuration = 10000
        val audioData = ByteArray(bufferSize)
        val totalData = mutableListOf<Byte>()

        val startTime = System.currentTimeMillis()
        while (System.currentTimeMillis() - startTime < 2000) {
            val readBytes = audioRecord.read(audioData, 0, audioData.size)
            if (readBytes > 0) {
                totalData.addAll(audioData.take(readBytes))
            }
        }

        // Остановка записи
        audioRecord.stop()
        audioRecord.release()
        Log.d("SignalR", "Recording stopped")

        // Преобразование данных в строку
        val data = totalData.toByteArray()
        val encodedString = Base64.getEncoder().encodeToString(data)
        Log.d("SignalR", "send bytes")
        Log.d("SignalR", encodedString)
        hubConnection.invoke("SendBytes", encodedString)
        delay(1000)
        */

        /** Код для преобразования 10 аудиофайлов в массив байтов, кодированный в строку
         *  и декодированный в байтовую строку Base64 **/

        /*for (i in 1 until 10) {
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
        }*/


        hubConnection.on("RecognizedResults", { data: String ->
            run {
                Log.d("SignalR", data)
            }
        }, String::class.java)

        delay(1000)
        hubConnection.invoke("GetRecognizeResult").blockingAwait()
        delay(1000)

        /*hubConnection.on("Ping") { test: Any ->
            Log.d("SignalR", "AAA")
        }
        hubConnection.send("Ping")*/

        Log.d("SignalR", "close")
        hubConnection.stop().blockingAwait()
    }
}




suspend fun pingServer() {
    val connection = HubConnectionBuilder
        .create(uri)
        .withHeaders(mapOf("X-API-KEY" to apiKey))
        .build()

    connection.start().blockingAwait()

    connection.on("Ping", { message: String ->
        run {
            Log.i("Ping", "Message from server: $message")
        }
    }, String::class.java)

    for (n in 1 .. 8) {
        connection.invoke("Ping").blockingAwait()
        delay(5000) // Пинг каждую секунду
    }
}*/
package com.modilebev.beatheaven.client

import android.annotation.SuppressLint
import android.media.AudioFormat
import android.media.AudioRecord
import android.media.MediaRecorder
import android.util.Log
import com.modilebev.beatheaven.MainActivity
import com.microsoft.signalr.HubConnectionBuilder
import kotlinx.coroutines.*
import java.util.Base64

fun tryRecording(activity: MainActivity) {
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
