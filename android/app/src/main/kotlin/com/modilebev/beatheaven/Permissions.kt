package com.modilebev.beatheaven

import android.Manifest
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

private const val REQUEST_RECORD_AUDIO_PERMISSION = 200

fun checkRecordAudioPermission(activity: MainActivity): Boolean {
    val permission = ContextCompat.checkSelfPermission(
        activity,
        Manifest.permission.RECORD_AUDIO
    )
    return permission == PackageManager.PERMISSION_GRANTED
}

fun requestRecordAudioPermission(activity: MainActivity) {
    ActivityCompat.requestPermissions(
        activity,
        arrayOf(Manifest.permission.RECORD_AUDIO),
        REQUEST_RECORD_AUDIO_PERMISSION
    )
}