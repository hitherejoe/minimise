package co.joebirch.minimise.navigation

import androidx.navigation.NavController

fun navigateToAuthentication(navController: NavController) =
    navController.navigate(R.id.action_onboardingFragment_to_authenticationFragment)