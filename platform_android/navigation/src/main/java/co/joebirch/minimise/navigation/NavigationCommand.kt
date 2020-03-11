package co.joebirch.minimise.navigation

import androidx.navigation.NavDirections

sealed class NavigationCommand {
    data class ToAndFinishActivity(val directions: NavDirections): NavigationCommand()
    data class To(val directions: NavDirections): NavigationCommand()
    object Back: NavigationCommand()
    data class BackTo(val destinationId: Int): NavigationCommand()
    object ToRoot: NavigationCommand()
}