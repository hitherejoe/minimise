package co.joebirch.minimise.authentication.reset_password

import android.view.ViewGroup
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.ColumnScope.weight
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.TextField
import androidx.compose.material.ripple.RippleIndication
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Recomposer
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.state
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.setContent
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.LiveData
import co.joebirch.minimise.authentication.R
import co.joebirch.minimise.authentication.forgot_password.ForgotPasswordState
import co.joebirch.minimise.common_ui.MinimiseTheme

fun ViewGroup.composeForgotPasswordContent(
    uiState: LiveData<ForgotPasswordState>,
    authenticateClicked: () -> Unit,
    emailChanged: (String) -> Unit
): Any = setContent(Recomposer.current()) {
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
                        TextField(
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
            }
        }
    }
}