package com.racovita.videosdemo.utils.network

import com.racovita.videosdemo.BuildConfig
import com.racovita.videosdemo.data.models.VideosRs
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query
import java.util.*

interface Webservice {

    @GET("popular")
    fun getVideos(
        @Query("api_key") apiKey: String = BuildConfig.API_KEY,
        @Query("language") lang: String = Locale.getDefault().language,
        @Query("page") page: Int
    ): Single<VideosRs>
}