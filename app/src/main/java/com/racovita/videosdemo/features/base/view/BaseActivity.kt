package com.racovita.videosdemo.features.base.view

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import com.racovita.videosdemo.features.videos.view.adapter.SectionsPagerAdapter
import com.racovita.videosdemo.utils.helper.ResUtil
import org.koin.android.ext.android.inject


/**
 * Common functional for all Activities
 */
@SuppressLint("Registered")
open class BaseActivity : AppCompatActivity() {

    protected val mResUtil: ResUtil by inject()

}