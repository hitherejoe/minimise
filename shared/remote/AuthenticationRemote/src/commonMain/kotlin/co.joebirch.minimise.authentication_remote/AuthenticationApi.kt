package co.joebirch.minimise.authentication_remote

import co.joebirch.minimise.authentication_remote.model.AuthenticationResponse
import co.joebirch.minimise.remote.BaseApi
import co.joebirch.minimise.authentication_remote.model.FirebaseAuthenticationResponse
import io.ktor.client.HttpClient
import io.ktor.client.request.parameter
import io.ktor.client.request.post
import io.ktor.client.request.url
import io.ktor.client.statement.HttpStatement
import io.ktor.client.statement.readText
import io.ktor.http.HttpStatusCode
import kotlinx.serialization.json.Json

open class AuthenticationApi(
    private val apiKey: String,
    httpClient: HttpClient
): BaseApi(httpClient), AuthenticationRemote {

    override suspend fun signUp(
        email: String,
        password: String,
        returnSecureToken: Boolean
    ) = handleAuthenticationRequest(ENDPOINT_SIGN_UP, email, password,
        returnSecureToken)

    override suspend fun signIn(
        email: String,
        password: String,
        returnSecureToken: Boolean
    ) = handleAuthenticationRequest(ENDPOINT_SIGN_IN, email, password, returnSecureToken)

    private suspend fun handleAuthenticationRequest(
        endpoint: String,
        email: String,
        password: String,
        returnSecureToken: Boolean
    ): AuthenticationResponse {
        return try {
            val response = client.post<HttpStatement> {
                url(baseUrl + endpoint)
                parameter("key", apiKey)
                parameter("email", email)
                parameter("password", password)
                parameter("returnSecureToken", returnSecureToken)
            }.execute()

            if (response.status != HttpStatusCode.OK) {
                AuthenticationResponse(success = false, errorCode = response.status.value)
            } else {
                val jsonBody = response.readText()
                val auth = Json.parse(FirebaseAuthenticationResponse.serializer(), jsonBody)

                AuthenticationResponse(success = true, token = auth.idToken)
            }
        } catch (cause: Throwable) {
            AuthenticationResponse(false)
        }
    }

    companion object {
        private const val baseUrl = "https://identitytoolkit.googleapis.com/v1/accounts:"
        private const val ENDPOINT_SIGN_UP = "signUp"
        private const val ENDPOINT_SIGN_IN = "signInWithPassword"
    }
}