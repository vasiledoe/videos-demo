package com.racovita.videosdemo.features.videos.view

import android.os.Bundle
import android.util.Log
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
    private var isPaginationConsumed: Boolean = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_videos)

        setupItemsAdapter()

        onBindModel()

        mViewModel.getVideos()
    }

    private fun setupItemsAdapter() {
        val linearLayoutManager = LinearLayoutManager(
            this,
            RecyclerView.VERTICAL,
            false
        )

        mItemsAdapter =
            VideosListAdapter(
                items = mutableListOf(),
                onClickItemListener = { openItem(it) })

        rv_items.layoutManager = linearLayoutManager
        rv_items.adapter = mItemsAdapter

        rv_items.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val currentItems = linearLayoutManager.childCount
                val totalItems = linearLayoutManager.itemCount
                val scrollOutItems = linearLayoutManager.findFirstVisibleItemPosition()

                if (scrollOutItems + currentItems == totalItems &&
                    !isPaginationConsumed &&
                    mViewModel.hasNextPage
                ) {
                    onPaginationState(true)
                    mViewModel.getData()
                }
            }
        })
    }

    private fun onBindModel() {
        mViewModel.error.observe(this, Observer { info ->

        })

        mViewModel.videos.observe(this, Observer { items ->
            loadItems(items)
        })
    }

    /**
     * Load items to list
     *
     * <CAUTION!!!> in the API there is a issue concerning pagination, item 19 and 20 are the same
     * with ID 540247 - so don't be surprised there are 2 same items for first pagination!
     *
     */
    private fun loadItems(apiVideos: List<Video>) {
        onPaginationState(false)
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

    private fun onPaginationState(icConsumed: Boolean) {
        if (icConsumed) {
            isPaginationConsumed = true
            progress_pagination.show()

        } else {
            isPaginationConsumed = false
            progress_pagination.hide()
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