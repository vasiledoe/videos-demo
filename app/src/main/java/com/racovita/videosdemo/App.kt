package com.racovita.videosdemo

import android.app.Application
import com.racovita.videosdemo.features.videos.videosModule
import com.racovita.videosdemo.utils.utilsModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        setupKoin()
    }

    private fun setupKoin() {
        startKoin {
            // use Koin logger
            printLogger(Level.DEBUG)

            // Android context
            androidContext(this@App)

            // modules
            modules(utilsModule + videosModule)
        }
    }
}