package com.modilebev.beatheaven

import android.util.Log
import com.google.gson.Gson


data class JSON(
    val tracks: List<Track>
)

data class Album(
    val Artist: String,
    val Attribute: Attribute,
    val Image: List<Image>,
    val Mbid: String,
    val Title: String,
    val Url: String
)

data class Artist(
    val Mbid: String,
    val Name: String,
    val Url: String
)

data class Attribute(
    val Position: String
)

data class Image(
    val Size: String,
    val Text: String
)

data class Track(
    val Album: Album,
    val Artist: Artist,
    val Name: String,
    val YoutubeUrl: String
)

fun dataHandleToFirstTrackInfo(data: String) : List<String> {
    Log.d("data", data)
    val json = Gson().fromJson(data, JSON::class.java)
    val firstTrackInfo = listOf(
        json.tracks[0].Name,
        json.tracks[0].Artist.Name,
        json.tracks[0].Album.Image[json.tracks[0].Album.Image.count()-1].Text,
        json.tracks[0].YoutubeUrl
    )
    Log.d("firstTrackInfo", firstTrackInfo.toString())
    return firstTrackInfo
}