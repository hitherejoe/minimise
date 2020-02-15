package co.joebirch.minimise.navigation

import androidx.navigation.NavController

fun navigateToOnboarding(navController: NavController) =
    navController.navigate(R.id.action_authenticationFragment_to_onboardingFragment)

fun navigateToResetPassword(navController: NavController) =
    navController.navigate(R.id.action_authenticationFragment_to_forgottenPasswordFragment)