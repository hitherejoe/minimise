package co.joebirch.minimise.android

import co.joebirch.minimise.navigation.NavigationController
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Singleton
    @Provides
    fun providesNavigationController() = NavigationController()
}