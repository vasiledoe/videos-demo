package com.racovita.videosdemo.data.models

import com.google.gson.annotations.SerializedName

data class VideosRs(
    val page: Int,
    @SerializedName("total_pages") val pagesAmount: Int,
    val results: Array<ApiVideo>
)

/**
 * Received from REST API
 */
data class ApiVideo(
    val id: Int,
    val title: String?,
    @SerializedName("vote_average") val rating: Float?,
    @SerializedName("vote_count") val raters: Int?,
    @SerializedName("backdrop_path") val thumb: String?,
    val overview: String?
)

/**
 * Used in adapter
 */
data class Video(
    val id: Int,
    val title: String,
    val rating: Float,
    val ratingStr: String,
    val raters: String,
    val thumb: String,
    val overview: String
)