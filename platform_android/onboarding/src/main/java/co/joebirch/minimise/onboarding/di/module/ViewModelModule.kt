package co.joebirch.minimise.onboarding.di.module

import androidx.lifecycle.ViewModel
import co.joebirch.minimise.android.core.di.ViewModelKey
import co.joebirch.minimise.onboarding.OnboardingViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(OnboardingViewModel::class)
    abstract fun bindOnboardingViewModel(viewModel: OnboardingViewModel): ViewModel
}