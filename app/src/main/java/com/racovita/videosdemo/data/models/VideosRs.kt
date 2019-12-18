package com.racovita.videosdemo.data.models

import com.google.gson.annotations.SerializedName

data class VideosRs(
    val page: Int,
    @SerializedName("total_pages") val pagesAmount: Int,
    val results: Array<Video>,
    val success: Boolean?,
    @SerializedName("status_message") val message: String?
)

data class Video(
    val id: Int,
    val title: String,
    val vote_average: Float,
    val vote_count: Int,
    val overview: String
)