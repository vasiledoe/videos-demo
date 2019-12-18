package com.racovita.videosdemo.utils.extensions

import com.racovita.videosdemo.data.models.ApiVideo
import com.racovita.videosdemo.data.models.Video

/**
 * Format object data as is required to consume it in app.
 */
fun ApiVideo.toDomain(): Video {
    return Video(
        id = id,
        title = title ?: "",
        rating = rating?.div(2) ?: 0f,
        ratingStr = String.format("%.2f", rating?.div(2) ?: 0),
        raters = "(".plus(raters.toString()).plus(")"),
        thumb = if (thumb.isNullOrEmpty()) {
            "unknown_url"
        } else {
            "https://image.tmdb.org/t/p/w500".plus(thumb)
        },
        overview = overview ?: ""
    )
}