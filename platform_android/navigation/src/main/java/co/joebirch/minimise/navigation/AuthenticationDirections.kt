package co.joebirch.minimise.navigation

import android.os.Bundle
import androidx.navigation.NavDirections

object AuthenticationDirections {

    val Onboarding = object : NavDirections {
        override fun getArguments() = Bundle()

        override fun getActionId() = R.id.action_authenticationFragment_to_onboardingFragment
    }

    val ResetPassword = object : NavDirections {
        override fun getArguments() = Bundle()

        override fun getActionId() = R.id.action_authenticationFragment_to_forgottenPasswordFragment
    }

    val Dashboard = object : NavDirections {
        override fun getArguments() = Bundle()

        override fun getActionId() = R.id.action_authenticationFragment_to_dashboard
    }
}