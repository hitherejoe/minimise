package co.joebirch.minimise.shared_authentication

import co.joebirch.minimise.shared_authentication.model.AuthenticationModel
import co.joebirch.minimise.shared_authentication.store.AuthenticationRemote

internal class AuthenticationDataRepository constructor(
    private val authenticationRemote: AuthenticationRemote
) : AuthenticationRepository {

    override suspend fun signUp(
        emailAddress: String,
        password: String
    ): AuthenticationModel {
        val authenticationResponse = authenticationRemote.signUp(emailAddress, password, true)
        return AuthenticationModel(
            authenticationResponse.success,
            authenticationResponse.token, authenticationResponse.errorCode?.toString()
        )
    }

    override suspend fun signIn(
        emailAddress: String,
        password: String
    ): AuthenticationModel {
        val authenticationResponse = authenticationRemote.signIn(emailAddress, password, true)
        return AuthenticationModel(
            authenticationResponse.success,
            authenticationResponse.token, authenticationResponse.errorCode?.toString()
        )
    }
}