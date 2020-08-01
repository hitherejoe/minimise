package co.joebirch.minimise.authentication

import android.view.ViewGroup
import androidx.compose.Composable
import androidx.compose.Recomposer
import androidx.compose.getValue
import androidx.compose.state
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.ui.core.Alignment
import androidx.ui.core.Modifier
import androidx.ui.core.setContent
import androidx.ui.core.testTag
import androidx.ui.foundation.*
import androidx.ui.graphics.Color
import androidx.ui.input.ImeAction
import androidx.ui.input.KeyboardType
import androidx.ui.input.TextFieldValue
import androidx.ui.input.PasswordVisualTransformation
import androidx.ui.layout.*
import androidx.ui.layout.ColumnScope.weight
import androidx.ui.layout.RowScope.gravity
import androidx.ui.livedata.observeAsState
import androidx.ui.material.*
import androidx.ui.res.stringResource
import androidx.ui.text.TextStyle
import androidx.ui.text.font.FontFamily
import androidx.ui.text.font.FontWeight
import androidx.ui.text.style.TextAlign
import androidx.ui.unit.dp
import androidx.ui.unit.sp
import co.joebirch.minimise.common_ui.MinimiseTheme

fun ViewGroup.composeAuthenticationContentTwo(
    uiState: LiveData<AuthenticationState>,
    authenticationModeToggled: () -> Unit,
    authenticateClicked: () -> Unit,
    forgotPasswordClicked: () -> Unit,
    emailChanged: (String) -> Unit,
    passwordChanged: (String) -> Unit
): Any =
    setContent(Recomposer.current()) {
        ComposeAuthenticationContent(
            uiState, authenticationModeToggled, authenticateClicked,
            forgotPasswordClicked, emailChanged, passwordChanged
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
    val viewState by uiState.observeAsState()
    AuthenticationContent(
        viewState!!,
        authenticationModeToggled,
        authenticateClicked,
        forgotPasswordClicked,
        emailChanged,
        passwordChanged
    )
}

@Composable
internal fun AuthenticationContent(
    viewState: AuthenticationState,
    authenticationModeToggled: (() -> Unit)? = null,
    authenticateClicked: (() -> Unit)? = null,
    forgotPasswordClicked: (() -> Unit)? = null,
    emailChanged: ((String) -> Unit)? = null,
    passwordChanged: ((String) -> Unit)? = null
) {
    val emailState = state { TextFieldValue(viewState.emailAddress) }
    val passwordState = state { TextFieldValue(viewState.password) }

    MinimiseTheme {
        Box(backgroundColor = Color.White, modifier = Modifier.fillMaxSize())
        if (viewState.isLoading) {
            CircularProgressIndicator(
                modifier =
                Modifier.gravity(Alignment.CenterVertically)
                    .testTag("Progress")
            )
        } else {
            Column(
                modifier = Modifier.fillMaxSize()
            ) {
                Spacer(modifier = Modifier.preferredHeight(20.dp))
                Text(
                    modifier = Modifier.preferredWidth(240.dp)
                        .gravity(Alignment.CenterHorizontally),
                    text = if (viewState.authenticationMode == AuthenticateMode.SignUp) {
                        "Sign up for a Minimise account"
                    } else "Sign in to your Minimise account",
                    textAlign = TextAlign.Center,
                    fontFamily = FontFamily.Monospace,
                    style = TextStyle(fontSize = 24.sp, fontWeight = FontWeight.Bold)
                )
                Spacer(modifier = Modifier.preferredHeight(36.dp))
                Column(modifier = Modifier.padding(8.dp)) {
                    FilledTextField(
                        value = emailState.value,
                        onValueChange = {
                            emailState.value = it
                            emailChanged?.invoke(it.text)
                        },
                        label = {
                            Text(text = "Email Address", fontSize = 12.sp)
                        },
                        keyboardType = KeyboardType.Email,
                        imeAction = ImeAction.Next,
                        modifier = Modifier.padding(16.dp).fillMaxWidth()
                    )
                    FilledTextField(
                        value = passwordState.value,
                        onValueChange = {
                            passwordState.value = it
                            passwordChanged?.invoke(it.text)
                        },
                        label = {
                            Text(text = "Password", fontSize = 12.sp)
                        },
                        visualTransformation = PasswordVisualTransformation(),
                        keyboardType = KeyboardType.Password,
                        imeAction = ImeAction.Done,
                        modifier = Modifier.padding(16.dp).fillMaxWidth()
                    )
                    if (viewState.authenticationMode == AuthenticateMode.SignIn) {
                        TextButton(onClick = {
                            forgotPasswordClicked?.invoke()
                        }) {
                            ProvideTextStyle(value = TextStyle(textAlign = TextAlign.Center)) {
                                Text(
                                    text = stringResource(R.string.forgotten_your_password),
                                    modifier = Modifier
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
                Spacer(Modifier.weight(1f, fill = true))
                Button(onClick = {
                    authenticateClicked?.invoke()
                }, modifier = Modifier.gravity(Alignment.CenterHorizontally)) {
                    ProvideTextStyle(value = TextStyle(textAlign = TextAlign.Center)) {
                        Text(
                            text = if (viewState.authenticationMode == AuthenticateMode.SignIn) {
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
                    authenticationModeToggled?.invoke()
                }, modifier = Modifier.gravity(Alignment.CenterHorizontally)) {
                    ProvideTextStyle(value = TextStyle(textAlign = TextAlign.Center)) {
                        Text(
                            text = if (viewState.authenticationMode == AuthenticateMode.SignIn) {
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
            val showingDialog = state { viewState.errorMessage }
            if (showingDialog.value != null) {
                AlertDialog(onCloseRequest = {
                    showingDialog.value = null
                }, title = {
                    Text(text = "Whoops!")
                }, text = {
                    Text(text = viewState.errorMessage ?: "")
                }, buttons = {
                    Stack(modifier = Modifier.fillMaxWidth()) {
                        Text(
                            text = "OK", style = currentTextStyle().plus(
                                TextStyle(fontWeight = FontWeight.SemiBold)
                            ),
                            modifier = Modifier.padding(16.dp).gravity(Alignment.CenterEnd)
                                .clickable(onClick = {
                                    showingDialog.value = null
                                })
                        )
                    }
                })
            }
        }
    }
}