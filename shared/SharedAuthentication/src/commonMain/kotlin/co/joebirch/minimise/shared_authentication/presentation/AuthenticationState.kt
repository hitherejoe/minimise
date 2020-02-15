package co.joebirch.minimise.shared_authentication.presentation

sealed class AuthenticationState(
    val isLoading: Boolean = false,
    val success: Boolean = false,
    val errorMessage: String? = null
) {

    object Idle : AuthenticationState(isLoading = false)

    object Loading : AuthenticationState(isLoading = true)

    object Success : AuthenticationState(success = true)

    class Failure(error: String? = null) :
        AuthenticationState(success = false, errorMessage = error)

}