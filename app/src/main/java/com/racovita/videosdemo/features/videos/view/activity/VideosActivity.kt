package com.racovita.videosdemo.features.videos.view.activity

import android.os.Bundle
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import com.racovita.videosdemo.R
import com.racovita.videosdemo.features.base.view.BaseActivity
import com.racovita.videosdemo.features.videos.view.adapter.SectionsPagerAdapter

class VideosActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_videos)

        setupViews()
    }

    private fun setupViews() {
        val sectionsPagerAdapter =
            SectionsPagerAdapter(
                supportFragmentManager,
                mResUtil
            )

        val viewPager: ViewPager = findViewById(R.id.view_pager)
        viewPager.adapter = sectionsPagerAdapter

        val tabs: TabLayout = findViewById(R.id.tabs)
        tabs.setupWithViewPager(viewPager)
    }
}