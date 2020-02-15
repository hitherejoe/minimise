package co.joebirch.minimise.shared_authentication.interactor

import co.joebirch.minimise.shared_authentication.MockAuthenticationDataRepository
import co.joebirch.minimise.shared_authentication.runTest
import co.joebirch.minimise.shared_authentication.util.AuthenticationResponseFactory.makeAuthenticationModel
import co.joebirch.minimise.shared_authentication.util.DataFactory.randomString
import kotlin.test.Test
import kotlin.test.assertEquals

class AuthTest {

    private val mockAuthenticationDataRepository = MockAuthenticationDataRepository()
    private val authenticationRepository = Authenticate(mockAuthenticationDataRepository)

    @Test
    fun `Sign up succeeds and returns data`() =
        runTest {
            val authenticationModel = makeAuthenticationModel()
            mockAuthenticationDataRepository.whenSignUp = { _, _ -> authenticationModel }

            authenticationRepository.run(
                Authenticate.Params.forSignUp(
                    randomString(), randomString()
                )
            ) {
                assertEquals(authenticationModel, it)
            }
        }

    @Test
    fun `Sign in succeeds and returns data`() =
        runTest {
            val authenticationModel = makeAuthenticationModel()
            mockAuthenticationDataRepository.whenSignIn = { _, _ -> authenticationModel }

            authenticationRepository.run(
                Authenticate.Params.forSignUp(
                    randomString(), randomString()
                )
            ) {
                assertEquals(authenticationModel, it)
            }
        }
}