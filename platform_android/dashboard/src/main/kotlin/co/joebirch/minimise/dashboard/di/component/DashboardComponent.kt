package co.joebirch.minimise.dashboard.di.component

import co.joebirch.minimise.android.core.di.BaseComponent
import co.joebirch.minimise.android.core.di.CoreComponent
import co.joebirch.minimise.android.core.di.scope.FragmentScope
import dagger.Component
import co.joebirch.minimise.dashboard.DashboardFragment
import co.joebirch.minimise.dashboard.di.module.ViewModelModule

@FragmentScope
@Component(
    modules = [ViewModelModule::class],
    dependencies = [CoreComponent::class]
)
interface DashboardComponent : BaseComponent<DashboardFragment> {

    @Component.Builder
    interface Builder {
        fun build(): DashboardComponent
        fun coreComponent(module: CoreComponent): Builder
    }
}