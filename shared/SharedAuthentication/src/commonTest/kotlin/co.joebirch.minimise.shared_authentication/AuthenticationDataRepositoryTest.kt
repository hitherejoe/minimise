package co.joebirch.minimise.shared_authentication

import co.joebirch.minimise.shared_authentication.mapper.MockAuthenticationResponseMapper
import co.joebirch.minimise.shared_authentication.util.AuthenticationResponseFactory.makeAuthenticationModel
import co.joebirch.minimise.shared_authentication.util.AuthenticationResponseFactory.makeAuthenticationResponse
import co.joebirch.minimise.shared_authentication.util.DataFactory.randomString
import co.joebirch.minimise.shared_authentication.util.MockAuthenticationRemote
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class AuthenticationDataRepositoryTest {

    private val mockAuthenticationRemote =
        MockAuthenticationRemote()
    private val mockAuthenticationMapper = MockAuthenticationResponseMapper()
    private val authenticationRepository = AuthenticationDataRepository(
        mockAuthenticationRemote, mockAuthenticationMapper)

    @Test
    fun `Sign up succeeds and returns data`() =
        runTest {
            val authenticationModel = makeAuthenticationModel()
            val authenticationResponse = makeAuthenticationResponse(authenticationModel.token!!)
            mockAuthenticationMapper.whenMapFromAuthenticationResponse =
                { _ -> authenticationModel }
            mockAuthenticationRemote.whenSignUp = { _, _, _ -> authenticationResponse }

            val result = authenticationRepository.signUp(randomString(), randomString())
            assertEquals(authenticationModel, result)
        }

    @Test
    fun `Sign in succeeds and returns data`() =
        runTest {
            val authenticationModel = makeAuthenticationModel()
            val authenticationResponse = makeAuthenticationResponse(authenticationModel.token!!)

            mockAuthenticationMapper.whenMapFromAuthenticationResponse =
                { _ -> authenticationModel }
            mockAuthenticationRemote.whenSignIn = { _, _, _ -> authenticationResponse }

            val result = authenticationRepository.signIn(randomString(), randomString())
            assertEquals(authenticationModel, result)
        }
}