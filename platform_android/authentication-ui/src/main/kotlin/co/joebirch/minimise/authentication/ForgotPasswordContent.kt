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
import androidx.ui.input.TextFieldValue
import androidx.ui.layout.*
import androidx.ui.layout.ColumnScope.weight
import androidx.ui.livedata.observeAsState
import androidx.ui.material.*
import androidx.ui.material.ripple.RippleIndication
import androidx.ui.res.stringResource
import androidx.ui.text.TextStyle
import androidx.ui.text.font.FontFamily
import androidx.ui.text.font.FontWeight
import androidx.ui.text.style.TextAlign
import androidx.ui.unit.dp
import androidx.ui.unit.sp
import co.joebirch.minimise.authentication.forgot_password.ForgotPasswordState
import co.joebirch.minimise.authentication.ui.R
import co.joebirch.minimise.common_ui.MinimiseTheme
import co.joebirch.minimise.common_ui.setContentWithLifecycle

fun ViewGroup.composeForgotPasswordContent(
    lifecycleOwner: LifecycleOwner,
    uiState: LiveData<ForgotPasswordState>,
    authenticateClicked: () -> Unit,
    emailChanged: (String) -> Unit
): Any = setContentWithLifecycle(lifecycleOwner) {
    ComposeForgotPasswordContent(
        uiState,
        authenticateClicked,
        emailChanged
    )
}

@Composable
private fun ComposeForgotPasswordContent(
    uiState: LiveData<ForgotPasswordState>,
    authenticateClicked: () -> Unit,
    emailChanged: (String) -> Unit
) {
    val viewState = uiState.observeAsState()
    FormContent(
        viewState.value!!.isLoading,
        viewState.value!!.emailAddress,
        viewState.value!!.errorMessage,
        authenticateClicked,
        emailChanged
    )
}

@Composable
private fun FormContent(
    isLoading: Boolean,
    email: String,
    errorMessage: String?,
    authenticateClicked: () -> Unit,
    emailChanged: (String) -> Unit
) {
    val emailState = state { TextFieldValue(email) }
    MinimiseTheme {
        Stack(modifier = Modifier.fillMaxSize()) {
            Box(backgroundColor = Color.White, modifier = Modifier.fillMaxSize())
            if (isLoading) {
                CircularProgressIndicator(modifier = Modifier.gravity(Alignment.Center))
            } else {
                ScrollableColumn(
                    modifier = Modifier.fillMaxSize().padding(16.dp)
                ) {
                    Spacer(modifier = Modifier.preferredHeight(20.dp))
                    Text(
                        modifier = Modifier.preferredWidth(240.dp)
                            .gravity(Alignment.CenterHorizontally),
                        text = "Reset password",
                        textAlign = TextAlign.Center,
                        fontFamily = FontFamily.Monospace,
                        style = TextStyle(fontSize = 24.sp, fontWeight = FontWeight.Bold)
                    )
                    Spacer(modifier = Modifier.preferredHeight(36.dp))
                    Column(modifier = Modifier.padding(8.dp)) {
                        FilledTextField(
                            value = emailState.value,
                            onValueChange = {
                                emailChanged(emailState.value.text)
                            },
                            label = {
                                Text(text = "Email Address", fontSize = 12.sp)
                            },
                            keyboardType = KeyboardType.Email,
                            imeAction = ImeAction.Next,
                            modifier = Modifier.padding(16.dp).fillMaxWidth()
                        )
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
                                text = stringResource(R.string.sign_in),
                                modifier = Modifier.preferredSizeIn(minWidth = 220.dp)
                            )
                        }
                    }
                    Spacer(Modifier.preferredHeight(16.dp))
                }
                val showingDialog = state { errorMessage }
                if (showingDialog.value != null) {
                    AlertDialog(onCloseRequest = {
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
            }
        }
    }
}