package co.joebirch.minimise.authentication

import android.view.ViewGroup
import androidx.compose.Composable
import androidx.compose.State
import androidx.compose.state
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.ui.core.Alignment
import androidx.ui.core.Modifier
import androidx.ui.foundation.*
import androidx.ui.foundation.shape.corner.CornerSize
import androidx.ui.foundation.shape.corner.RoundedCornerShape
import androidx.ui.graphics.Color
import androidx.ui.input.ImeAction
import androidx.ui.input.KeyboardType
import androidx.ui.input.PasswordVisualTransformation
import androidx.ui.layout.*
import androidx.ui.material.Button
import androidx.ui.material.CircularProgressIndicator
import androidx.ui.material.Surface
import androidx.ui.material.TextButton
import androidx.ui.res.stringResource
import androidx.ui.text.TextStyle
import androidx.ui.text.style.TextAlign
import androidx.ui.unit.dp
import co.joebirch.minimise.authentication.ui.R
import co.joebirch.minimise.common_ui.observe
import co.joebirch.minimise.common_ui.setContentWithLifecycle

fun ViewGroup.composeAuthenticationContent(
    lifecycleOwner: LifecycleOwner,
    uiState: LiveData<AuthenticationState>,
    authenticationModeToggled: () -> Unit,
    authenticateClicked: () -> Unit,
    forgotPasswordClicked: () -> Unit,
    emailChanged: (String) -> Unit,
    passwordChanged: (String) -> Unit
): Any = setContentWithLifecycle(lifecycleOwner) {
    ComposeAuthenticationContent(
        uiState,
        authenticationModeToggled,
        authenticateClicked,
        forgotPasswordClicked,
        emailChanged,
        passwordChanged
    )
}

@Composable
private fun ComposeAuthenticationContent(
    uiState: LiveData<AuthenticationState>,
    authenticationModeToggled: () -> Unit,
    authenticateClicked: () -> Unit,
    forgotPasswordClicked: () -> Unit,
    emailChanged: (String) -> Unit,
    passwordChanged: (String) -> Unit
) {
    val viewState = observe(uiState)
    if (viewState != null) {
        FormContent(
            uiState.value!!.emailAddress,
            uiState.value!!.password,
            uiState.value!!.authenticationMode,
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
    email: String,
    password: String,
    authenticationMode: AuthenticateMode,
    authenticationModeToggled: () -> Unit,
    authenticateClicked: () -> Unit,
    forgotPasswordClicked: () -> Unit,
    emailChanged: (String) -> Unit,
    passwordChanged: (String) -> Unit
) {
    val emailState = state { TextFieldValue(email) }
    val passwordState = state { TextFieldValue(password) }

    Column(
        modifier = Modifier.wrapContentHeight(align = Alignment.CenterVertically)
            .fillMaxWidth()
    ) {
        Surface(
            modifier = Modifier.padding(16.dp),
            color = Color.White,
            shape = RoundedCornerShape(CornerSize(4.dp))
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    "Email Address", modifier = Modifier.padding(
                        start = 16.dp,
                        top = 16.dp,
                        bottom = 8.dp
                    )
                )
                Surface(
                    color = Color.LightGray,
                    modifier = Modifier.fillMaxWidth().preferredHeight(50.dp)
                ) {
                    TextField(
                        value = emailState.value,
                        onValueChange = {
                            emailState.value = it
                            emailChanged(it.text)
                        },
                        keyboardType = KeyboardType.Email,
                        imeAction = ImeAction.Next,
                        modifier = Modifier.padding(16.dp).fillMaxWidth()
                    )
                }
                Text(
                    "Password",
                    modifier = Modifier.padding(
                        start = 16.dp,
                        top = 16.dp,
                        bottom = 8.dp
                    ).fillMaxWidth()
                )
                Surface(
                    color = Color.LightGray,
                    modifier = Modifier.fillMaxWidth().preferredHeight(50.dp)
                ) {
                    TextField(
                        value = passwordState.value,
                        onValueChange = {
                            passwordState.value = it
                            passwordChanged(it.text)
                        },
                        keyboardType = KeyboardType.Password,
                        imeAction = ImeAction.Done,
                        visualTransformation = PasswordVisualTransformation(),
                        modifier = Modifier.padding(16.dp)
                    )
                }
                if (authenticationMode == AuthenticateMode.SignIn) {
                    TextButton(onClick = {
                        forgotPasswordClicked()
                    }, modifier = Modifier.gravity(ColumnAlign.Center)) {
                        ProvideTextStyle(value = TextStyle(textAlign = TextAlign.Center)) {
                            Text(
                                text = stringResource(R.string.forgotten_your_password),
                                modifier = Modifier.gravity(Alignment.CenterHorizontally)
                                    .padding(top = 8.dp, bottom = 8.dp, start = 12.dp, end = 12.dp)
                            )
                        }
                    }
                }
            }
        }
        Spacer(Modifier.preferredHeight(32.dp))
        Column(
            modifier = Modifier.gravity(Alignment.CenterHorizontally)
                .wrapContentHeight(align = Alignment.CenterVertically)
        ) {
            Button(onClick = {
                authenticateClicked()
            }, modifier = Modifier.gravity(Alignment.CenterHorizontally)) {
                ProvideTextStyle(value = TextStyle(textAlign = TextAlign.Center)) {
                    Text(
                        text = if (authenticationMode == AuthenticateMode.SignIn) {
                            stringResource(R.string.sign_in)
                        } else {
                            stringResource(R.string.sign_up)
                        },
                        modifier = Modifier.preferredSizeIn(minWidth = 220.dp)
                    )
                }
            }
            Spacer(Modifier.preferredHeight(16.dp))
            Button(onClick = {
                authenticationModeToggled()
            }, modifier = Modifier.gravity(Alignment.CenterHorizontally)) {
                ProvideTextStyle(value = TextStyle(textAlign = TextAlign.Center)) {
                    Text(
                        text = if (authenticationMode == AuthenticateMode.SignIn) {
                            stringResource(R.string.no_account)
                        } else {
                            stringResource(R.string.existing_account)
                        },
                        modifier = Modifier.preferredSizeIn(minWidth = 220.dp)
                    )
                }
            }
        }
    }
}