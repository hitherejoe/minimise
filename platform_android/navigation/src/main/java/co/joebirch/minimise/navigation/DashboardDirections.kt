package co.joebirch.minimise.navigation

import android.os.Bundle
import androidx.navigation.NavDirections

object DashboardDirections {

    val Creation = object : NavDirections {
        override fun getArguments() = Bundle()

        override fun getActionId() = R.id.action_inventoryFragment_to_creationFragment
    }
}