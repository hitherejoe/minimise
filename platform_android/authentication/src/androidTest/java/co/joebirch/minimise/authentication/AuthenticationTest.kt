package co.joebirch.minimise.authentication

import androidx.test.filters.MediumTest
import androidx.test.platform.app.InstrumentationRegistry
import androidx.ui.test.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@MediumTest
@RunWith(JUnit4::class)
class AuthenticationTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private fun launchContent(viewState: AuthenticationState) {
        composeTestRule.setContent {
            AuthenticationContent(viewState)
        }
    }

    @Test
    fun signUpTitleDisplayedByDefault() {
        launchContent(AuthenticationState())

        InstrumentationRegistry.getInstrumentation().context.getString(R.string.title_sign_up)
            .also {
                onNodeWithText(it)
                    .assertIsDisplayed()
            }
    }

    @Test
    fun signInTitleIsNotDisplayedByDefault() {
        launchContent(AuthenticationState())

        InstrumentationRegistry.getInstrumentation().context.getString(R.string.title_sign_in)
            .also {
                onNodeWithText(it)
                    .assertDoesNotExist()
            }
    }

    @Test
    fun signInTitleDisplayedForSignInState() {
        launchContent(AuthenticationState(authenticationMode = AuthenticateMode.SignIn))

        InstrumentationRegistry.getInstrumentation().context.getString(R.string.title_sign_in)
            .also {
                onNodeWithText(it)
                    .assertIsDisplayed()
            }
    }

    @Test
    fun signUpTitleDisplayedForSignUpState() {
        launchContent(AuthenticationState(authenticationMode = AuthenticateMode.SignUp))

        InstrumentationRegistry.getInstrumentation().context.getString(R.string.title_sign_up)
            .also {
                onNodeWithText(it)
                    .assertIsDisplayed()
            }
    }

    @Test
    fun forgottenPasswordDisplayedForSignInState() {
        launchContent(AuthenticationState(authenticationMode = AuthenticateMode.SignIn))

        InstrumentationRegistry.getInstrumentation().context.getString(
            R.string.forgotten_your_password)
            .also {
                onNodeWithText(it)
                    .assertIsDisplayed()
            }
    }

    @Test
    fun forgottenPasswordNotDisplayedForSignUpState() {
        launchContent(AuthenticationState(authenticationMode = AuthenticateMode.SignUp))

        InstrumentationRegistry.getInstrumentation().context.getString(
            R.string.forgotten_your_password)
            .also {
                onNodeWithText(it)
                    .assertDoesNotExist()
            }
    }

    @Test
    fun errorMessageDisplayedWhenThereIsAnError() {
        val message = "There was an error"
        launchContent(AuthenticationState(errorMessage = message))

        onNodeWithText(message).assertIsDisplayed()
    }
}