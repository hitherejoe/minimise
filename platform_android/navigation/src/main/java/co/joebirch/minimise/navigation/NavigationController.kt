package co.joebirch.minimise.navigation

import co.joebirch.minimise.android.core.di.SingleLiveEvent

class NavigationController {

    var navigationCommands = SingleLiveEvent<NavigationCommand>()

    fun navigate(
        directions: NavigationDirection
    ) {
        navigationCommands.value = NavigationCommand.To(directions)
    }

}