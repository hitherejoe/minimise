package co.joebirch.minimise.authentication.di.component

import co.joebirch.minimise.android.core.di.BaseComponent
import co.joebirch.minimise.android.core.di.CoreComponent
import co.joebirch.minimise.android.core.di.scope.FragmentScope
import co.joebirch.minimise.authentication.AuthenticationFragment
import dagger.Component
import co.joebirch.minimise.authentication.di.module.AuthenticationModule

@FragmentScope
@Component(
    modules = [AuthenticationModule::class],
    dependencies = [CoreComponent::class]
)
interface AuthenticationComponent : BaseComponent<AuthenticationFragment> {

    @Component.Builder
    interface Builder {
        fun build(): AuthenticationComponent
        fun coreComponent(module: CoreComponent): Builder
    }
}