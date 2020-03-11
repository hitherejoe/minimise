package co.joebirch.minimise.authentication.di.module

import androidx.lifecycle.ViewModel
import co.joebirch.minimise.android.core.di.ViewModelKey
import co.joebirch.minimise.authentication.AuthenticationViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(AuthenticationViewModel::class)
    abstract fun bindAuthenticationViewModel(viewModel: AuthenticationViewModel): ViewModel
}