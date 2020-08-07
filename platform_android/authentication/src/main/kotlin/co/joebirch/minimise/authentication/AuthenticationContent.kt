package co.joebirch.minimise.authentication

import android.view.ViewGroup
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.RowScope.gravity
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Recomposer
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.state
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.setContent
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.LiveData
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

                        TextField(
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

                        TextField(
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