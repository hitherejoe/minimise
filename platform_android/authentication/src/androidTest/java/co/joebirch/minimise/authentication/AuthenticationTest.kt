package co.joebirch.minimise.authentication

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.test.platform.app.InstrumentationRegistry
import org.junit.Rule
import org.junit.Test

class AuthenticationTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private fun launchContent(viewState: AuthenticationState) {
        composeTestRule.setContent {
            AuthenticationContent(viewState) {

            }
        }
    }

    @Test
    fun signUpTitleDisplayedByDefault() {
        launchContent(AuthenticationState())

        InstrumentationRegistry.getInstrumentation().context.getString(R.string.title_sign_up)
            .also {
                composeTestRule.onNode(matcher = hasText(it))
                    .assertIsDisplayed()
            }
    }

    @Test
    fun signInTitleIsNotDisplayedByDefault() {
        launchContent(AuthenticationState())

        InstrumentationRegistry.getInstrumentation().context.getString(R.string.title_sign_in)
            .also {
                composeTestRule.onNode(matcher = hasText(it))
                    .assertDoesNotExist()
            }
    }

    @Test
    fun signInTitleDisplayedForSignInState() {
        launchContent(AuthenticationState(authenticationMode = AuthenticateMode.SignIn))

        InstrumentationRegistry.getInstrumentation().context.getString(R.string.title_sign_in)
            .also {
                composeTestRule.onNode(matcher = hasText(it))
                    .assertIsDisplayed()
            }
    }

    @Test
    fun signUpTitleDisplayedForSignUpState() {
        launchContent(AuthenticationState(authenticationMode = AuthenticateMode.SignUp))

        InstrumentationRegistry.getInstrumentation().context.getString(R.string.title_sign_up)
            .also {
                composeTestRule.onNode(matcher = hasText(it))
                    .assertIsDisplayed()
            }
    }

    @Test
    fun forgottenPasswordDisplayedForSignInState() {
        launchContent(AuthenticationState(authenticationMode = AuthenticateMode.SignIn))

        InstrumentationRegistry.getInstrumentation().context.getString(
            R.string.forgotten_your_password)
            .also {
                composeTestRule.onNode(matcher = hasText(it))
                    .assertIsDisplayed()
            }
    }

    @Test
    fun forgottenPasswordNotDisplayedForSignUpState() {
        launchContent(AuthenticationState(authenticationMode = AuthenticateMode.SignUp))

        InstrumentationRegistry.getInstrumentation().context.getString(
            R.string.forgotten_your_password)
            .also {
                composeTestRule.onNode(matcher = hasText(it))
                    .assertDoesNotExist()
            }
    }

    @Test
    fun errorMessageDisplayedWhenThereIsAnError() {
        val message = "There was an error"
        launchContent(AuthenticationState(errorMessage = message))
        composeTestRule.onNode(matcher = hasText(message))
            .assertIsDisplayed()
    }
}