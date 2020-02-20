package co.joebirch.minimise.authentication_remote

import co.joebirch.minimise.authentication_remote.AuthenticationResponseFactory.makeAuthenticationResponse
import co.joebirch.minimise.authentication_remote.DataFactory.randomString
import co.joebirch.minimise.authentication_remote.model.FirebaseAuthenticationResponse
import io.ktor.client.HttpClient
import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.respond
import io.ktor.http.HttpStatusCode
import io.ktor.util.InternalAPI
import kotlinx.serialization.json.Json
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class AuthenticationApiTest {

    private val mockAuthenticationResponseMapper = MockAuthenticationResponseMapper()
    private val client = HttpClient(MockEngine) {
        engine { addHandler {
            respond(Json.stringify(FirebaseAuthenticationResponse.serializer(),
                FirebaseAuthenticationResponse()
            ), HttpStatusCode.OK)
        }}
    }

    private val authenticationApi = AuthenticationApi(randomString(),
        mockAuthenticationResponseMapper, client)

    @Test
    fun `Sign up returns expected response`() =
        runTest {
            val authenticationResponse = makeAuthenticationResponse()
            mockAuthenticationResponseMapper.whenMap = { _, _ -> authenticationResponse }

            val result = authenticationApi.signUp(randomString(), randomString(), true)
            assertEquals(authenticationResponse, result)
        }

    @Test
    fun `Sign in returns expected response`() =
        runTest {
            val authenticationResponse = makeAuthenticationResponse()
            mockAuthenticationResponseMapper.whenMap = { _, _ -> authenticationResponse }

            val result = authenticationApi.signIn(randomString(), randomString(), true)
            assertEquals(authenticationResponse, result)
        }

}