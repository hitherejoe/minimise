package co.joebirch.minimise.shared_authentication

import co.joebirch.minimise.authentication.AuthenticationRepository
import co.joebirch.minimise.authentication.model.AuthenticationModel
import co.joebirch.minimise.authentication.model.ResetPasswordResponse

internal class MockAuthenticationDataRepository :
    AuthenticationRepository {

    lateinit var whenSignUp: (email: String, password: String) -> AuthenticationModel
    lateinit var whenSignIn: (email: String, password: String) -> AuthenticationModel
    lateinit var whenResetPassword: (apiKey: String, emailAddress: String) -> ResetPasswordResponse

    override suspend fun signUp(
        apiKey: String,
        emailAddress: String,
        password: String
    ) = whenSignUp(emailAddress, password)

    override suspend fun signIn(
        apiKey: String,
        emailAddress: String,
        password: String
    ) = whenSignIn(emailAddress, password)

    override suspend fun resetPassword(
        apiKey: String,
        emailAddress: String
    ) = whenResetPassword(apiKey, emailAddress)
}