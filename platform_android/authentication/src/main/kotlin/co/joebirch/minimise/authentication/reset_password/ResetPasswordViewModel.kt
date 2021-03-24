package co.joebirch.minimise.authentication.reset_password

import androidx.lifecycle.*
import co.joebirch.minimise.android.core.di.BaseViewModel
import co.joebirch.minimise.android.core.di.default
import co.joebirch.minimise.authentication.interactor.ResetPassword
import co.joebirch.minimise.authentication.ResetPasswordView
import co.joebirch.minimise.authentication.forgot_password.ForgotPasswordState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ResetPasswordViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val resetPassword: ResetPassword
) : BaseViewModel(), ResetPasswordView {

    private val uiState =
        MutableLiveData<ForgotPasswordState>().default(
            ForgotPasswordState())
    fun observeAuthenticationState(): LiveData<ForgotPasswordState> = uiState

    override fun resetPassword(emailAddress: String) {
      //  uiState.postValue(AuthenticationState.Loading)
        viewModelScope.launch {
            resetPassword.run(
                ResetPassword.Params.forResetPassword(
                "AIzaSyBFLpvP6vOjrl_5s_R45E6s33FOFg6y5wQ", emailAddress)) { result ->
                if (result.success) {
              //      uiState.postValue(AuthenticationState.Success)
                } else {
                //    uiState.postValue(AuthenticationState.Failure(result.message))
                }
            }
        }
    }
}