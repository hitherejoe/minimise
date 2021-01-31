package co.joebirch.minimise.navigation

import androidx.navigation.compose.NamedNavArgument

interface NavigationDirection {

    fun getArguments(): List<NamedNavArgument>

    fun getDestination(): String
}