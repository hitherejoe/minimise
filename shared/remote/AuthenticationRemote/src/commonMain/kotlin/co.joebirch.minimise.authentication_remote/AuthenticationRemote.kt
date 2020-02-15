package co.joebirch.minimise.authentication_remote

import co.joebirch.minimise.authentication_remote.model.AuthenticationResponse
import co.joebirch.minimise.shared_common.di.Provider

interface AuthenticationRemote {

    suspend fun signUp(
        email: String,
        password: String,
        returnSecureToken: Boolean
    ): AuthenticationResponse

    suspend fun signIn(
        email: String,
        password: String,
        returnSecureToken: Boolean
    ): AuthenticationResponse

    companion object : Provider<AuthenticationRemote>()  {
        override fun create(): AuthenticationRemote =
            AuthenticationRemoteProvider.getAuthenticationRemote()
    }
}