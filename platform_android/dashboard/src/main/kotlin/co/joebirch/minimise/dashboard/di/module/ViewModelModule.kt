package co.joebirch.minimise.dashboard.di.module

import androidx.lifecycle.ViewModel
import co.joebirch.minimise.android.core.di.ViewModelKey
import co.joebirch.minimise.dashboard.DashboardViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(DashboardViewModel::class)
    abstract fun bindAuthenticationViewModel(viewModel: DashboardViewModel): ViewModel
}