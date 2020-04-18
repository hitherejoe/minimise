package co.joebirch.minimise.android.di.module

import androidx.lifecycle.ViewModel
import co.joebirch.minimise.android.MainViewModel
import co.joebirch.minimise.android.core.di.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    abstract fun bindMainViewModel(viewModel: MainViewModel): ViewModel
}