package co.joebirch.minimise.navigation

import androidx.navigation.compose.NamedNavArgument

object DashboardDirections {

    fun creation(
        navArguments: List<NamedNavArgument> = emptyList()
    ) = object : NavigationCommand {

        override val arguments = navArguments

        override val destination = "creation"

    }
}