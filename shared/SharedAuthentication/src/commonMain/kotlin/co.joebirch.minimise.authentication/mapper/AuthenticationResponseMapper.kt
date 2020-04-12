package co.joebirch.minimise.authentication.mapper

import co.joebirch.minimise.authentication.firebase.model.FirebaseAuthenticationResponse
import co.joebirch.minimise.authentication.model.AuthenticationModel

open class AuthenticationResponseMapper {

    open fun mapFromAuthenticationResponse(
        authenticationResponse: FirebaseAuthenticationResponse
    ) = AuthenticationModel(
        authenticationResponse.idToken,
        authenticationResponse.message,
        authenticationResponse.code
    )
}