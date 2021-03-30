package co.joebirch.minimise.android

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltNavGraphViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigate
import androidx.navigation.compose.rememberNavController
import co.joebirch.minimise.authentication.AuthenticationUI
import co.joebirch.minimise.authentication.reset_password.ResetPasswordUI
import co.joebirch.minimise.common_ui.MinimiseTheme
import co.joebirch.minimise.dashboard.DashboardContentUI
import co.joebirch.minimise.navigation.AuthenticationDirections
import co.joebirch.minimise.navigation.NavigationManager
import co.joebirch.minimise.navigation.OnboardingDirections
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
                navigationManager.commands.collectAsState().value.destination.also { destination ->
                    navController.navigate(destination)
                }
                NavHost(
                    navController,
                    startDestination = OnboardingDirections.authentication().destination
                ) {
                    composable(OnboardingDirections.authentication().destination) {
                        AuthenticationUI(
                            navController.hiltNavGraphViewModel(
                                route = OnboardingDirections.authentication().destination
                            )
                        )
                    }
                    composable(AuthenticationDirections.forgotPassword().destination) {
                        ResetPasswordUI(
                            navController.hiltNavGraphViewModel(
                                route = AuthenticationDirections.forgotPassword().destination
                            )
                        )
                    }
                    composable(AuthenticationDirections.dashboard().destination) {
                        DashboardContentUI(
                            navController.hiltNavGraphViewModel(
                                route = AuthenticationDirections.dashboard().destination
                            )
                        )
                    }
                }
            }
        }
    }
}
