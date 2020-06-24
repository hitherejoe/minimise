package co.joebirch.minimise.authentication.mapper

import co.joebirch.minimise.authentication.model.AuthenticationModel
import co.joebirch.minimise.authentication.model.AuthenticationResponseModel

open class AuthenticationResponseMapper {

    fun mapFromAuthenticationResponse(
        authenticationResponse: AuthenticationResponseModel? = null
    ) = AuthenticationModel(
       // authenticationResponse.idToken,
       // authenticationResponse.message,
       // authenticationResponse.code
    )
}