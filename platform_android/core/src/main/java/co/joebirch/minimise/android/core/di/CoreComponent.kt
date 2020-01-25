package co.joebirch.minimise.android.core.di

import android.app.Application
import android.content.Context
import dagger.BindsInstance
import dagger.Component
import co.joebirch.minimise.android.core.di.module.ContextModule
import co.joebirch.minimise.android.core.di.module.ViewModelModule
import javax.inject.Singleton

@Singleton
@Component(modules = [
    CoreModule::class,
    ContextModule::class,
    ViewModelModule::class
])
interface CoreComponent {

    @Component.Builder interface Builder {
        @BindsInstance
        fun application(application: Application): Builder
        fun build(): CoreComponent
    }

    fun context(): Context
}