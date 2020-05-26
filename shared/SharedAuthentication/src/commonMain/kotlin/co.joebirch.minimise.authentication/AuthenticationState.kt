package co.joebirch.minimise.authentication

class AuthenticationState(
    val emailAddress: String = "",
    val password: String = "",
    val authenticationMode: AuthenticateMode = AuthenticateMode.SignUp,
    val isLoading: Boolean = false,
    val success: Boolean? = null,
    val errorMessage: String? = null
) {

    fun build(block: Builder.() -> Unit) = Builder(this).apply(block).build()

    class Builder(uiModel: AuthenticationState) {
        private val currentState = uiModel
        var userEmail = currentState.emailAddress
        var userPassword = currentState.password
        var mode: AuthenticateMode = currentState.authenticationMode
        var loading = currentState.isLoading
        var success = currentState.success
        var error = currentState.errorMessage

        fun build(): AuthenticationState {
            return AuthenticationState(
                userEmail,
                userPassword,
                mode,
                loading,
                success,
                error
            )
        }
    }
}