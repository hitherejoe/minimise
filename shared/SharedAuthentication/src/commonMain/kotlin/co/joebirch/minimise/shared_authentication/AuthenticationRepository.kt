package co.joebirch.minimise.shared_authentication

import co.joebirch.minimise.authentication_remote.AuthenticationRemote
import co.joebirch.minimise.shared_authentication.mapper.AuthenticationResponseMapper
import co.joebirch.minimise.shared_authentication.model.AuthenticationModel
import co.joebirch.minimise.shared_common.di.Provider

interface AuthenticationRepository {

    suspend fun signUp(emailAddress: String, password: String): AuthenticationModel

    suspend fun signIn(emailAddress: String, password: String): AuthenticationModel

    companion object : Provider<AuthenticationRepository>() {
        override fun create(): AuthenticationRepository =
            RepositoriesProvider.getAuthenticationDataRepository(AuthenticationRemote.get(),
                AuthenticationResponseMapper()
            )
    }
}