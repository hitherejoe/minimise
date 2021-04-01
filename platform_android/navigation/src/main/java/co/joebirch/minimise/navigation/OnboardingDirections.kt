package co.joebirch.minimise.navigation

import androidx.navigation.compose.NamedNavArgument

object OnboardingDirections {

    val root = "onboarding"

    val authentication  = object : NavigationCommand {

        override val arguments = emptyList<NamedNavArgument>()

        override val destination = "authentication"

    }
}