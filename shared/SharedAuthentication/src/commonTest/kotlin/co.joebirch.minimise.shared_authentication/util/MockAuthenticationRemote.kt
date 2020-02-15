package co.joebirch.minimise.shared_authentication.util

import co.joebirch.minimise.authentication_remote.AuthenticationRemote
import co.joebirch.minimise.authentication_remote.model.AuthenticationResponse

internal class MockAuthenticationRemote : AuthenticationRemote {

    lateinit var whenSignUp: (email: String, password: String, returnSecureToken: Boolean) -> AuthenticationResponse
    lateinit var whenSignIn: (email: String, password: String, returnSecureToken: Boolean) -> AuthenticationResponse

    override suspend fun signUp(
        email: String,
        password: String,
        returnSecureToken: Boolean
    ) = whenSignUp(email, password, returnSecureToken)

    override suspend fun signIn(
        email: String,
        password: String,
        returnSecureToken: Boolean
    ) = whenSignIn(email, password, returnSecureToken)
}