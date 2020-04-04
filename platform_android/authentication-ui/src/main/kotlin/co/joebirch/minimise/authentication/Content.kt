package co.joebirch.minimise.authentication

import android.view.ViewGroup
import androidx.compose.Composable
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.ui.core.Modifier
import androidx.ui.foundation.Text
import androidx.ui.foundation.TextField
import androidx.ui.input.ImeAction
import androidx.ui.input.KeyboardType
import androidx.ui.input.PasswordVisualTransformation
import androidx.ui.layout.*
import androidx.ui.material.Button
import androidx.ui.material.CircularProgressIndicator
import androidx.ui.material.TextButton
import androidx.ui.res.stringResource
import androidx.ui.unit.dp
import co.joebirch.minimise.authentication.ui.R
import co.joebirch.minimise.common_ui.observe
import co.joebirch.minimise.common_ui.setContentWithLifecycle

fun ViewGroup.composeAuthenticationContent(
    lifecycleOwner: LifecycleOwner,
    state: LiveData<AuthenticationState>,
    authenticationModeToggled: () -> Unit,
    authenticateClicked: () -> Unit,
    forgotPasswordClicked: () -> Unit,
    emailChanged: (String) -> Unit,
    passwordChanged: (String) -> Unit
): Any = setContentWithLifecycle(lifecycleOwner) {
    ComposeAuthenticationContent(
        state,
        authenticationModeToggled,
        authenticateClicked,
        forgotPasswordClicked,
        emailChanged,
        passwordChanged
    )
}

@Composable
private fun ComposeAuthenticationContent(
    state: LiveData<AuthenticationState>,
    authenticationModeToggled: () -> Unit,
    authenticateClicked: () -> Unit,
    forgotPasswordClicked: () -> Unit,
    emailChanged: (String) -> Unit,
    passwordChanged: (String) -> Unit
) {
    val viewState = observe(state)
    if (viewState != null) {
        FormContent(
            state.value!!.emailAddress,
            state.value!!.password,
            state.value!!.authenticationMode,
            authenticationModeToggled,
            authenticateClicked,
            forgotPasswordClicked,
            emailChanged,
            passwordChanged
        )
    }
}

@Composable
private fun progress() {
    CircularProgressIndicator()
}

@Composable
private fun FormContent(
    emailAddress: String,
    password: String,
    authenticationMode: AuthenticateMode,
    authenticationModeToggled: () -> Unit,
    authenticateClicked: () -> Unit,
    forgotPasswordClicked: () -> Unit,
    emailChanged: (String) -> Unit,
    passwordChanged: (String) -> Unit
) {
    Column(
    ) {
        Text(
            text = stringResource(id = R.string.title_minimise),
            modifier = LayoutGravity.Center
        )
        Spacer(Modifier.preferredSize(24.dp))
        Column(modifier = Modifier.padding(16.dp)) {
            TextField(
                value = emailAddress,
                onValueChange = { emailChanged(it) },
                keyboardType = KeyboardType.Email,
                imeAction = ImeAction.Next,
                modifier = LayoutGravity.Center
            )
            Spacer(LayoutHeight(12.dp))
            TextField(
                value = password,
                onValueChange = { passwordChanged(it) },
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Done,
                modifier = LayoutGravity.Center,
                visualTransformation = PasswordVisualTransformation()
            )
            Spacer(LayoutHeight(12.dp))
            if (authenticationMode == AuthenticateMode.SignIn) {
                TextButton(onClick = {
                    forgotPasswordClicked()
                }, modifier = LayoutGravity.Center.plus(LayoutWidth.Fill)) {
                    Text(
                        text = stringResource(R.string.forgotten_your_password),
                        modifier = LayoutGravity.Center.plus(
                            LayoutPadding(16.dp)
                        )
                    )
                }
            }
        }

        Column {
            Button(onClick = {
                authenticateClicked()
            }, modifier = LayoutGravity.Center.plus(LayoutWidth.Fill)) {
                Text(
                    text = if (authenticationMode == AuthenticateMode.SignIn) {
                        stringResource(R.string.sign_in)
                    } else {
                        stringResource(R.string.sign_up)
                    }
                )
            }
            Spacer(LayoutHeight(12.dp))
            Button(onClick = {
                authenticationModeToggled()
            }, modifier = LayoutGravity.Center.plus(LayoutWidth.Fill)) {
                Text(
                    text = if (authenticationMode == AuthenticateMode.SignIn) {
                        stringResource(R.string.no_account)
                    } else {
                        stringResource(R.string.existing_account)
                    }
                )
            }
        }
    }
}