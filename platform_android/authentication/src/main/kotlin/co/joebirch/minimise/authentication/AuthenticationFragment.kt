package co.joebirch.minimise.authentication

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import co.joebirch.minimise.authentication.databinding.FragmentAuthenticationBinding
import co.joebirch.minimise.navigation.navigateToOnboarding
import co.joebirch.minimise.shared_authentication.SharedAuthentication
import co.joebirch.minimise.shared_authentication.presentation.AuthenticateMode

class AuthenticationFragment : Fragment() {

    private lateinit var binding: FragmentAuthenticationBinding
    private var authenticationMode: AuthenticateMode = AuthenticateMode.SIGN_UP
    private lateinit var viewModel: AuthenticationViewModel
    private lateinit var authenticatonValidator: AuthenticationValidator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        SharedAuthentication.initialize()

        val callback: OnBackPressedCallback =
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    navigateToOnboarding()
                }
            }
        requireActivity().onBackPressedDispatcher.addCallback(this, callback)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAuthenticationBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        configureLayoutFor(authenticationMode)

        binding.authenticateButton.setOnClickListener {
            when (authenticationMode) {
                AuthenticateMode.SIGN_IN -> {
                    viewModel.signIn(
                        binding.textInputLayout.editText?.text.toString(),
                        binding.textInputLayout2.editText?.text.toString()
                    )
                }
                AuthenticateMode.SIGN_UP -> {
                    viewModel.signUp(
                        binding.textInputLayout.editText?.text.toString(),
                        binding.textInputLayout2.editText?.text.toString()
                    )
                }
            }
        }

        binding.textInputLayout.editText?.addTextChangedListener {
            handleAuthenticateButtonState()
        }
        binding.textInputLayout2.editText?.addTextChangedListener {
            handleAuthenticateButtonState()
        }

        binding.switchAuthenticationModeButton.setOnClickListener {
            authenticationMode = when (authenticationMode) {
                AuthenticateMode.SIGN_IN -> {
                    AuthenticateMode.SIGN_UP
                }
                AuthenticateMode.SIGN_UP -> {
                    AuthenticateMode.SIGN_IN
                }
            }
            configureLayoutFor(authenticationMode)
        }
    }

    private fun handleAuthenticateButtonState() {
        binding.authenticateButton.isEnabled = authenticatonValidator.validateInputData(
            binding.textInputLayout.editText?.text.toString(),
            binding.textInputLayout2.editText?.text.toString()
        )
    }

    private fun configureLayoutFor(authenticateMode: AuthenticateMode) {
        when (authenticateMode) {
            AuthenticateMode.SIGN_UP -> {
                binding.authenticateButton.text = "Sign Up"
                binding.switchAuthenticationModeButton.text = "Already have an account?"
            }
            AuthenticateMode.SIGN_IN -> {
                binding.authenticateButton.text = "Sign In"
                binding.switchAuthenticationModeButton.text = "Don't have an account yet?"
            }
        }
    }
}
