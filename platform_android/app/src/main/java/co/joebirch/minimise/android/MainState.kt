package co.joebirch.minimise.android

sealed class MainState(
    val loading: Boolean = true,
    val isAuthenticated: Boolean = false
) {

    object Loading : MainState(loading = true)

    object Authenticated : MainState(isAuthenticated = true)

    object NotAuthenticated : MainState(isAuthenticated = false)

}