package com.racovita.videosdemo.features.videos.view_model

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.racovita.videosdemo.data.models.Video
import com.racovita.videosdemo.data.models.VideosRs
import com.racovita.videosdemo.utils.extensions.getPrettyErrorMessage
import com.racovita.videosdemo.utils.extensions.toDomain
import com.racovita.videosdemo.utils.helper.ResUtil
import com.racovita.videosdemo.utils.network.DataRepository
import com.racovita.videosdemo.utils.network.NetworkConnectivity
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable

class VideosViewModel(
    private val connectivity: NetworkConnectivity,
    private val repo: DataRepository,
    private val resUtil: ResUtil
) : ViewModel() {

    val videos = MutableLiveData<List<Video>>()
    val totalPages = MutableLiveData<Int>()
    val currentPage = MutableLiveData<Int>()

    val loadingState = MutableLiveData<Boolean>()
    val error = MutableLiveData<String>()

    private var mDisposables = CompositeDisposable()


    /**
     * Call in owner Activity when it's started or screen orientation is changed so
     * if already have data - ignore calling API because
     * Activity will receive items from existing LiveData, else initiate API request.
     */
    fun getVideos() {
        if (videos.value == null) {
            getData()
        }
    }


    fun getData() {
        repo.getData()
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe {
                mDisposables.add(it)
                loadingState.value = true
            }
            .doAfterTerminate {
                loadingState.value = false
            }
            .subscribe(
                this::onHandleSuccess,
                this::onHandleError
            )
    }

    private fun onHandleSuccess(videosRs: VideosRs) {
        videos.value = videosRs.results.map { it.toDomain() }
        currentPage.value = videosRs.page
        totalPages.value = videosRs.pagesAmount
    }

    private fun onHandleError(t: Throwable) {
        error.value = t.getPrettyErrorMessage(resUtil)
    }


    /**
     * Clear all disposables if ViewModel is cleared. It happens when Activity owner doesn't
     * need it anymore - when it's destroyed.
     *
     * */
    override fun onCleared() {
        super.onCleared()

        mDisposables.clear()
    }
}