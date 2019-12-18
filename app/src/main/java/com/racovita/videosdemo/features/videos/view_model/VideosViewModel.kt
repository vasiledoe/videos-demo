package com.racovita.videosdemo.features.videos.view_model

import android.annotation.SuppressLint
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.github.pwittchen.reactivenetwork.library.rx2.ReactiveNetwork
import com.racovita.videosdemo.R
import com.racovita.videosdemo.data.models.Video
import com.racovita.videosdemo.data.models.VideosRs
import com.racovita.videosdemo.utils.extensions.getPrettyErrorMessage
import com.racovita.videosdemo.utils.extensions.plusAssign
import com.racovita.videosdemo.utils.extensions.safelyDispose
import com.racovita.videosdemo.utils.extensions.toDomain
import com.racovita.videosdemo.utils.helper.ResUtil
import com.racovita.videosdemo.utils.network.DataRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class VideosViewModel(
    private val repo: DataRepository,
    private val resUtil: ResUtil
) : ViewModel() {

    /**
     * Used to store all items and show them when screen orientation change.
     */
    private val videosTemp = MutableLiveData<ArrayList<Video>>()

    /**
     * Used to publish items to UI, each page or value of [videosTemp] when screen
     * orientation change.
     */
    val videos = MutableLiveData<List<Video>>()

    /**
     * Used to keep pagination data simple to use.
     */
    var hasNextPage: Boolean = false
    var nextPage: Int = 1

    /**
     * Used to track loading progress bar state & notify it to UI.
     */
    private val loadingState = MutableLiveData<Boolean>()

    /**
     * Used to publish to UI any API error.
     */
    val error = MutableLiveData<String>()

    /**
     * Store here all disposables and cancel them all when [VideosViewModel] is destroyed.
     */
    private var mDisposables = CompositeDisposable()

    /**
     * Disposable to keep a reference to current network connectivity check,
     * in case it's connected  just dispose it.
     */
    private var mConnectionDisposable: Disposable? = null


    /**
     * Called in owner Activity when it's started or screen orientation is changed so
     * if already have data - ignore calling API because
     * Activity will receive items from existing LiveData, else initiate API request.
     */
    fun getVideos() {
        /**
         *  nullify error value because after screen rotation if there was any errors,
         *  UI will get the last one
         */
        error.value = null

        /**
         * Clear existing disposables
         */
        mDisposables.clear()

        if (videosTemp.value != null) {
            videos.value = videosTemp.value

        } else {
            tryGetData()
        }
    }

    /**
     * Check if has Internet connection, if no then wait to connect and then do request.
     */
    @SuppressLint("CheckResult")
    fun tryGetData() {
        ReactiveNetwork.observeInternetConnectivity()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe {
                mDisposables.add(it)
                mConnectionDisposable = it
            }
            .subscribe { isConnectedToInternet ->

                if (isConnectedToInternet) {
                    getData()
                    mConnectionDisposable.safelyDispose()

                } else {
                    error.value = resUtil.getStringRes(R.string.err_no_connection)
                }
            }
    }

    /**
     * Do Api request.
     */
    private fun getData() {
        if (nextPage == 1)
            loadingState.value = true

        mDisposables.add(
            repo.getData(nextPage)
                .observeOn(AndroidSchedulers.mainThread())
                .doAfterTerminate {
                    loadingState.value = false
                }
                .subscribe(
                    this::onHandleSuccess,
                    this::onHandleError
                )
        )
    }

    /**
     * Manage successfully server response storing pagination data to local vars &
     * adding items to [videosTemp] to notify UI &
     * storing items to [videosTemp] for screen orientation backup.
     */
    private fun onHandleSuccess(videosRs: VideosRs) {
        val items = videosRs.results.map { it.toDomain() }
        videos.value = items
        videosTemp += items

        hasNextPage = videosRs.page < videosRs.pagesAmount
        nextPage = videosRs.page + 1
    }

    /**
     * Manage error server response and notify UI.
     */
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