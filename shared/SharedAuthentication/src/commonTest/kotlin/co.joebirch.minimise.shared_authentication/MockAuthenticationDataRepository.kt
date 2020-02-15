package co.joebirch.minimise.shared_authentication

import co.joebirch.minimise.shared_authentication.model.AuthenticationModel

internal class MockAuthenticationDataRepository : AuthenticationRepository {

    lateinit var whenSignUp: (email: String, password: String) -> AuthenticationModel
    lateinit var whenSignIn: (email: String, password: String) -> AuthenticationModel

    override suspend fun signUp(emailAddress: String, password: String) =
        whenSignUp(emailAddress, password)

    override suspend fun signIn(emailAddress: String, password: String) =
        whenSignIn(emailAddress, password)
}