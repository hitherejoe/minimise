package co.joebirch.minimise.navigation

import androidx.navigation.NavController
import androidx.test.ext.junit.runners.AndroidJUnit4

import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify

@RunWith(AndroidJUnit4::class)
class OnboardingNavigationTest {

    private val mockNavController = mock(NavController::class.java)

    @Test
    fun testOnboardingNavigatesToAuthentication() {
        navigateToAuthentication(mockNavController).also {
            verify(mockNavController)
                .navigate(R.id.action_onboardingFragment_to_authenticationFragment)
        }
    }
}