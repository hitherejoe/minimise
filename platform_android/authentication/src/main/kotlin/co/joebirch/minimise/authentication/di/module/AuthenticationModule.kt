package co.joebirch.minimise.authentication.di.module

import co.joebirch.minimise.authentication.interactor.Authenticate
import dagger.Module
import dagger.Provides

@Module
class AuthenticationModule {

    @Provides
    fun providesAuthenticate(): Authenticate {
        return Authenticate()
    }

}