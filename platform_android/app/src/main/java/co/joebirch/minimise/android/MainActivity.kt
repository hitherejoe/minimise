package co.joebirch.minimise.android

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.ui.viewinterop.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigate
import androidx.navigation.compose.rememberNavController
import co.joebirch.minimise.authentication.AuthenticationContentFactory
import co.joebirch.minimise.authentication.AuthenticationViewModel
import co.joebirch.minimise.authentication.reset_password.ForgotPasswordContentFactory
import co.joebirch.minimise.common_ui.MinimiseTheme
import co.joebirch.minimise.dashboard.DashboardContentFactory
import co.joebirch.minimise.dashboard.DashboardViewModel
import co.joebirch.minimise.navigation.AuthenticationDirections
import co.joebirch.minimise.navigation.NavigationController
import co.joebirch.minimise.navigation.OnboardingDirections
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var authenticationContentFactory: AuthenticationContentFactory
    @Inject
    lateinit var resetPasswordContentFactory: ForgotPasswordContentFactory
    @Inject
    lateinit var dashboardContentFactory: DashboardContentFactory

    @Inject
    lateinit var navigationController: NavigationController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setContent {
            MinimiseTheme {
                val navController = rememberNavController()
                navigationController.navigationCommands.observeAsState().value?.let {
                    navController.navigate(it.direction.getDestination())
                    navigationController.navigationCommands.value = null
                }
                NavHost(
                    navController,
                    startDestination = OnboardingDirections.Authentication.getDestination()
                ) {
                    composable(OnboardingDirections.Authentication.getDestination()) {
                        authenticationContentFactory.Content(
                            viewModel(
                                modelClass = AuthenticationViewModel::class.java,
                                factory = defaultViewModelProviderFactory
                            )
                        )
                    }
                    composable(AuthenticationDirections.ForgotPassword.getDestination()) {
                        resetPasswordContentFactory.Content(
                            viewModel(
                                factory = defaultViewModelProviderFactory
                            )
                        )
                    }
                    composable(AuthenticationDirections.Dashboard.getDestination()) {
                        dashboardContentFactory.Content(
                            viewModel(
                                modelClass = DashboardViewModel::class.java,
                                factory = defaultViewModelProviderFactory
                            )
                        )
                    }
                }
            }
        }
    }
}
