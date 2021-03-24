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
import co.joebirch.minimise.navigation.NavigationController
import co.joebirch.minimise.navigation.OnboardingDirections
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var navigationController: NavigationController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setContent {
            MinimiseTheme {
                val navController = rememberNavController()
                navigationController.navigationCommands.collectAsState().value.direction?.let { direction ->
                    navController.navigate(direction.getDestination())
                }
                NavHost(
                    navController,
                    startDestination = OnboardingDirections.Authentication.getDestination()
                ) {
                    composable(OnboardingDirections.Authentication.getDestination()) {
                        AuthenticationUI(
                            navController.hiltNavGraphViewModel(route = OnboardingDirections.Authentication.getDestination())
                        )
                    }
                    composable(AuthenticationDirections.ForgotPassword.getDestination()) {
                        ResetPasswordUI(
                            navController.hiltNavGraphViewModel(route = AuthenticationDirections.ForgotPassword.getDestination())
                        )
                    }
                    composable(AuthenticationDirections.Dashboard.getDestination()) {
                        DashboardContentUI(
                            navController.hiltNavGraphViewModel(route = AuthenticationDirections.Dashboard.getDestination())
                        )
                    }
                }
            }
        }
    }
}
