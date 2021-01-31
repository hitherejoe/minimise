package co.joebirch.minimise.navigation

import androidx.navigation.compose.NamedNavArgument

object AuthenticationDirections {

    val Dashboard = object : NavigationDirection {

        override fun getArguments() = emptyList<NamedNavArgument>()

        override fun getDestination() = "dashboard"

    }

    val ForgotPassword = object : NavigationDirection {

        override fun getArguments() = emptyList<NamedNavArgument>()

        override fun getDestination() = "forgot_password"

    }
}