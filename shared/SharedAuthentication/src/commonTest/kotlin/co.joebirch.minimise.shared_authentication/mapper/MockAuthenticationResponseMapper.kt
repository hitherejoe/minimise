package co.joebirch.minimise.shared_authentication.mapper

import co.joebirch.firebase_auth_multiplatform.model.FirebaseAuthenticationResponse
import co.joebirch.minimise.authentication.mapper.AuthenticationResponseMapper
import co.joebirch.minimise.authentication.model.AuthenticationModel

internal class MockAuthenticationResponseMapper : AuthenticationResponseMapper() {

    lateinit var whenMapFromAuthenticationResponse:
                (response: FirebaseAuthenticationResponse) -> AuthenticationModel

}