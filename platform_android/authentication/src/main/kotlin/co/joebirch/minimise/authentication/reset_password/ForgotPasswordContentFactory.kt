package co.joebirch.minimise.authentication.reset_password

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import co.joebirch.minimise.common_ui.Composablefactory
import co.joebirch.minimise.authentication.R
import co.joebirch.minimise.authentication.forgot_password.ForgotPasswordState
import co.joebirch.minimise.common_ui.MinimiseTheme
import co.joebirch.minimise.common_ui.FilledButton
import co.joebirch.minimise.common_ui.onSecondaryInputField
import co.joebirch.minimise.common_ui.RoundedBackgroundBox
import javax.inject.Inject

class ForgotPasswordContentFactory @Inject constructor(
): Composablefactory<ResetPasswordViewModel>() {

    @Composable
    fun AuthenticationContent(
        state: ForgotPasswordState,
        authenticateClicked: () -> Unit,
        emailChanged: (String) -> Unit
    ) {
        MinimiseTheme {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = MaterialTheme.colors.primary)
            ) {
                if (state.isLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.Center),
                        color = MaterialTheme.colors.secondary
                    )
                } else {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp)
                    ) {
                        Spacer(modifier = Modifier.height(20.dp))
                        Text(
                            modifier = Modifier.align(Alignment.CenterHorizontally),
                            text = stringResource(id = R.string.title_forgot_password),
                            color = MaterialTheme.colors.onPrimary,
                            style = TextStyle(fontSize = 24.sp, fontWeight = FontWeight.Bold)
                        )
                        Spacer(modifier = Modifier.height(36.dp))

                        RoundedBackgroundBox(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(
                                    start = 32.dp,
                                    end = 32.dp,
                                    bottom = 32.dp
                                )
                        ) {
                            onSecondaryInputField(
                                modifier = Modifier.padding(16.dp),
                                inputValue = state.emailAddress,
                                inputChanged = { value ->
                                    emailChanged(value)
                                },
                                label = stringResource(id = R.string.hint_forgot_password),
                                keyboardType = KeyboardOptions(
                                    keyboardType = KeyboardType.Email,
                                    imeAction = ImeAction.Done
                                ),
                                keyboardActions = KeyboardActions(
                                    onDone = {
                                        authenticateClicked()
                                    }
                                )
                            )
                        }
                        Spacer(Modifier.weight(1f, fill = true))
                        FilledButton(
                            modifier = Modifier.align(Alignment.CenterHorizontally),
                            text = stringResource(R.string.action_forgot_password),
                            onClick = {
                                authenticateClicked()
                            },
                            enabled = true
                        )
                        Box(Modifier.preferredHeight(16.dp))
                    }

                    /*
                    val showingDialog = state { errorMessage }
                    if (showingDialog.value != null) {
                        AlertDialog(onDismissRequest = {
                            showingDialog.value = null
                        }, title = {
                            Text(text = "Whoops!")
                        }, text = {
                            Text(text = errorMessage ?: "")
                        }, buttons = {
                            Stack(modifier = Modifier.fillMaxWidth()) {
                                Text(
                                    text = "OK", style = currentTextStyle().plus(
                                        TextStyle(fontWeight = FontWeight.SemiBold)
                                    ),
                                    modifier = Modifier.clickable(onClick = {
                                        showingDialog.value = null
                                    }, indication = RippleIndication()).padding(16.dp)
                                        .gravity(Alignment.CenterEnd)
                                )
                            }
                        })
                    }
                    */
                }
            }
        }
    }

    @Composable
    override fun Content(viewModel: ResetPasswordViewModel) {
        val state by viewModel.observeAuthenticationState().observeAsState()
        AuthenticationContent(
            state = state ?: ForgotPasswordState(),
            {  },
            {  }
        )
    }

}