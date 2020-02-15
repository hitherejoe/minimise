package co.joebirch.minimise.shared_authentication.mapper

import co.joebirch.minimise.authentication_remote.model.AuthenticationResponse
import co.joebirch.minimise.shared_authentication.model.AuthenticationModel

internal class MockAuthenticationResponseMapper : AuthenticationResponseMapper() {

    lateinit var whenMapFromAuthenticationResponse:
                (response: AuthenticationResponse) -> AuthenticationModel

}