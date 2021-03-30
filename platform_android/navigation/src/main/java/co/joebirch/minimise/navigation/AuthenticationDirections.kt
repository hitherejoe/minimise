package co.joebirch.minimise.navigation

import androidx.navigation.compose.NamedNavArgument

object AuthenticationDirections {

    val Default = object : NavigationCommand {

        override val arguments = emptyList<NamedNavArgument>()

        override val destination = ""

    }

    fun dashboard(
        navArguments: List<NamedNavArgument> = emptyList()
    ) = object : NavigationCommand {

        override val arguments = navArguments

        override val destination = "dashboard"
    }

    fun forgotPassword(
        navArguments: List<NamedNavArgument> = emptyList()
    ) = object : NavigationCommand {

        override val arguments = navArguments

        override val destination = "forgot_password"

    }
}