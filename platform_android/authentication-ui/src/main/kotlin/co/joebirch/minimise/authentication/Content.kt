package co.joebirch.minimise.authentication

import android.view.ViewGroup
import androidx.compose.Composable
import androidx.compose.state
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.ui.core.Alignment
import androidx.ui.core.Modifier
import androidx.ui.foundation.*
import androidx.ui.graphics.Color
import androidx.ui.input.ImeAction
import androidx.ui.input.KeyboardType
import androidx.ui.input.PasswordVisualTransformation
import androidx.ui.layout.*
import androidx.ui.layout.ColumnScope.weight
import androidx.ui.livedata.observeAsState
import androidx.ui.material.*
import androidx.ui.res.stringResource
import androidx.ui.text.TextStyle
import androidx.ui.text.font.FontWeight
import androidx.ui.text.style.TextAlign
import androidx.ui.unit.dp
import androidx.ui.unit.sp
import co.joebirch.minimise.authentication.ui.R
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
    val viewState = uiState.observeAsState()
    FormContent(
        viewState.value!!.emailAddress,
        viewState.value!!.password,
        viewState.value!!.authenticationMode,
        authenticationModeToggled,
        authenticateClicked,
        forgotPasswordClicked,
        emailChanged,
        passwordChanged
    )
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

    Stack(modifier = Modifier.fillMaxSize()) {
        Box(backgroundColor = Color.White, modifier = Modifier.fillMaxSize())
        VerticalScroller(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Spacer(modifier = Modifier.preferredHeight(20.dp))
                Text(
                    modifier = Modifier.preferredWidth(240.dp)
                        .gravity(Alignment.CenterHorizontally),
                    text = if (authenticationMode == AuthenticateMode.SignUp) {
                        "Sign up for a Minimise account"
                    } else "Sign in to your Minimise account",
                    textAlign = TextAlign.Center,
                    style = TextStyle(fontSize = 24.sp, fontWeight = FontWeight.Bold)
                )
                Spacer(modifier = Modifier.preferredHeight(36.dp))
                Column(modifier = Modifier.padding(8.dp)) {

                    FilledTextField(
                        value = emailState.value,
                        onValueChange = {
                            emailState.value = it
                            emailChanged(it.text)
                        },
                        label = {
                            Text(text = "Email Address")
                        },
                        keyboardType = KeyboardType.Email,
                        imeAction = ImeAction.Next,
                        modifier = Modifier.padding(16.dp).fillMaxWidth()
                    )
                    FilledTextField(
                        value = passwordState.value,
                        onValueChange = {
                            passwordState.value = it
                            passwordChanged(it.text)
                        },
                        label = {
                            Text(text = "Password")
                        },
                        visualTransformation = PasswordVisualTransformation(),
                        keyboardType = KeyboardType.Password,
                        imeAction = ImeAction.Done,
                        modifier = Modifier.padding(16.dp).fillMaxWidth()
                    )
                    if (authenticationMode == AuthenticateMode.SignIn) {
                        TextButton(onClick = {
                            forgotPasswordClicked()
                        }, modifier = Modifier.gravity(ColumnAlign.Center)) {
                            ProvideTextStyle(value = TextStyle(textAlign = TextAlign.Center)) {
                                Text(
                                    text = stringResource(R.string.forgotten_your_password),
                                    modifier = Modifier.gravity(Alignment.CenterHorizontally)
                                        .padding(
                                            top = 8.dp,
                                            bottom = 8.dp,
                                            start = 12.dp,
                                            end = 12.dp
                                        )
                                )
                            }
                        }
                    }
                }
            }
            Column(
                modifier = Modifier.fillMaxSize().weight(1f, fill = true),
                horizontalGravity = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Bottom
            ) {
                Button(onClick = {
                    authenticateClicked()
                }) {
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
                TextButton(onClick = {
                    authenticationModeToggled()
                }) {
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
                Spacer(Modifier.preferredHeight(26.dp))
            }
        }
    }
}