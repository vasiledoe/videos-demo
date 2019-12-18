package com.racovita.videosdemo.utils

import com.racovita.videosdemo.utils.helper.ResUtil
import org.koin.dsl.module
import org.koin.experimental.builder.single

val utilsModule = module {

    single<ResUtil>()
}