package co.joebirch.minimise.authentication

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import co.joebirch.minimise.android.core.di.BaseViewModel
import co.joebirch.minimise.android.core.di.Preferences
import co.joebirch.minimise.android.core.di.default
import co.joebirch.minimise.authentication.interactor.Authenticate
import co.joebirch.minimise.authentication.model.AuthenticationModel
import co.joebirch.minimise.navigation.AuthenticationDirections
import kotlinx.coroutines.launch
import javax.inject.Inject

class AuthenticationViewModel @Inject constructor(
    private val authenticate: Authenticate,
    private val sharedPrefs: Preferences
) : BaseViewModel(), AuthenticateView {

    private var uiState =
        MutableLiveData<AuthenticationState>().default(
            AuthenticationState.Idle()
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
            signUp()
        } else {
            signIn()
        }
    }

    override fun signUp() {
        uiState.postValue(
            uiState.value!!.build {
                state = AuthenticationState.Loading()
            }
        )
        viewModelScope.launch {
            authenticate.run(
                Authenticate.Params.forSignUp(
                    "FIREBASE_API_KEY",
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
                state = AuthenticationState.Loading()
            }
        )
        viewModelScope.launch {
            authenticate.run(
                Authenticate.Params.forSignIn(
                    "FIREBASE_API_KEY",
                    uiState.value!!.emailAddress, uiState.value!!.password
                )
            ) { result ->
                handleResult(result)
            }
        }
    }

    private fun handleResult(result: AuthenticationModel) {
        if (result.token != null) {
            sharedPrefs.accessToken = result.token
            uiState.postValue(
                uiState.value!!.build {
                    state = AuthenticationState.Success
                }
            )
            navigate(AuthenticationDirections.Dashboard, finishActivity = true)
        } else {
            uiState.postValue(
                uiState.value!!.build {
                    state = AuthenticationState.Error(message = result.message)
                }
            )
        }
    }
}