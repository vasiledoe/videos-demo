package com.racovita.videosdemo.features.videos.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.racovita.videosdemo.R
import com.racovita.videosdemo.features.videos.view_model.VideosViewModel

/**
 * Fragment containing videos
 */
class VideosFragment : Fragment() {

    private lateinit var mViewModel: VideosViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        bindViewModel()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_videos, container, false)

        return root
    }


    /**
     * Bind ViewModel from parent Activity - need it shared between activity and fragments
     */
    private fun bindViewModel() {
        activity?.let {
            mViewModel = ViewModelProviders.of(it).get(VideosViewModel::class.java)
        }
    }


    companion object {
        /**
         * The fragment argument representing the type of items to show
         */
        private const val ARG_VIDEOS_TYPE = "videos"

        const val VIDEOS_TYPE_ALL = "all"
        const val VIDEOS_TYPE_FAVORITE = "favorites"

        /**
         * Returns a new instance of this fragment for the given videos type
         */
        @JvmStatic
        fun newInstance(videosType: String): VideosFragment {
            return VideosFragment()
                .apply {
                    arguments = Bundle().apply {
                        putString(ARG_VIDEOS_TYPE, videosType)
                    }
                }
        }
    }
}