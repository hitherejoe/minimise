package co.joebirch.minimise.navigation

import androidx.navigation.compose.NamedNavArgument

object AuthenticationDirections {

    val Default = object : NavigationDirection {

        override val arguments = emptyList<NamedNavArgument>()

        override val destination = ""

    }

    val Dashboard = object : NavigationDirection {

        override val arguments = emptyList<NamedNavArgument>()

        override val destination = "dashboard"

    }

    val ForgotPassword = object : NavigationDirection {

        override val arguments = emptyList<NamedNavArgument>()

        override val destination = "forgot_password"

    }
}