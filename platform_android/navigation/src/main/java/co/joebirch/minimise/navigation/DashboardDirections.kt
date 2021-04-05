package co.joebirch.minimise.navigation

import androidx.navigation.compose.NamedNavArgument

object DashboardDirections {

    val creation = object : NavigationCommand {

        override val arguments = emptyList<NamedNavArgument>()

        override val destination = "creation"

    }
}