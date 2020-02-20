package co.joebirch.minimise.authentication_remote

import co.joebirch.minimise.authentication_remote.model.AuthenticationResponse
import io.ktor.http.HttpStatusCode

internal class MockAuthenticationResponseMapper : AuthenticationResponseMapper() {

    lateinit var whenMap: (status: HttpStatusCode, response: String) -> AuthenticationResponse

    override fun map(status: HttpStatusCode, response: String): AuthenticationResponse =
        whenMap(status, response)

}