package co.joebirch.minimise.shared_authentication

import co.joebirch.minimise.authentication.AuthenticationDataRepository
import co.joebirch.minimise.shared_authentication.mapper.MockResetPasswordResponseMapper
import co.joebirch.minimise.shared_authentication.util.AuthenticationResponseFactory.makeAuthenticationModel
import co.joebirch.minimise.shared_authentication.util.AuthenticationResponseFactory.makeAuthenticationResponse
import co.joebirch.minimise.shared_authentication.util.DataFactory.randomString
import co.joebirch.minimise.shared_authentication.util.MockAuthenticationStore
import kotlin.test.Test
import kotlin.test.assertEquals

class AuthenticationDataRepositoryTest {

    private val mockAuthenticationRemote =
        MockAuthenticationStore()
    private val mockPasswordResponseMapper = MockResetPasswordResponseMapper()
    private val authenticationRepository =
        AuthenticationDataRepository(
            mockAuthenticationRemote, mockPasswordResponseMapper
        )

    @Test
    fun `Sign up succeeds and returns data`() =
        runTest {
            val authenticationModel = makeAuthenticationModel()

            mockAuthenticationRemote.whenSignUp = { _, _ -> authenticationModel }

            val result = authenticationRepository.signUp(randomString(), randomString(),
                randomString())
            assertEquals(authenticationModel, result)
        }

    @Test
    fun `Sign in succeeds and returns data`() =
        runTest {
            val authenticationModel = makeAuthenticationModel()

            mockAuthenticationRemote.whenSignIn = { _, _ -> authenticationModel }

            val result = authenticationRepository.signIn(randomString(), randomString(),
                randomString())
            assertEquals(authenticationModel, result)
        }
}