package co.joebirch.minimise.navigation

import androidx.navigation.NavType
import androidx.navigation.compose.NamedNavArgument
import androidx.navigation.compose.navArgument

object AuthenticationDirections {

    val root = "home"

    val Root = object : NavigationCommand {

        override val arguments = emptyList<NamedNavArgument>()

        override val destination = "home"

    }

    val Default = object : NavigationCommand {

        override val arguments = emptyList<NamedNavArgument>()

        override val destination = ""

    }

    val dashboard = object : NavigationCommand {

        override val arguments = emptyList<NamedNavArgument>()

        override val destination = "dashboard"
    }

    fun forgotPassword(
        navArguments: List<NamedNavArgument> = emptyList()
    ) = object : NavigationCommand {

        override val arguments = navArguments

        override val destination = "forgot_password"

    }
}