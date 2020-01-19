package co.joebirch.minimise.navigation

import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController

fun Fragment.navigateToAuthentication() =
    findNavController().navigate(R.id.action_onboardingFragment_to_authenticationFragment)