package co.joebirch.minimise.authentication.mapper

import co.joebirch.firebase_auth_multiplatform.model.FirebaseAuthenticationResponse
import co.joebirch.minimise.authentication.model.AuthenticationModel

open class AuthenticationResponseMapper {

    fun mapFromAuthenticationResponse(
        authenticationResponse: FirebaseAuthenticationResponse
    ) = AuthenticationModel(
        authenticationResponse.idToken,
        authenticationResponse.message,
        authenticationResponse.code
    )
}