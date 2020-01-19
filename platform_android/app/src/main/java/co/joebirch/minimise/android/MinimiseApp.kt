package co.joebirch.minimise.android

import android.app.Application
import co.joebirch.minimise.shared_core.MinimiseApp

class MinimiseApp : Application() {

    override fun onCreate() {
        super.onCreate()
        MinimiseApp.startKoin()
    }

}