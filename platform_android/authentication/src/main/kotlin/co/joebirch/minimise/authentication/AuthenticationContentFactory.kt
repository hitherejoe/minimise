package co.joebirch.minimise.authentication

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.*
import androidx.compose.ui.graphics.Color
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
import co.joebirch.minimise.common_ui.*

@Composable
fun Authentication(
    viewModel: AuthenticationViewModel
) {
    val state by viewModel.uiState.observeAsState()
    Authentication(
        viewState = state ?: AuthenticationState(),
        events = viewModel::handleAuthenticationEvent
    )
}

@Composable
private fun Authentication(
    viewState: AuthenticationState,
    events: (event: AuthenticationEvent) -> Unit
) {
    if (viewState.isLoading) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(color = MaterialTheme.colors.primary),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator(
                modifier = Modifier.testTag(TAG_PROGRESS),
                color = MaterialTheme.colors.secondary
            )
        }
    } else {
        val titleFocusRequest = remember { FocusRequester() }
        val passwordFocusRequest = remember { FocusRequester() }
        val authFocusRequest = remember { FocusRequester() }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = MaterialTheme.colors.primary)
        ) {
            Spacer(modifier = Modifier.height(36.dp))
            Text(
                modifier = Modifier
                    .width(240.dp)
                    .align(Alignment.CenterHorizontally)
                    .focusModifier()
                    .focusable(true)
                    .focusRequester(titleFocusRequest)
                    .onFocusChanged {
                        val s = ""
                    }
                    .onFocusEvent {
                        val e = ""
                    },
                text = if (viewState.authenticationMode == AuthenticateMode.SignUp) {
                    stringResource(id = R.string.title_sign_up)
                } else stringResource(id = R.string.title_sign_in),
                textAlign = TextAlign.Center,
                color = Color.White,
                style = TextStyle(fontSize = 24.sp, fontWeight = FontWeight.Bold)
            )
            Spacer(modifier = Modifier.height(48.dp))
            RoundedBackgroundBox(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        start = 32.dp,
                        end = 32.dp
                    )
            ) {
                Column {
                    onSecondaryInputField(
                        inputValue = viewState.emailAddress,
                        inputChanged = {
                            events(AuthenticationEvent.EmailChanged(it))
                        },
                        label = stringResource(id = R.string.hint_email_address),
                        keyboardType = KeyboardOptions(
                            imeAction = ImeAction.Next,
                            keyboardType = KeyboardType.Email,

                            ),
                        keyboardActions = KeyboardActions(
                            onNext = {
                                passwordFocusRequest.requestFocus()
                            }
                        ),
                        modifier = Modifier.padding(
                            start = 24.dp,
                            end = 24.dp,
                            top = 12.dp
                        )
                    )
                    Spacer(modifier = Modifier.height(20.dp))

                    onSecondaryInputField(
                        inputValue = viewState.password,
                        inputChanged = {
                            events(AuthenticationEvent.PasswordChanged(it))
                        },
                        label = stringResource(id = R.string.hint_password),
                        visualTransformation = PasswordVisualTransformation(),
                        keyboardType = KeyboardOptions(
                            imeAction = ImeAction.Done,
                            keyboardType = KeyboardType.Password
                        ),
                        keyboardActions = KeyboardActions(
                            onDone = {
                                events(AuthenticationEvent.AuthenticateClicked)
                            }
                        ),
                        modifier = Modifier
                            .padding(
                                start = 24.dp,
                                end = 24.dp,
                                bottom = 36.dp,
                            )
                            .focusRequester(passwordFocusRequest)
                    )
                }
            }
            if (viewState.authenticationMode == AuthenticateMode.SignIn) {
                TextButton(
                    onClick = {
                        events(AuthenticationEvent.ForgotPasswordClicked)
                    }, modifier = Modifier
                        .align(Alignment.CenterHorizontally)
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

            Spacer(
                Modifier.weight(1f, fill = true)
            )
            FilledButton(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .testTag(TAG_AUTHENTICATE),
                text = if (viewState.authenticationMode == AuthenticateMode.SignIn) {
                    stringResource(R.string.sign_in)
                } else {
                    stringResource(R.string.sign_up)
                },
                onClick = {
                    events(AuthenticationEvent.AuthenticateClicked)
                },
                enabled = viewState.isAuthenticationContentValid,
            )
            Spacer(Modifier.height(16.dp))
            TextButton(
                onClick = {
                    events(AuthenticationEvent.AuthenticationModeToggled)
                }, modifier = Modifier.align(Alignment.CenterHorizontally)
            ) {
                Text(
                    text = if (viewState.authenticationMode == AuthenticateMode.SignIn) {
                        stringResource(R.string.no_account)
                    } else {
                        stringResource(R.string.existing_account)
                    },
                    modifier = Modifier.widthIn(min = 220.dp),
                    color = Color.White,
                    textAlign = TextAlign.Center
                )
            }
            Spacer(Modifier.height(26.dp))
        }
        if (viewState.errorMessage != null) {
            AlertDialog(onDismissRequest = {
                events(AuthenticationEvent.DismissErrorDialog)
            }, title = {
                Text(text = stringResource(id = R.string.error_generic_title))
            }, text = {
                Text(
                    text = viewState.errorMessage
                        ?: stringResource(id = R.string.error_generic_message)
                )
            }, buttons = {
                Box(modifier = Modifier.fillMaxWidth()) {
                    Text(
                        text = stringResource(id = R.string.error_generic_action),
                        style = TextStyle(fontWeight = FontWeight.SemiBold),
                        modifier = Modifier
                            .padding(16.dp)
                            .align(Alignment.CenterEnd)
                            .clickable(onClick = {
                                events(AuthenticationEvent.DismissErrorDialog)
                            })
                    )
                }
            })
        }
    }
}