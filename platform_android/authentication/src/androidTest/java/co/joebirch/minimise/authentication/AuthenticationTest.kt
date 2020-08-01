package co.joebirch.minimise.authentication

import androidx.test.filters.MediumTest
import androidx.ui.layout.ExperimentalLayout
import androidx.ui.test.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@ExperimentalLayout
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

        onNodeWithText("Sign up for a Minimise account").assertIsDisplayed()
    }
}