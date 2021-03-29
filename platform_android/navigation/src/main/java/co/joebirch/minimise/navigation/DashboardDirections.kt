package co.joebirch.minimise.navigation

import androidx.navigation.compose.NamedNavArgument

object DashboardDirections {

    val Creation = object : NavigationDirection {

        override val arguments = emptyList<NamedNavArgument>()

        override val destination = "creation"

    }
}