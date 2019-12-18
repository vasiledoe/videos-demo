package com.racovita.videosdemo.features.videos.view_model

import android.annotation.SuppressLint
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.racovita.videosdemo.data.models.Video
import com.racovita.videosdemo.data.models.VideosRs
import com.racovita.videosdemo.utils.extensions.getPrettyErrorMessage
import com.racovita.videosdemo.utils.extensions.plusAssign
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

    /**
     * Used to store all items and show them when screen orientation change
     */
    private val videosTemp = MutableLiveData<ArrayList<Video>>()

    /**
     * Used to publish items to UI, each page or value of [videosTemp] when screen
     * orientation change
     */
    val videos = MutableLiveData<List<Video>>()

    /**
     * Used to keep pagination data simple to use
     */
    var hasNextPage: Boolean = false
    var nextPage: Int = 1

    /**
     * Used to track loading progress bar state & notify it to UI
     */
    val loadingState = MutableLiveData<Boolean>()

    /**
     * Used to publish to UI any API error
     */
    val error = MutableLiveData<String>()

    /**
     * store here all disposables and cancel them all when [VideosViewModel] is destroyed
     */
    private var mDisposables = CompositeDisposable()


    /**
     * Called in owner Activity when it's started or screen orientation is changed so
     * if already have data - ignore calling API because
     * Activity will receive items from existing LiveData, else initiate API request.
     */
    fun getVideos() {
        if (videosTemp.value != null) {
            videos.value = videosTemp.value

        } else {
            getData()
        }
    }


    @SuppressLint("CheckResult")
    fun getData() {
        repo.getData(nextPage)
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe {
                mDisposables.add(it)

                if (nextPage > 1)
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
        val items = videosRs.results.map { it.toDomain() }
        videos.value = items
        videosTemp += items

        hasNextPage = videosRs.page < videosRs.pagesAmount
        nextPage = videosRs.page + 1
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