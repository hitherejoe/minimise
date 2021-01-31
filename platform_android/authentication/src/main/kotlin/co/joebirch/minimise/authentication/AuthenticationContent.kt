package co.joebirch.minimise.authentication

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
import co.joebirch.minimise.common_ui.FilledButton
import co.joebirch.minimise.common_ui.onSecondaryInputField
import co.joebirch.minimise.common_ui.roundedBackgroundBox

@Composable
fun Content(
    viewState: AuthenticationState,
    events: (event: AuthenticationEvent) -> Unit
) {
    if (viewState.isLoading) {
        CircularProgressIndicator(
            modifier = Modifier.testTag("Progress"),
            color = MaterialTheme.colors.secondary
        )
    } else {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = MaterialTheme.colors.primary)
        ) {
            Spacer(modifier = Modifier.height(36.dp))
            Text(
                modifier = Modifier
                    .width(240.dp)
                    .align(Alignment.CenterHorizontally),
                text = if (viewState.authenticationMode == AuthenticateMode.SignUp) {
                    stringResource(id = R.string.title_sign_up)
                } else stringResource(id = R.string.title_sign_in),
                textAlign = TextAlign.Center,
                color = Color.White,
                style = TextStyle(fontSize = 24.sp, fontWeight = FontWeight.Bold)
            )
            Spacer(modifier = Modifier.height(48.dp))
            roundedBackgroundBox(
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
                            keyboardType = KeyboardType.Email
                        ),
                        modifier = Modifier.padding(
                            start = 24.dp,
                            end = 24.dp,
                            top = 12.dp
                        ),
                        imeActionPerformed = { _, _ ->

                        }
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
                        imeActionPerformed = { _, _ ->
                            val s = ""
                        },
                        modifier = Modifier.padding(
                            start = 24.dp,
                            end = 24.dp,
                            bottom = 36.dp,
                        )
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
                modifier = Modifier.align(Alignment.CenterHorizontally),
                text = if (viewState.authenticationMode == AuthenticateMode.SignIn) {
                    stringResource(R.string.sign_in)
                } else {
                    stringResource(R.string.sign_up)
                },
                onClick = {
                    events(AuthenticationEvent.AuthenticateClicked)
                }
            )
            Spacer(Modifier.preferredHeight(16.dp))
            TextButton(
                onClick = {
                    events(AuthenticationEvent.AuthenticationModeToggled)
                }, modifier = Modifier.align(Alignment.CenterHorizontally)
            ) {
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
            Spacer(Modifier.height(26.dp))
        }
        val showingDialog = remember { mutableStateOf(viewState.errorMessage) }
        if (showingDialog.value != null) {
            AlertDialog(onDismissRequest = {
                showingDialog.value = null
            }, title = {
                Text(text = stringResource(id = R.string.error_title))
            }, text = {
                Text(text = viewState.errorMessage ?: "")
            }, buttons = {
                Box(modifier = Modifier.fillMaxWidth()) {
                    Text(
                        text = stringResource(id = R.string.error_action),
                        style = TextStyle(fontWeight = FontWeight.SemiBold),
                        modifier = Modifier
                            .padding(16.dp)
                            .align(Alignment.CenterEnd)
                            .clickable(onClick = {
                                showingDialog.value = null
                            })
                    )
                }
            })
        }
    }
}