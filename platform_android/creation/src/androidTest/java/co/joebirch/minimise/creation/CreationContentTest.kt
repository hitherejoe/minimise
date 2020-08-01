package co.joebirch.minimise.creation

import androidx.test.filters.MediumTest
import androidx.ui.layout.ExperimentalLayout
import androidx.ui.material.MaterialTheme
import androidx.ui.material.Surface
import androidx.ui.test.*
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