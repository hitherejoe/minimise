package co.joebirch.minimise.shared_common.presentation

abstract class UiState(
    open val isLoading: Boolean,
    open val success: Boolean,
    open val errorMessage: String?
)