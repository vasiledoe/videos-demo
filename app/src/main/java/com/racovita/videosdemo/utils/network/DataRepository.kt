package com.racovita.videosdemo.utils.network

import com.racovita.videosdemo.data.models.VideosRs
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers

class DataRepository(private val client: RestClient) {

    fun getData(page: Int = 1): Single<VideosRs> =
        client.retrofit.getVideos(page = page)
            .subscribeOn(Schedulers.io())
}