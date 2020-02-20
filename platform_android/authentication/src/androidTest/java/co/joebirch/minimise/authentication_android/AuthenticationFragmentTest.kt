package co.joebirch.minimise.authentication_android

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import co.joebirch.minimise.authentication.AuthenticationFragment
import co.joebirch.minimise.authentication.R
import org.hamcrest.CoreMatchers.not
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class AuthenticationFragmentTest {

    @Test
    fun progressHiddenWhenIdle() {
        val scenario = launchFragmentInContainer<AuthenticationFragment>()
        onView(withId(R.id.progress)).check(matches(not(isDisplayed())))
    }
}