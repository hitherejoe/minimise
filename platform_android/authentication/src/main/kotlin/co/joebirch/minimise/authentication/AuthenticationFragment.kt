package co.joebirch.minimise.authentication

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import co.joebirch.minimise.authentication.databinding.FragmentAuthenticationBinding
import co.joebirch.minimise.authentication.di.inject
import co.joebirch.minimise.navigation.navigateToOnboarding
import co.joebirch.minimise.shared_authentication.SharedAuthentication
import co.joebirch.minimise.shared_authentication.presentation.AuthenticateMode
import co.joebirch.minimise.shared_authentication.presentation.AuthenticationState
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import javax.inject.Inject

class AuthenticationFragment : Fragment() {

    private lateinit var binding: FragmentAuthenticationBinding
    private var authenticationMode: AuthenticateMode = AuthenticateMode.SIGN_UP
    @Inject lateinit var viewModel: AuthenticationViewModel
    @Inject lateinit var authenticatonValidator: AuthenticationValidator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        SharedAuthentication.initialize()
        inject(this)

        val callback: OnBackPressedCallback =
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    navigateToOnboarding(findNavController())
                }
            }
        requireActivity().onBackPressedDispatcher.addCallback(this, callback)

        viewModel.observeAuthenticationState().observe(this, Observer {
            when (it) {
                is AuthenticationState.Loading -> {
                    binding.authenticationGroup.visibility = View.INVISIBLE
                    binding.progress.visibility = View.VISIBLE
                }
                is AuthenticationState.Success -> {
                    //TODO: navigation to dashboard
                }
                is AuthenticationState.Failure -> {
                    binding.authenticationGroup.visibility = View.VISIBLE
                    binding.progress.visibility = View.INVISIBLE

                    MaterialAlertDialogBuilder(context)
                        .setTitle("Whoops!")
                        .setMessage(it.errorMessage)
                        .show()
                }
            }
        })
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
        handleAuthenticateButtonState()

        binding.authenticateButton.setOnClickListener {
            when (authenticationMode) {
                AuthenticateMode.SIGN_IN -> {
                    viewModel.signIn(
                        binding.emailInput.editText?.text.toString(),
                        binding.passwordInput.editText?.text.toString()
                    )
                }
                AuthenticateMode.SIGN_UP -> {
                    viewModel.signUp(
                        binding.emailInput.editText?.text.toString(),
                        binding.passwordInput.editText?.text.toString()
                    )
                }
            }
        }

        binding.emailInput.editText?.addTextChangedListener {
            handleAuthenticateButtonState()
        }
        binding.passwordInput.editText?.addTextChangedListener {
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
            binding.emailInput.editText?.text.toString(),
            binding.passwordInput.editText?.text.toString()
        )
    }

    private fun configureLayoutFor(authenticateMode: AuthenticateMode) {
        when (authenticateMode) {
            AuthenticateMode.SIGN_UP -> {
                binding.authenticateButton.text = getString(R.string.sign_up)
                binding.switchAuthenticationModeButton.text = getString(R.string.existing_account)
            }
            AuthenticateMode.SIGN_IN -> {
                binding.authenticateButton.text = getString(R.string.sign_in)
                binding.switchAuthenticationModeButton.text = getString(R.string.no_account)
            }
        }
    }
}
