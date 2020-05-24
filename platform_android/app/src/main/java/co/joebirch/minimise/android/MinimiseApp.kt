package co.joebirch.minimise.android

import android.app.Application
import co.joebirch.minimise.android.core.di.CoreComponent
import co.joebirch.minimise.android.core.di.CoreComponentProvider
import co.joebirch.minimise.android.core.di.DaggerCoreComponent
import com.google.firebase.FirebaseApp
import com.google.firebase.ktx.Firebase
import com.google.firebase.ktx.initialize

class MinimiseApp : Application(), CoreComponentProvider {

    private lateinit var coreComponent: CoreComponent

    override fun onCreate() {
        super.onCreate()

        coreComponent = DaggerCoreComponent
            .builder()
            .application(this)
            .build()

        FirebaseApp.initializeApp(this)
    }

    override fun provideCoreComponent(): CoreComponent = coreComponent
}