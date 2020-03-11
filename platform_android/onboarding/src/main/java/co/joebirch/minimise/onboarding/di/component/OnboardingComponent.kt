package co.joebirch.minimise.onboarding.di.component

import co.joebirch.minimise.android.core.di.BaseComponent
import co.joebirch.minimise.android.core.di.CoreComponent
import co.joebirch.minimise.android.core.di.scope.FragmentScope
import dagger.Component
import co.joebirch.minimise.onboarding.OnboardingFragment
import co.joebirch.minimise.onboarding.di.module.ViewModelModule

@FragmentScope
@Component(
    modules = [ViewModelModule::class],
    dependencies = [CoreComponent::class]
)
interface OnboardingComponent : BaseComponent<OnboardingFragment> {

    @Component.Builder
    interface Builder {
        fun build(): OnboardingComponent
        fun coreComponent(module: CoreComponent): Builder
    }
}