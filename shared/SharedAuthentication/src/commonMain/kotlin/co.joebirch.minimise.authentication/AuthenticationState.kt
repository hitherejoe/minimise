package co.joebirch.minimise.authentication

data class AuthenticationState(
    val emailAddress: String = "",
    val password: String = "",
    val authenticationMode: AuthenticateMode = AuthenticateMode.SignUp,
    val isLoading: Boolean = false,
    val success: Boolean = false,
    val errorMessage: String? = null
) {

    fun build(block: Builder.() -> Unit) = Builder(this).apply(block).build()

    class Builder(uiModel: AuthenticationState) {
        var emailAddress = uiModel.emailAddress
        var password = uiModel.password
        var authenticateMode = uiModel.authenticationMode
        var isLoading = uiModel.isLoading
        var success = uiModel.success
        var errorMessage = uiModel.errorMessage

        fun build(): AuthenticationState {
            return AuthenticationState(
                emailAddress, password,
                authenticateMode, isLoading, success, errorMessage
            )
        }
    }
}