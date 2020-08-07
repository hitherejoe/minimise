package co.joebirch.minimise.authentication

import android.view.ViewGroup
import androidx.compose.Composable
import androidx.compose.Recomposer
import androidx.compose.getValue
import androidx.compose.state
import androidx.lifecycle.LiveData
import androidx.ui.core.*
import androidx.ui.foundation.*
import androidx.ui.foundation.shape.corner.RoundedCornerShape
import androidx.ui.graphics.Color
import androidx.ui.input.ImeAction
import androidx.ui.input.KeyboardType
import androidx.ui.input.PasswordVisualTransformation
import androidx.ui.layout.*
import androidx.ui.layout.RowScope.gravity
import androidx.ui.livedata.observeAsState
import androidx.ui.material.*
import androidx.ui.res.stringResource
import androidx.ui.text.TextStyle
import androidx.ui.text.font.FontWeight
import androidx.ui.text.style.TextAlign
import androidx.ui.unit.dp
import androidx.ui.unit.sp
import co.joebirch.minimise.common_ui.MinimiseTheme

fun ViewGroup.composeAuthenticationContent(
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
    viewState?.let {
        AuthenticationContent(
            it,
            authenticationModeToggled,
            authenticateClicked,
            forgotPasswordClicked,
            emailChanged,
            passwordChanged
        )
    }
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

    MinimiseTheme {
        Box(backgroundColor = MaterialTheme.colors.primary, modifier = Modifier.fillMaxSize())
        if (viewState.isLoading) {
            CircularProgressIndicator(
                modifier =
                Modifier.gravity(Alignment.CenterVertically)
                    .testTag("Progress"),
                color = MaterialTheme.colors.secondary
            )
        } else {
            Column(
                modifier = Modifier.fillMaxSize()
            ) {
                Spacer(modifier = Modifier.height(36.dp))
                Text(
                    modifier = Modifier.preferredWidth(240.dp)
                        .gravity(Alignment.CenterHorizontally),
                    text = if (viewState.authenticationMode == AuthenticateMode.SignUp) {
                        stringResource(id = R.string.title_sign_up)
                    } else stringResource(id = R.string.title_sign_in),
                    textAlign = TextAlign.Center,
                    color = Color.White,
                    style = TextStyle(fontSize = 24.sp, fontWeight = FontWeight.Bold)
                )
                Spacer(modifier = Modifier.height(48.dp))
                Box(
                    shape = RoundedCornerShape(10.dp),
                    backgroundColor = MaterialTheme.colors.secondary,
                    modifier = Modifier.fillMaxWidth().padding(
                        start = 32.dp,
                        end = 32.dp
                    )
                ) {
                    Column {

                        FilledTextField(
                            value = viewState.emailAddress,
                            onValueChange = {
                                emailChanged?.invoke(it)
                            },
                            label = {
                                Text(
                                    text = stringResource(id = R.string.hint_email_address),
                                    fontSize = 12.sp
                                )
                            },
                            inactiveColor = MaterialTheme.colors.onSecondary,
                            keyboardType = KeyboardType.Email,
                            imeAction = ImeAction.Next,
                            modifier = Modifier.padding(
                                start = 24.dp,
                                end = 24.dp,
                                top = 12.dp
                            ).fillMaxWidth(),
                            activeColor = Color.White,
                            shape = RoundedCornerShape(10.dp),
                            backgroundColor = MaterialTheme.colors.secondary,
                        )
                        Spacer(modifier = Modifier.height(36.dp))

                        FilledTextField(
                            value = viewState.password,
                            onValueChange = {
                                passwordChanged?.invoke(it)
                            },
                            label = {
                                Text(
                                    text = stringResource(id = R.string.hint_password),
                                    fontSize = 12.sp
                                )
                            },
                            visualTransformation = PasswordVisualTransformation(),
                            keyboardType = KeyboardType.Password,
                            inactiveColor = MaterialTheme.colors.onSecondary,
                            imeAction = ImeAction.Done,
                            modifier = Modifier.padding(
                                start = 24.dp,
                                end = 24.dp,
                                bottom = 36.dp,
                            ).fillMaxWidth(),
                            activeColor = Color.White,
                            shape = RoundedCornerShape(10.dp),
                            backgroundColor = MaterialTheme.colors.secondary,
                        )
                    }
                }
                if (viewState.authenticationMode == AuthenticateMode.SignIn) {
                    TextButton(
                        onClick = {
                            forgotPasswordClicked?.invoke()
                        }, modifier = Modifier.gravity(Alignment.CenterHorizontally)
                            .padding(16.dp)
                    ) {
                        ProvideTextStyle(
                            value = TextStyle(
                                textAlign = TextAlign.Center,
                                color = Color.White
                            )
                        ) {
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

                Spacer(Modifier.weight(1f, fill = true))
                Button(
                    onClick = {
                        authenticateClicked?.invoke()
                    }, modifier = Modifier.gravity(Alignment.CenterHorizontally),
                    backgroundColor = MaterialTheme.colors.secondary,
                    shape = RoundedCornerShape(10.dp),
                    contentColor = Color.White
                ) {
                    ProvideTextStyle(
                        value = TextStyle(
                            textAlign = TextAlign.Center,
                            fontWeight = FontWeight.Bold
                        )
                    ) {
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
                            modifier = Modifier.preferredSizeIn(minWidth = 220.dp),
                            color = Color.White
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
                    Text(text = stringResource(id = R.string.error_title))
                }, text = {
                    Text(text = viewState.errorMessage ?: "")
                }, buttons = {
                    Stack(modifier = Modifier.fillMaxWidth()) {
                        Text(
                            text = stringResource(id = R.string.error_action),
                            style = currentTextStyle().plus(
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