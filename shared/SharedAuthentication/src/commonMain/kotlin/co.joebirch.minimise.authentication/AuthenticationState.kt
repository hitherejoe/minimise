package co.joebirch.minimise.authentication

sealed class AuthenticationState(
    val emailAddress: String = "",
    val password: String = "",
    val authenticationMode: AuthenticateMode = AuthenticateMode.SignUp,
    val isLoading: Boolean = false,
    val success: Boolean? = null,
    val errorMessage: String? = null
) {

    object Idle : AuthenticationState()

    data class Loading(
        val userEmail: String,
        val userPassword: String,
        val mode: AuthenticateMode
    ) : AuthenticationState(isLoading = true, emailAddress = userEmail, password = userPassword)

    object Success : AuthenticationState()

    data class Error(
        val userEmail: String,
        val userPassword: String,
        val mode: AuthenticateMode,
        val message: String? = null
    ) : AuthenticationState(
        isLoading = false, emailAddress = userEmail, password = userPassword,
        authenticationMode = mode, errorMessage = message
    )

    fun build(block: Builder.() -> Unit) = Builder(this).apply(block).build()

    class Builder(uiModel: AuthenticationState) {
        var emailAddress = uiModel.emailAddress
        var password = uiModel.password
        var authenticateMode = uiModel.authenticationMode
        var isLoading = uiModel.isLoading
        var success = uiModel.success
        var errorMessage = uiModel.errorMessage

        fun build(): AuthenticationState {
            return if (isLoading) {
                Loading(emailAddress, password, authenticateMode)
            } else if (errorMessage != null || success == false) {
                Error(emailAddress, password, authenticateMode, errorMessage)
            } else if (success == true) {
                Success
            } else {
                Idle
            }
        }
    }
}