package co.joebirch.minimise.navigation

import androidx.navigation.compose.NamedNavArgument

object OnboardingDirections {

    fun authentication(
        navArguments: List<NamedNavArgument> = emptyList()
    ) = object : NavigationCommand {

        override val arguments = navArguments

        override val destination = "authentication"

    }
}