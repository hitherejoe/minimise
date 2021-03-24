package co.joebirch.minimise.navigation

import kotlinx.coroutines.flow.MutableStateFlow

class NavigationController {

    var navigationCommands = MutableStateFlow<NavigationCommand>(NavigationCommand.Idle)

    fun navigate(
        directions: NavigationDirection
    ) {
        navigationCommands.value = NavigationCommand.To(directions)
    }

}