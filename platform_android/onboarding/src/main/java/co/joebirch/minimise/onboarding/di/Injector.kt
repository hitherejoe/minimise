package co.joebirch.minimise.onboarding.di

import co.joebirch.minimise.android.core.di.coreComponent
import co.joebirch.minimise.onboarding.OnboardingFragment
import co.joebirch.minimise.onboarding.di.component.DaggerOnboardingComponent

fun inject(fragment: OnboardingFragment) {
    DaggerOnboardingComponent
        .builder()
        .coreComponent(fragment.coreComponent())
        .build()
        .inject(fragment)
}