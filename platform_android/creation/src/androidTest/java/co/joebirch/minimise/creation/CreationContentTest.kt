package co.joebirch.minimise.creation

import androidx.test.filters.MediumTest
import androidx.test.platform.app.InstrumentationRegistry
import androidx.ui.layout.ExperimentalLayout
import androidx.ui.test.*
import co.joebirch.minimise.dashboard.CreationState
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

    private fun launchContent(viewState: CreationState = CreationState()) {
        composeTestRule.setContent {
            CreationContent(creationState = viewState)
        }
    }

    @Test
    fun productNameTitleDisplayedByDefault() {
        launchContent()

        InstrumentationRegistry.getInstrumentation().context.getString(R.string.hint_product_name)
            .also {
                onNodeWithText(it)
                    .assertIsDisplayed()
            }
    }
}