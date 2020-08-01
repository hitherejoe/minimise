package co.joebirch.minimise.shared_authentication.util

import co.joebirch.minimise.authentication.model.AuthenticationModel
import co.joebirch.minimise.authentication.remote.AuthenticationRemote

internal class MockAuthenticationStore : AuthenticationRemote {

    lateinit var whenSignUp: (
        email: String,
        password: String
    ) -> AuthenticationModel

    lateinit var whenSignIn: (
        email: String,
        password: String
    ) -> AuthenticationModel

    override suspend fun signUp(
        apiKey: String,
        email: String,
        password: String
    ) = whenSignUp(email, password)

    override suspend fun signIn(
        apiKey: String,
        email: String,
        password: String
    ) = whenSignIn(email, password)

    override fun resetPassword(
        apiKey: String,
        emailAddress: String) {
        TODO("Not yet implemented")
    }
}