package co.joebirch.minimise.authentication

import android.view.ViewGroup
import androidx.compose.Composable
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
import androidx.ui.text.TextFieldValue
import androidx.ui.text.TextStyle
import androidx.ui.text.style.TextAlign
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
    Column(modifier = Modifier.wrapContentHeight(align = Alignment.Center)) {
        Surface(
            modifier = Modifier.padding(16.dp),
            color = Color.White,
            shape = RoundedCornerShape(CornerSize(4.dp))
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    "Email Address", modifier = Modifier.padding(
                        start = 16.dp,
                        top = 16.dp
                    )
                )
                Surface(color = Color.LightGray, modifier = Modifier.padding(16.dp)) {
                    TextField(
                        value = TextFieldValue(text = emailAddress),
                        onValueChange = { emailChanged(it.text) },
                        keyboardType = KeyboardType.Email,
                        imeAction = ImeAction.Next,
                        modifier = Modifier.gravity(ColumnAlign.Center).padding(16.dp)
                    )
                }
                Text("Password", modifier = Modifier.padding(start = 16.dp, top = 8.dp))
                Surface(color = Color.LightGray, modifier = Modifier.padding(16.dp)) {
                    TextField(
                        value = TextFieldValue(text = password),
                        onValueChange = { passwordChanged(it.text) },
                        keyboardType = KeyboardType.Password,
                        imeAction = ImeAction.Done,
                        visualTransformation = PasswordVisualTransformation(),
                        modifier = Modifier.gravity(ColumnAlign.Center).padding(16.dp)
                    )
                }
                if (authenticationMode == AuthenticateMode.SignIn) {
                    TextButton(onClick = {
                        forgotPasswordClicked()
                    }, modifier = Modifier.gravity(ColumnAlign.Center)) {
                        ProvideTextStyle(value = TextStyle(textAlign = TextAlign.Center)) {
                            Text(
                                text = stringResource(R.string.forgotten_your_password),
                                modifier = Modifier.gravity(ColumnAlign.Center)
                                    .padding(top = 8.dp, bottom = 8.dp, start = 12.dp, end = 12.dp)
                            )
                        }
                    }
                }
            }
        }
        Spacer(Modifier.preferredHeight(32.dp))
        Column(modifier = Modifier.gravity(ColumnAlign.Center)) {
            Button(onClick = {
                authenticateClicked()
            }, modifier = Modifier.gravity(ColumnAlign.Center)) {
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
            }, modifier = Modifier.gravity(ColumnAlign.Center)) {
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