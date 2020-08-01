package co.joebirch.creation_ui

import androidx.test.filters.MediumTest
import androidx.test.platform.app.InstrumentationRegistry
import androidx.ui.foundation.Text
import androidx.ui.foundation.TextFieldValue
import androidx.ui.layout.ExperimentalLayout
import androidx.ui.material.MaterialTheme
import androidx.ui.material.Surface
import androidx.ui.res.stringResource
import androidx.ui.test.*
import co.joebirch.minimise.dashboard.CreationState
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@ExperimentalLayout
@MediumTest
@RunWith(JUnit4::class)
class CreationContentTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private fun launchContent() {
        composeTestRule.setContent {
            MaterialTheme {
                Surface {

                }
            }
        }
    }

    @Test
    fun productNameHintDisplayedByDefault() {
        launchContent()
    }
}