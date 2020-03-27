package co.joebirch.minimise.dashboard.di

import co.joebirch.minimise.android.core.di.coreComponent
import co.joebirch.minimise.dashboard.DashboardFragment
import co.joebirch.minimise.dashboard.di.component.DaggerDashboardComponent

fun inject(fragment: DashboardFragment) {
    DaggerDashboardComponent
        .builder()
        .coreComponent(fragment.coreComponent())
        .build()
        .inject(fragment)
}