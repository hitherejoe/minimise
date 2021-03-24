package co.joebirch.minimise.navigation

sealed class NavigationCommand(val direction: NavigationDirection? = null) {
    data class To(private val directions: NavigationDirection) : NavigationCommand(directions)
    object Idle : NavigationCommand()
}