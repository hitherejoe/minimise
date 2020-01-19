package co.joebirch.minimise.navigation

import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController

fun Fragment.navigateToOnboarding() =
    findNavController().navigate(R.id.action_authenticationFragment_to_onboardingFragment)

fun Fragment.navigateToResetPassword() =
    findNavController().navigate(R.id.action_authenticationFragment_to_forgottenPasswordFragment)