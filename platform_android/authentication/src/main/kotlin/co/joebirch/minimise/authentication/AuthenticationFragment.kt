package co.joebirch.minimise.authentication

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import co.joebirch.minimise.android.core.di.BaseFragment
import co.joebirch.minimise.android.core.di.ViewModelFactory
import co.joebirch.minimise.authentication.di.inject
import co.joebirch.minimise.authentication.util.AuthenticationValidator
import javax.inject.Inject
import androidx.fragment.app.viewModels
import co.joebirch.minimise.navigation.AuthenticationDirections

class AuthenticationFragment : BaseFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    @Inject
    lateinit var authenticatonValidator: AuthenticationValidator

    private val authenticationViewModel: AuthenticationViewModel by viewModels {
        viewModelFactory
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        inject(this)
        viewModel = authenticationViewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return FrameLayout(requireContext()).apply {
            layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )

            composeAuthenticationContent(
                viewLifecycleOwner,
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
