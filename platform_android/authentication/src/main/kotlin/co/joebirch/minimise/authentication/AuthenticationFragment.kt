package co.joebirch.minimise.authentication

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import co.joebirch.minimise.android.core.di.BaseFragment
import co.joebirch.minimise.authentication.util.AuthenticationValidator
import javax.inject.Inject
import androidx.fragment.app.viewModels
import co.joebirch.minimise.navigation.AuthenticationDirections
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AuthenticationFragment : BaseFragment() {

    @Inject
    lateinit var authenticatonValidator: AuthenticationValidator

    private val authenticationViewModel: AuthenticationViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = authenticationViewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        return FrameLayout(requireContext()).apply {
            layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
            composeAuthenticationContentTwo(
                authenticationViewModel.observeAuthenticationState(),
                authenticationViewModel::toggleAuthenticationMode,
                authenticationViewModel::authenticate,
                { authenticationViewModel.navigate(AuthenticationDirections.ResetPassword) },
                authenticationViewModel::setEmailAddress,
                authenticationViewModel::setPassword
            )
        }
    }
}
