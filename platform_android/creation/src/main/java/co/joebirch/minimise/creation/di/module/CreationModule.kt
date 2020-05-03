package co.joebirch.minimise.creation.di.module

import androidx.lifecycle.ViewModel
import co.joebirch.minimise.android.core.di.ViewModelKey
import co.joebirch.minimise.creation.CreationViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class CreationModule {
    @Binds
    @IntoMap
    @ViewModelKey(CreationViewModel::class)
    abstract fun bindCreationViewModel(viewModel: CreationViewModel): ViewModel

}