package co.joebirch.minimise.shared_authentication.util

import co.joebirch.minimise.authentication.remote.AuthenticationRemote

internal class MockAuthenticationStore : AuthenticationRemote {

    lateinit var whenSignUp: (
        email: String,
        password: String
    ) -> Any

    lateinit var whenSignIn: (
        email: String,
        password: String
    ) -> Any

    override fun signUp(
        apiKey: String,
        email: String,
        password: String
    ) {

    }

    override fun signIn(
        apiKey: String,
        email: String,
        password: String
    ) {

    }

    override fun resetPassword(apiKey: String, emailAddress: String) {
        TODO("Not yet implemented")
    }
}