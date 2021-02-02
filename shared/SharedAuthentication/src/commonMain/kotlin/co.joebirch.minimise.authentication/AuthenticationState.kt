package co.joebirch.minimise.authentication

class AuthenticationState(
    val emailAddress: String = "",
    val password: String = "",
    val isAuthenticationContentValid: Boolean = false,
    val authenticationMode: AuthenticateMode = AuthenticateMode.SignUp,
    val isLoading: Boolean = false,
    val success: Boolean? = null,
    val errorMessage: String? = null
) {

    companion object {
        fun initialise(): AuthenticationState = AuthenticationState()
    }

    fun build(block: Builder.() -> Unit) = Builder(this).apply(block).build()

    class Builder(state: AuthenticationState) {
        var userEmail = state.emailAddress
        var userPassword = state.password
        var isAuthenticationContentValid = userEmail.trim().isNotEmpty() &&
                userPassword.trim().isNotEmpty()
        var mode: AuthenticateMode = state.authenticationMode
        var loading = state.isLoading
        var success = state.success
        var error = state.errorMessage

        fun build(): AuthenticationState {
            return AuthenticationState(
                userEmail,
                userPassword,
                isAuthenticationContentValid,
                mode,
                loading,
                success,
                error
            )
        }
    }
}