package co.joebirch.minimise.authentication

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import co.joebirch.minimise.android.core.di.BaseViewModel
import co.joebirch.minimise.android.core.di.Preferences
import co.joebirch.minimise.android.core.di.default
import co.joebirch.minimise.authentication.interactor.Authenticate
import co.joebirch.minimise.authentication.model.AuthenticationModel
import co.joebirch.minimise.navigation.AuthenticationDirections
import kotlinx.coroutines.launch

class AuthenticationViewModel @ViewModelInject constructor(
    private val authenticate: Authenticate,
    private val sharedPrefs: Preferences,
    @Assisted private val savedStateHandle: SavedStateHandle
) : BaseViewModel(), AuthenticateView {

    private var uiState =
        MutableLiveData<AuthenticationState>().default(
            AuthenticationState()
        )

    fun observeAuthenticationState(): LiveData<AuthenticationState> = uiState

    override fun toggleAuthenticationMode() {
        if (uiState.value!!.authenticationMode == AuthenticateMode.SignIn) {
            uiState.postValue(
                uiState.value!!.build {
                    mode = AuthenticateMode.SignUp
                }
            )
        } else {
            uiState.postValue(
                uiState.value!!.build {
                    mode = AuthenticateMode.SignIn
                }
            )
        }
    }

    override fun setEmailAddress(emailAddress: String) {
        uiState.postValue(
            uiState.value!!.build {
                this.userEmail = emailAddress
            }
        )
    }

    override fun setPassword(password: String) {
        uiState.postValue(
            uiState.value!!.build {
                this.userPassword = password
            }
        )
    }

    override fun authenticate() {
        if (uiState.value?.authenticationMode == AuthenticateMode.SignIn) {
            signIn()
        } else {
            signUp()
        }
    }

    override fun signUp() {
        uiState.postValue(
            uiState.value!!.build {
                loading = true
                error = null
            }
        )
        viewModelScope.launch {
            authenticate?.run(
                Authenticate.Params.forSignUp(
                    BuildConfig.FIREBASE_API_KEY,
                    uiState.value!!.emailAddress, uiState.value!!.password
                )
            ) { result ->
                handleResult(result)
            }
        }
    }

    override fun signIn() {
        uiState.postValue(
            uiState.value!!.build {
                loading = true
                error = null
            }
        )
        viewModelScope.launch {
            authenticate?.run(
                Authenticate.Params.forSignIn(
                    BuildConfig.FIREBASE_API_KEY,
                    uiState.value!!.emailAddress, uiState.value!!.password
                )
            ) { result ->
                handleResult(result)
            }
        }
    }

    private fun handleResult(result: AuthenticationModel) {
        if (result.token != null) {
            sharedPrefs?.accessToken = result.token
            navigate(AuthenticationDirections.Dashboard)
        } else {
            uiState.postValue(
                uiState.value!!.build {
                    loading = false
                    error = result.message
                }
            )
        }
    }
}