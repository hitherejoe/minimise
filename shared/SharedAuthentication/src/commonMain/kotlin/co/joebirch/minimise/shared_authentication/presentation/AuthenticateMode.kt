package co.joebirch.minimise.shared_authentication.presentation

sealed class AuthenticateMode {
    object SignUp: AuthenticateMode()
    object SignIn: AuthenticateMode()
}