package co.joebirch.minimise.authentication_remote

import co.joebirch.minimise.authentication_remote.model.AuthenticationResponse
import co.joebirch.minimise.authentication_remote.model.FirebaseAuthenticationResponse
import io.ktor.http.HttpStatusCode
import kotlinx.serialization.json.Json

open class AuthenticationResponseMapper {

    open fun map(status: HttpStatusCode, response: String): AuthenticationResponse {
        return if (status != HttpStatusCode.OK) {
            AuthenticationResponse(success = false, errorCode = status.value)
        } else {
            val auth = Json.parse(FirebaseAuthenticationResponse.serializer(), response)
            AuthenticationResponse(success = true, token = auth.idToken)
        }
    }
}