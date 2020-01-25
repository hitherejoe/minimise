package co.joebirch.minimise.authentication.di

import co.joebirch.minimise.android.core.di.coreComponent
import co.joebirch.minimise.authentication.AuthenticationFragment
import co.joebirch.minimise.authentication.di.component.DaggerAuthenticationComponent

fun inject(fragment: AuthenticationFragment) {
    DaggerAuthenticationComponent
        .builder()
        .coreComponent(fragment.coreComponent())
        .build()
        .inject(fragment)
}