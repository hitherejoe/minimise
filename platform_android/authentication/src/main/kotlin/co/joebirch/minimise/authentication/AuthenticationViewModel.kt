package co.joebirch.minimise.authentication

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.joebirch.minimise.android.core.di.BaseViewModel
import co.joebirch.minimise.android.core.di.default
import co.joebirch.minimise.authentication.BuildConfig.FIREBASE_API_KEY
import co.joebirch.minimise.authentication.interactor.Authenticate
import co.joebirch.minimise.authentication.model.AuthenticationModel
import co.joebirch.minimise.navigation.AuthenticationDirections
import kotlinx.coroutines.launch
import javax.inject.Inject

class AuthenticationViewModel @Inject constructor(
    private val authenticate: Authenticate
) : BaseViewModel(), AuthenticateView {

    private val uiState =
        MutableLiveData<AuthenticationState>().default(
            AuthenticationState.Idle
        )

    fun observeAuthenticationState(): LiveData<AuthenticationState> = uiState

    override fun toggleAuthenticationMode() {
        if (uiState.value!!.authenticationMode == AuthenticateMode.SignIn) {
            uiState.postValue(
                uiState.value!!.build {
                    authenticateMode = AuthenticateMode.SignUp
                }
            )
        } else {
            uiState.postValue(
                uiState.value!!.build {
                    authenticateMode = AuthenticateMode.SignIn
                }
            )
        }
    }

    override fun setEmailAddress(emailAddress: String) {
        uiState.postValue(
            uiState.value!!.build {
                this.emailAddress = emailAddress
            }
        )
    }

    override fun setPassword(password: String) {
        uiState.postValue(
            uiState.value!!.build {
                this.password = password
            }
        )
    }

    override fun authenticate() {
        if (uiState.value?.authenticationMode == AuthenticateMode.SignIn) {
            signUp()
        } else {
            signIn()
        }
    }

    override fun signUp() {
        uiState.value!!.build {
            errorMessage = null
            isLoading = true
        }
        viewModelScope.launch {
            authenticate.run(
                Authenticate.Params.forSignUp(
                    FIREBASE_API_KEY,
                    uiState.value!!.emailAddress, uiState.value!!.password
                )
            ) { result ->
                handleResult(result)
            }
        }
    }

    override fun signIn() {
        uiState.value!!.build {
            errorMessage = null
            isLoading = true
        }
        viewModelScope.launch {
            authenticate.run(
                Authenticate.Params.forSignIn(
                    FIREBASE_API_KEY,
                    uiState.value!!.emailAddress, uiState.value!!.password
                )
            ) { result ->
                handleResult(result)
            }
        }
    }

    private fun handleResult(result: AuthenticationModel) {
        if (result.token != null) {
            uiState.value!!.build {
                isLoading = false
                success = true
            }
            navigate(AuthenticationDirections.Inventory, finishActivity = true)
        } else {
            uiState.value!!.build {
                isLoading = false
                errorMessage = result.message
            }
        }
    }
}