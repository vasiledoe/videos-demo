package com.racovita.videosdemo.features.videos.view

import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.racovita.videosdemo.R
import com.racovita.videosdemo.data.models.Video
import com.racovita.videosdemo.features.base.view.BaseActivity
import com.racovita.videosdemo.features.videos.view_model.VideosViewModel
import com.racovita.videosdemo.utils.extensions.hide
import com.racovita.videosdemo.utils.extensions.show
import kotlinx.android.synthetic.main.activity_videos_content.*
import org.koin.android.viewmodel.ext.android.viewModel

class VideosActivity : BaseActivity() {

    private val mViewModel by viewModel<VideosViewModel>()
    private lateinit var mItemsAdapter: VideosListAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_videos)

        setupViews()
        setupItemsAdapter()

        onBindModel()

        mViewModel.getVideos()
    }

    private fun setupViews() {

    }

    private fun setupItemsAdapter() {
        mItemsAdapter =
            VideosListAdapter(
                items = mutableListOf(),
                onClickItemListener = { openItem(it) })

        rv_items.adapter = mItemsAdapter

        rv_items.layoutManager = LinearLayoutManager(
            this,
            RecyclerView.VERTICAL,
            false
        )
    }

    private fun onBindModel() {
        mViewModel.error.observe(this, Observer { info ->

        })

        mViewModel.videos.observe(this, Observer { items ->
            loadItems(items)
        })
    }

    private fun loadItems(apiVideos: List<Video>) {
        progress.hide()

        if (apiVideos.isNotEmpty()) {
            tv_error.hide()
            rv_items.show()

            mItemsAdapter.addItems(apiVideos)

        } else {
            tv_error.show()
            rv_items.hide()
            tv_error.text = resources.getString(R.string.err_no_items)
        }
    }

    /**
     * Fake method to demonstrate each item click action
     */
    private fun openItem(apiVideo: Video) {
        Toast.makeText(
            this,
            apiVideo.id.toString().plus(" ").plus(apiVideo.title),
            Toast.LENGTH_LONG
        ).show()
    }
}