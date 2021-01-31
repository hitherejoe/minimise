package co.joebirch.minimise.authentication

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import co.joebirch.minimise.authentication.interactor.Authenticate
import co.joebirch.minimise.authentication.interactor.ResetPassword
import co.joebirch.minimise.authentication.mapper.ResetPasswordResponseMapper
import co.joebirch.minimise.authentication.remote.AuthenticationRemote
import co.joebirch.minimise.authentication.remote.AuthenticationRemoteStore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.qualifiers.ActivityContext

@Module
@InstallIn(ActivityComponent::class)
object AuthenticationModule {

    @Provides
    fun providesAuthenticate(
        repository: AuthenticationRepository
    ): Authenticate = Authenticate(repository)

    @Provides
    fun providesResetPassword(
        repository: AuthenticationRepository
    ): ResetPassword = ResetPassword(repository)

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