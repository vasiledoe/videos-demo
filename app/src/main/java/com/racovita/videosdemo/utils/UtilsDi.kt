package com.racovita.videosdemo.utils

import com.racovita.videosdemo.utils.helper.ResUtil
import com.racovita.videosdemo.utils.network.DataRepository
import com.racovita.videosdemo.utils.network.RestClient
import org.koin.dsl.module
import org.koin.experimental.builder.single

val utilsModule = module {

    single<ResUtil>()

    single<RestClient>()
    single<DataRepository>()
}