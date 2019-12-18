package com.racovita.videosdemo.features.videos.view.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.racovita.videosdemo.R
import com.racovita.videosdemo.features.videos.view.fragment.VideosFragment
import com.racovita.videosdemo.utils.helper.ResUtil

/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to one of the tabs.
 */
class SectionsPagerAdapter(fm: FragmentManager, private val resUtil: ResUtil) :
    FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    override fun getItem(position: Int): Fragment {
        return if (position == 0) {
            VideosFragment.newInstance(VideosFragment.VIDEOS_TYPE_ALL)

        } else {
            VideosFragment.newInstance(VideosFragment.VIDEOS_TYPE_FAVORITE)
        }
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return if (position == 0) {
            resUtil.getStringRes(R.string.tab_text_all)

        } else {
            resUtil.getStringRes(R.string.tab_text_fav)
        }
    }

    override fun getCount(): Int {
        // Show 2 total pages.
        return 2
    }
}