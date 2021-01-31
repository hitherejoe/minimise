package co.joebirch.minimise.authentication.reset_password

import android.view.ViewGroup
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Recomposer
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.platform.setContent
import androidx.lifecycle.LiveData
import co.joebirch.minimise.authentication.forgot_password.ForgotPasswordState

fun ViewGroup.composeForgotPasswordContent(
    uiState: LiveData<ForgotPasswordState>,
    authenticateClicked: () -> Unit,
    emailChanged: (String) -> Unit
): Any = setContent(Recomposer.current()) {
    val viewState by uiState.observeAsState()
    viewState?.let { state ->
        FormContent(
            state,
            authenticateClicked,
            emailChanged
        )
    }
}

@Composable
private fun FormContent(
    state: ForgotPasswordState,
    authenticateClicked: () -> Unit,
    emailChanged: (String) -> Unit
) {

}