package co.joebirch.minimise.authentication

sealed class AuthenticationState(
    val emailAddress: String = "",
    val password: String = "",
    val authenticationMode: AuthenticateMode = AuthenticateMode.SignUp,
    val isLoading: Boolean = false,
    val success: Boolean? = null,
    val errorMessage: String? = null
) {

    data class Idle(
        val userEmail: String = "",
        val userPassword: String = "",
        val mode: AuthenticateMode = AuthenticateMode.SignUp
    ) : AuthenticationState(
        emailAddress = userEmail, password = userPassword,
        authenticationMode = mode
    )

    data class Loading(
        val userEmail: String = "",
        val userPassword: String = "",
        val mode: AuthenticateMode = AuthenticateMode.SignUp
    ) : AuthenticationState(isLoading = true, emailAddress = userEmail, password = userPassword)

    object Success : AuthenticationState()

    data class Error(
        val userEmail: String = "",
        val userPassword: String = "",
        val mode: AuthenticateMode = AuthenticateMode.SignUp,
        val message: String? = null
    ) : AuthenticationState(
        isLoading = false, emailAddress = userEmail, password = userPassword,
        authenticationMode = mode, errorMessage = message
    )

    fun build(block: Builder.() -> Unit) = Builder(this).apply(block).build()

    class Builder(uiModel: AuthenticationState) {
        private val currentState = uiModel
        var state = currentState
        var userEmail = currentState.emailAddress
        var userPassword = currentState.password
        var mode: AuthenticateMode = currentState.authenticationMode

        fun build(): AuthenticationState {
            return when (state) {
                is Loading -> Loading(userEmail, userPassword, mode)
                is Error -> Error(userEmail, userPassword, mode, state.errorMessage)
                is Success -> Success
                else -> Idle(userEmail, userPassword, mode)
            }
        }
    }
}