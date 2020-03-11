package co.joebirch.minimise.navigation

import android.os.Bundle
import androidx.navigation.NavDirections

object OnboardingDirections {

    val Authentication = object : NavDirections {
        override fun getArguments() = Bundle()

        override fun getActionId() = R.id.action_onboardingFragment_to_authenticationFragment
    }
}