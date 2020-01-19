package co.joebirch.minimise.authentication

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.joebirch.minimise.shared_authentication.interactor.Authenticate
import co.joebirch.minimise.shared_authentication.model.AuthenticationModel
import co.joebirch.minimise.shared_authentication.presentation.AuthenticateView
import co.joebirch.minimise.shared_authentication.presentation.AuthenticationState
import kotlinx.coroutines.launch

class AuthenticationViewModel constructor(
    private val authenticate: Authenticate
) : ViewModel(), AuthenticateView {

    private val uiState = MutableLiveData<AuthenticationState>()
    fun observeAuthenticationState(): LiveData<AuthenticationState> = uiState

    override fun signUp(
        emailAddress: String,
        password: String
    ) {
        uiState.postValue(AuthenticationState.Loading)
        authenticate.run(Authenticate.Params.forSignUp(emailAddress, password)) { result ->
            handleResult(result)
        }
    }

    override fun signIn(
        emailAddress: String,
        password: String
    ) {
        uiState.postValue(AuthenticationState.Loading)
        viewModelScope.launch {
            authenticate.run(Authenticate.Params.forSignIn(emailAddress, password)) { result ->
                handleResult(result)
            }
        }
    }

    private fun handleResult(result: AuthenticationModel) {
        if (result.success) {
            uiState.postValue(AuthenticationState.Success)
        } else {
            uiState.postValue(AuthenticationState.Failure(result.message))
        }
    }
}