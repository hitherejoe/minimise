package co.joebirch.minimise.authentication

import co.joebirch.minimise.shared_common.presentation.UiState

sealed class ResetPasswordState(
    override val isLoading: Boolean = false,
    override val success: Boolean = false,
    override val errorMessage: String? = null
): UiState(isLoading, success, errorMessage) {

    object Idle : ResetPasswordState(isLoading = false)

    object Loading : ResetPasswordState(isLoading = true)

    object Success : ResetPasswordState(success = true)

    class Failure(error: String? = null) :
        ResetPasswordState(success = false, errorMessage = error)

}