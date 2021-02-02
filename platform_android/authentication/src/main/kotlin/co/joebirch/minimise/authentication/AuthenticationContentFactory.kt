package co.joebirch.minimise.authentication

import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import co.joebirch.minimise.common_ui.MinimiseTheme
import javax.inject.Inject

class AuthenticationContentFactory @Inject constructor() :
    Composablefactory<AuthenticationViewModel>() {

    @Composable
    override fun Content(
        viewModel: AuthenticationViewModel
    ) {
        val state by viewModel.uiState.observeAsState()
        MinimiseTheme {
            AuthenticationUI(
                viewState = state ?: AuthenticationState(),
                events = viewModel::handleAuthenticationEvent
            )
        }
    }
}