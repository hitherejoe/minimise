package co.joebirch.minimise.shared_authentication.mapper

import co.joebirch.minimise.authentication_remote.model.AuthenticationResponse
import co.joebirch.minimise.shared_authentication.model.AuthenticationModel

open class AuthenticationResponseMapper {

    fun mapFromAuthenticationResponse(
        authenticationResponse: AuthenticationResponse
    ) = AuthenticationModel(
        authenticationResponse.success,
        authenticationResponse.token,
        authenticationResponse.message,
        authenticationResponse.errorCode
    )
}