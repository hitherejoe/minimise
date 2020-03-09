package co.joebirch.minimise.authentication

sealed class AuthenticateMode {
    object SignUp: AuthenticateMode()
    object SignIn: AuthenticateMode()
}