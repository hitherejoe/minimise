package co.joebirch.minimise.shared_authentication.presentation

interface AuthenticateView {

    fun signUp(
        emailAddress: String,
        password: String
    )

    fun signIn(
        emailAddress: String,
        password: String
    )
}