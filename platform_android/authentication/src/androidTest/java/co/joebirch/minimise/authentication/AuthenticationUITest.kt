package co.joebirch.minimise.authentication

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.test.platform.app.InstrumentationRegistry
import org.junit.Rule
import org.junit.Test

class AuthenticationUITest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private fun launchContent(viewState: AuthenticationState) {
        composeTestRule.setContent {
            AuthenticationUI(viewState) {

            }
        }
    }

    @Test
    fun signUpTitleDisplayedByDefault() {
        launchContent(AuthenticationState())

        InstrumentationRegistry.getInstrumentation().targetContext
            .getString(R.string.title_sign_up)
            .also {
                composeTestRule.onNode(matcher = hasText(it))
                    .assertIsDisplayed()
            }
    }

    @Test
    fun signInTitleIsNotDisplayedByDefault() {
        launchContent(AuthenticationState())

        InstrumentationRegistry.getInstrumentation().targetContext
            .getString(R.string.title_sign_in)
            .also {
                composeTestRule.onNode(matcher = hasText(it))
                    .assertDoesNotExist()
            }
    }

    @Test
    fun signInTitleDisplayedForSignInState() {
        launchContent(AuthenticationState(authenticationMode = AuthenticateMode.SignIn))

        InstrumentationRegistry.getInstrumentation().targetContext
            .getString(R.string.title_sign_in)
            .also {
                composeTestRule.onNode(matcher = hasText(it))
                    .assertIsDisplayed()
            }
    }

    @Test
    fun signUpTitleDisplayedForSignUpState() {
        launchContent(AuthenticationState(authenticationMode = AuthenticateMode.SignUp))

        InstrumentationRegistry.getInstrumentation().targetContext
            .getString(R.string.title_sign_up)
            .also {
                composeTestRule.onNode(matcher = hasText(it))
                    .assertIsDisplayed()
            }
    }

    @Test
    fun forgottenPasswordDisplayedForSignInState() {
        launchContent(AuthenticationState(authenticationMode = AuthenticateMode.SignIn))

        InstrumentationRegistry.getInstrumentation().targetContext
            .getString(R.string.forgotten_your_password)
            .also {
                composeTestRule.onNode(matcher = hasText(it))
                    .assertIsDisplayed()
            }
    }

    @Test
    fun forgottenPasswordNotDisplayedForSignUpState() {
        launchContent(AuthenticationState(authenticationMode = AuthenticateMode.SignUp))

        InstrumentationRegistry.getInstrumentation().targetContext
            .getString(R.string.forgotten_your_password)
            .also {
                composeTestRule.onNode(matcher = hasText(it))
                    .assertDoesNotExist()
            }
    }

    @Test
    fun signUpButtonDisplayed() {
        launchContent(AuthenticationState(authenticationMode = AuthenticateMode.SignUp))
        composeTestRule.onNode(matcher = hasTestTag(TAG_AUTHENTICATE))
            .assertTextEquals(
                InstrumentationRegistry.getInstrumentation().targetContext
                    .getString(R.string.sign_up)
            )
    }

    @Test
    fun signInButtonDisplayed() {
        launchContent(AuthenticationState(authenticationMode = AuthenticateMode.SignIn))
        composeTestRule.onNode(matcher = hasTestTag(TAG_AUTHENTICATE))
            .assertTextEquals(
                InstrumentationRegistry.getInstrumentation().targetContext
                    .getString(R.string.sign_in)
            )
    }

    @Test
    fun authenticateButtonDisabledByDefault() {
        launchContent(AuthenticationState(authenticationMode = AuthenticateMode.SignIn))
        composeTestRule.onNode(matcher = hasTestTag(TAG_AUTHENTICATE))
            .assertIsNotEnabled()
    }

    @Test
    fun authenticateButtonEnabledWithText() {
        launchContent(
            AuthenticationState(
                isAuthenticationContentValid = true
            )
        )
        composeTestRule.onNode(matcher = hasTestTag(TAG_AUTHENTICATE))
            .assertIsEnabled()
    }

    @Test
    fun errorMessageDisplayedWhenThereIsAnError() {
        val message = "There was an error"
        launchContent(AuthenticationState(errorMessage = message))
        composeTestRule.onNode(matcher = hasText(message))
            .assertIsDisplayed()
    }
}