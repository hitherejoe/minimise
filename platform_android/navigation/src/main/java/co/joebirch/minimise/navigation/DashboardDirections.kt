package co.joebirch.minimise.navigation

import android.os.Bundle
import androidx.navigation.NavDirections
import androidx.navigation.compose.NamedNavArgument

object DashboardDirections {

    val Creation = object : NavigationDirection {

        override fun getArguments() = emptyList<NamedNavArgument>()

        override fun getDestination() = "creation"

    }
}