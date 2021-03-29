package co.joebirch.minimise.navigation

import androidx.navigation.compose.NamedNavArgument

object OnboardingDirections {

    val Authentication = object : NavigationDirection {

        override val arguments = emptyList<NamedNavArgument>()

        override val destination = "authentication"

    }
}