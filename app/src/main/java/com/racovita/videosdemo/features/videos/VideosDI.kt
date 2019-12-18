package com.racovita.videosdemo.features.videos

import com.racovita.videosdemo.features.videos.view.adapter.SectionsPagerAdapter
import com.racovita.videosdemo.features.videos.view_model.VideosViewModel
import org.koin.android.experimental.dsl.viewModel
import org.koin.dsl.module
import org.koin.experimental.builder.single

val videosModule = module {
    viewModel<VideosViewModel>()
    single<SectionsPagerAdapter>()
}