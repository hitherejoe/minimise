package co.joebirch.minimise.shared_authentication

import co.joebirch.minimise.authentication_remote.AuthenticationRemote
import co.joebirch.minimise.shared_authentication.mapper.AuthenticationResponseMapper
import co.joebirch.minimise.shared_authentication.model.AuthenticationModel

class AuthenticationDataRepository constructor(
    private val authenticationRemote: AuthenticationRemote,
    private val authenticationResponseMapper: AuthenticationResponseMapper
) : AuthenticationRepository {

    override suspend fun signUp(
        emailAddress: String,
        password: String
    ): AuthenticationModel {
        return authenticationResponseMapper.mapFromAuthenticationResponse(
            authenticationRemote.signUp(emailAddress, password, true)
        )
    }

    override suspend fun signIn(
        emailAddress: String,
        password: String
    ): AuthenticationModel {
        return authenticationResponseMapper.mapFromAuthenticationResponse(
            authenticationRemote.signIn(emailAddress, password, true)
        )
    }
}