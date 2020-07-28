package co.joebirch.minimise.authentication

import co.joebirch.minimise.authentication.interactor.Authenticate
import co.joebirch.minimise.authentication.mapper.ResetPasswordResponseMapper
import co.joebirch.minimise.authentication.remote.AuthenticationRemote
import co.joebirch.minimise.authentication.remote.AuthenticationRemoteStore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.components.FragmentComponent

@Module
@InstallIn(ActivityComponent::class)
object AuthenticationModule {

    @Provides
    fun providesAuthenticate(
        repository: AuthenticationRepository
    ): Authenticate = Authenticate(repository)

    @Provides
    fun providesResetPasswordResponseMapper() =
        ResetPasswordResponseMapper()

    @Provides
    fun providesAuthenticationRepository(
        repository: AuthenticationRemote,
        resetPasswordResponseMapper: ResetPasswordResponseMapper
    ): AuthenticationRepository = AuthenticationDataRepository(repository,
        resetPasswordResponseMapper)

    @Provides
    fun providesAuthenticationRemote(): AuthenticationRemote = AuthenticationRemoteStore()
}