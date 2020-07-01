package co.joebirch.minimise.android

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MinimiseApp : Application() {

    override fun onCreate() {
        super.onCreate()
    }
}