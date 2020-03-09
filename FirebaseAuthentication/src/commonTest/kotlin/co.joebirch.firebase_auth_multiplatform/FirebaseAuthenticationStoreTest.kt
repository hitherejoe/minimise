package co.joebirch.firebase_auth_multiplatform

import co.joebirch.firebase_auth_multiplatform.AuthenticationResponseFactory.makeAnonymousSignInResponse
import co.joebirch.firebase_auth_multiplatform.AuthenticationResponseFactory.makeAuthenticationResponse
import co.joebirch.firebase_auth_multiplatform.AuthenticationResponseFactory.makeCustomTokenSignInResponse
import co.joebirch.firebase_auth_multiplatform.AuthenticationResponseFactory.makeDeleteUserResponse
import co.joebirch.firebase_auth_multiplatform.AuthenticationResponseFactory.makePasswordResetResponse
import co.joebirch.firebase_auth_multiplatform.AuthenticationResponseFactory.makeResetPasswordResponse
import co.joebirch.firebase_auth_multiplatform.AuthenticationResponseFactory.makeUpdateUserResponse
import co.joebirch.firebase_auth_multiplatform.AuthenticationResponseFactory.makeUserResponse
import co.joebirch.firebase_auth_multiplatform.DataFactory.randomBoolean
import co.joebirch.firebase_auth_multiplatform.DataFactory.randomString
import co.joebirch.firebase_auth_multiplatform.model.FirebaseAnonymousAuthenticationResponse
import co.joebirch.firebase_auth_multiplatform.model.FirebaseAuthenticationResponse
import co.joebirch.firebase_auth_multiplatform.model.*
import io.ktor.client.HttpClient
import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.respond
import io.ktor.http.HttpStatusCode
import kotlinx.serialization.json.Json
import kotlin.test.Test
import kotlin.test.assertEquals

class FirebaseAuthenticationStoreTest {

    @Test
    fun `Sign up returns expected response`() =
        runTest {
            val authenticationResponse = makeAuthenticationResponse()

            val authenticationApi = createStubbedAuthenticationStore(
                Json.stringify(
                    FirebaseAuthenticationResponse.serializer(),
                    authenticationResponse
                )
            )

            authenticationApi.signUp(
                randomString(), randomString(), randomString(),
                returnSecureToken = true
            ).also { result ->
                assertEquals(authenticationResponse, result)
            }
        }

    @Test
    fun `Sign in returns expected response`() =
        runTest {
            val authenticationResponse = makeAuthenticationResponse()

            val authenticationApi = createStubbedAuthenticationStore(
                Json.stringify(
                    FirebaseAuthenticationResponse.serializer(),
                    authenticationResponse
                )
            )

            authenticationApi.signIn(
                randomString(), randomString(), randomString(),
                returnSecureToken = true
            ).also { result ->
                assertEquals(authenticationResponse, result)
            }
        }

    @Test
    fun `Sign in with custom token returns expected response`() =
        runTest {
            val authenticationResponse = makeCustomTokenSignInResponse()

            val authenticationApi = createStubbedAuthenticationStore(
                Json.stringify(
                    FirebaseCustomTokenSignInResponse.serializer(),
                    authenticationResponse
                )
            )

            authenticationApi.signInWithCustomToken(
                randomString(), randomString(), randomBoolean()
            ).also { result ->
                assertEquals(authenticationResponse, result)
            }
        }

    @Test
    fun `Sign in anonymously returns expected response`() =
        runTest {
            val authenticationResponse = makeAnonymousSignInResponse()

            val authenticationApi = createStubbedAuthenticationStore(
                Json.stringify(
                    FirebaseAnonymousAuthenticationResponse.serializer(),
                    authenticationResponse
                )
            )

            authenticationApi.signInAnonymously(
                randomString(), randomBoolean()
            ).also { result ->
                assertEquals(authenticationResponse, result)
            }
        }

    @Test
    fun `Reset password returns expected response`() =
        runTest {
            val authenticationResponse = makeResetPasswordResponse()

            val authenticationApi = createStubbedAuthenticationStore(
                Json.stringify(
                    FirebasePasswordResetResponse.serializer(),
                    authenticationResponse
                )
            )

            authenticationApi.resetPassword(
                randomString(), randomString()
            ).also { result ->
                assertEquals(authenticationResponse, result)
            }
        }

    @Test
    fun `Update email returns expected response`() =
        runTest {
            val authenticationResponse = makeUpdateUserResponse()

            val authenticationApi = createStubbedAuthenticationStore(
                Json.stringify(
                    FirebaseUpdateUserResponse.serializer(),
                    authenticationResponse
                )
            )

            authenticationApi.updateEmail(
                randomString(), randomString(), randomString()
            ).also { result ->
                assertEquals(authenticationResponse, result)
            }
        }

    @Test
    fun `Update password returns expected response`() =
        runTest {
            val authenticationResponse = makeUpdateUserResponse()

            val authenticationApi = createStubbedAuthenticationStore(
                Json.stringify(
                    FirebaseUpdateUserResponse.serializer(),
                    authenticationResponse
                )
            )

            authenticationApi.updatePassword(
                randomString(), randomString(), randomString()
            ).also { result ->
                assertEquals(authenticationResponse, result)
            }
        }

    @Test
    fun `Verify reset password returns expected response`() =
        runTest {
            val authenticationResponse = makePasswordResetResponse()

            val authenticationApi = createStubbedAuthenticationStore(
                Json.stringify(
                    FirebasePasswordResetResponse.serializer(),
                    authenticationResponse
                )
            )

            authenticationApi.verifyResetPassword(
                randomString(), randomString()
            ).also { result ->
                assertEquals(authenticationResponse, result)
            }
        }

    @Test
    fun `Confirm reset password returns expected response`() =
        runTest {
            val authenticationResponse = makePasswordResetResponse()

            val authenticationApi = createStubbedAuthenticationStore(
                Json.stringify(
                    FirebasePasswordResetResponse.serializer(),
                    authenticationResponse
                )
            )

            authenticationApi.confirmResetPassword(
                randomString(), randomString(), randomString()
            ).also { result ->
                assertEquals(authenticationResponse, result)
            }
        }

    @Test
    fun `Retrieve user returns expected response`() =
        runTest {
            val authenticationResponse = makeUserResponse()

            val authenticationApi = createStubbedAuthenticationStore(
                Json.stringify(
                    FirebaseUserResponse.serializer(),
                    authenticationResponse
                )
            )

            authenticationApi.retrieveUser(
                randomString(), randomString()
            ).also { result ->
                assertEquals(authenticationResponse, result)
            }
        }

    @Test
    fun `Delete user returns expected response`() =
        runTest {
            val authenticationResponse = makeDeleteUserResponse()

            val authenticationApi = createStubbedAuthenticationStore(
                Json.stringify(
                    FirebaseDeleteUserResponse.serializer(),
                    authenticationResponse
                )
            )

            authenticationApi.deleteUser(
                randomString(), randomString()
            ).also { result ->
                assertEquals(authenticationResponse, result)
            }
        }

    private fun createStubbedAuthenticationStore(
        response: String
    ): FirebaseAuthenticationStore {
        val client = HttpClient(MockEngine) {
            engine {
                addHandler {
                    respond(response, HttpStatusCode.OK)
                }
            }
        }
        return FirebaseAuthenticationStore(
            client
        )
    }
}