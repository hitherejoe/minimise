package co.joebirch.minimise.navigation

sealed class NavigationCommand(val direction: NavigationDirection) {
    data class To(private val directions: NavigationDirection): NavigationCommand(directions)
}