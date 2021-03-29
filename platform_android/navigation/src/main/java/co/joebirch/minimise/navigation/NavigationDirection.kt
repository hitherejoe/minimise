package co.joebirch.minimise.navigation

import androidx.navigation.compose.NamedNavArgument

interface NavigationDirection {

    val arguments: List<NamedNavArgument>

    val destination: String
}