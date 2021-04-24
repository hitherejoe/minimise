package co.joebirch.minimise.android

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltNavGraphViewModel
import androidx.navigation.compose.*
import co.joebirch.minimise.authentication.Authentication
import co.joebirch.minimise.authentication.reset_password.ResetPasswordUI
import co.joebirch.minimise.common_ui.MinimiseTheme
import co.joebirch.minimise.creation.Creation
import co.joebirch.minimise.dashboard.Dashboard
import co.joebirch.minimise.navigation.AuthenticationDirections
import co.joebirch.minimise.navigation.DashboardDirections
import co.joebirch.minimise.navigation.NavigationManager
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var navigationManager: NavigationManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setContent {
            MinimiseTheme {
                val navController = rememberNavController()
                navigationManager.commands.collectAsState().value.also { command ->
                    if (command.destination.isNotEmpty()) {
                        navController.navigate(command.destination)
                    }
                }
                NavHost(
                    navController,
                    startDestination = AuthenticationDirections.root.destination
                ) {
                    navigation(
                        startDestination = AuthenticationDirections.authentication.destination,
                        route = AuthenticationDirections.root.destination
                    ) {
                        composable(AuthenticationDirections.authentication.destination) {
                            Authentication(
                                navController.hiltNavGraphViewModel(
                                    route = AuthenticationDirections.authentication.destination
                                )
                            )
                        }
                        composable(AuthenticationDirections.forgotPassword.destination) {
                            ResetPasswordUI(
                                navController.hiltNavGraphViewModel(
                                    route = AuthenticationDirections.forgotPassword.destination
                                )
                            )
                        }
                    }
                    navigation(
                        startDestination = AuthenticationDirections.dashboard.destination,
                        route = DashboardDirections.root.destination
                    ) {
                        composable(AuthenticationDirections.dashboard.destination) {
                            Dashboard(
                                navController.hiltNavGraphViewModel(
                                    route = AuthenticationDirections.dashboard.destination
                                )
                            )
                        }
                        composable(DashboardDirections.creation.destination) {
                            Creation(
                                navController.hiltNavGraphViewModel(
                                    route = DashboardDirections.creation.destination
                                )
                            )
                        }
                    }
                }
            }
        }
    }
}
