package co.joebirch.minimise.creation.di.component

import co.joebirch.minimise.android.core.di.BaseComponent
import co.joebirch.minimise.android.core.di.CoreComponent
import co.joebirch.minimise.android.core.di.module.ViewModelModule
import co.joebirch.minimise.android.core.di.scope.FragmentScope
import co.joebirch.minimise.creation.CreationFragment
import co.joebirch.minimise.creation.di.module.CreationModule
import dagger.Component

@FragmentScope
@Component(
    modules = [CreationModule::class, ViewModelModule::class],
    dependencies = [CoreComponent::class]
)
interface CreationComponent : BaseComponent<CreationFragment> {

    @Component.Builder
    interface Builder {
        fun build(): CreationComponent
        fun coreComponent(module: CoreComponent): Builder
    }
}