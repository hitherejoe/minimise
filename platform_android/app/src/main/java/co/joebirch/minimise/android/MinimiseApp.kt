package co.joebirch.minimise.android

import android.app.Application
import co.joebirch.minimise.android.core.di.CoreComponent
import co.joebirch.minimise.android.core.di.CoreComponentProvider
import co.joebirch.minimise.android.core.di.DaggerCoreComponent
import co.joebirch.minimise.shared_core.MinimiseApp

class MinimiseApp : Application(), CoreComponentProvider {

    private lateinit var coreComponent: CoreComponent

    override fun onCreate() {
        super.onCreate()
        MinimiseApp.startKoin()

        coreComponent = DaggerCoreComponent
            .builder()
            .application(this)
            .build()
    }

    override fun provideCoreComponent(): CoreComponent = coreComponent
}