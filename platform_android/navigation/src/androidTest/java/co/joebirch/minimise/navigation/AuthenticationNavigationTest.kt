package co.joebirch.minimise.navigation

import androidx.navigation.NavController
import androidx.test.ext.junit.runners.AndroidJUnit4

import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify

@RunWith(AndroidJUnit4::class)
class AuthenticationNavigationTest {

    private val mockNavController = mock(NavController::class.java)

    @Test
    fun testOnboardingNavigatesToAuthentication() {
        navigateToOnboarding(mockNavController).also {
            verify(mockNavController)
                .navigate(R.id.action_authenticationFragment_to_onboardingFragment)
        }
    }
}