package co.joebirch.minimise.authentication

sealed class AuthenticationEvent {

    object AuthenticationModeToggled : AuthenticationEvent()
    object DismissErrorDialog : AuthenticationEvent()
    object AuthenticateClicked : AuthenticationEvent()
    object ForgotPasswordClicked : AuthenticationEvent()
    data class EmailChanged(val email: String) : AuthenticationEvent()
    data class PasswordChanged(val password: String) : AuthenticationEvent()

}