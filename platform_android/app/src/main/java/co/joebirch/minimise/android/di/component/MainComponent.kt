package co.joebirch.minimise.android.di.component

import co.joebirch.minimise.android.MainActivity
import co.joebirch.minimise.android.core.di.BaseComponent
import co.joebirch.minimise.android.core.di.CoreComponent
import co.joebirch.minimise.android.core.di.scope.ActivityScope
import co.joebirch.minimise.android.di.module.ViewModelModule
import dagger.Component

@ActivityScope
@Component(
    modules = [ViewModelModule::class],
    dependencies = [CoreComponent::class]
)
interface MainComponent : BaseComponent<MainActivity> {

    @Component.Builder
    interface Builder {
        fun build(): MainComponent
        fun coreComponent(module: CoreComponent): Builder
    }
}