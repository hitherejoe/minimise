package co.joebirch.minimise.authentication

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.fragment.app.Fragment
import co.joebirch.minimise.authentication.di.inject
import co.joebirch.minimise.authentication.util.AuthenticationValidator
import javax.inject.Inject

class AuthenticationFragment : Fragment() {

    @Inject lateinit var viewModel: AuthenticationViewModel
    @Inject lateinit var authenticatonValidator: AuthenticationValidator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        inject(this)
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
                viewModel.observeAuthenticationState(),
                viewModel::toggleAuthenticationMode,
                viewModel::authenticate,
                viewModel::setEmailAddress,
                viewModel::setPassword
            )
        }
    }
}
