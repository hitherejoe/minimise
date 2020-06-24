package co.joebirch.minimise.authentication.forgot_password

class ForgotPasswordState(
    val emailAddress: String = "",
    val isLoading: Boolean = false,
    val success: Boolean? = null,
    val errorMessage: String? = null
) {

    companion object {
        fun initialise(): ForgotPasswordState = ForgotPasswordState()
    }

    fun build(block: Builder.() -> Unit) = Builder(this).apply(block).build()

    class Builder(state: ForgotPasswordState) {
        var userEmail = state.emailAddress
        var loading = state.isLoading
        var success = state.success
        var error = state.errorMessage

        fun build(): ForgotPasswordState {
            return ForgotPasswordState(
                userEmail,
                loading,
                success,
                error
            )
        }
    }
}