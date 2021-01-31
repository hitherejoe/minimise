package co.joebirch.minimise.navigation

import androidx.navigation.compose.NamedNavArgument

object OnboardingDirections {

    val Authentication = object : NavigationDirection {

        override fun getArguments() = emptyList<NamedNavArgument>()

        override fun getDestination() = "authentication"

    }
}