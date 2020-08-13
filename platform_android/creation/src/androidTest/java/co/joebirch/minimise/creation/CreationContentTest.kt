package co.joebirch.minimise.creation

import androidx.compose.foundation.layout.ExperimentalLayout
import androidx.test.filters.MediumTest
import androidx.test.platform.app.InstrumentationRegistry
import androidx.ui.test.*
import co.joebirch.minimise.dashboard.CreationState
import co.joebirch.minimise.dashboard.CreationStep
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
    
    @Test
    fun titleComposableDisplaysText() {
        val title = "This is a title"
        composeTestRule.setContent {
            titleComposable(title = title)
        }

        onNodeWithText(title)
            .assertIsDisplayed()
    }

    @Test
    fun stepCounterDisplaysViewedSteps() {
        composeTestRule.setContent {
            stepCounter(currentStep = CreationStep.FREQUENCY)
        }

        repeat(CreationStep.FREQUENCY.ordinal + 1) {
            onNodeWithTag(CreationStep.values()[it].name)
                .assertIsDisplayed()
        }
    }

    @Test
    fun stepCounterDoesNotDisplayNotViewedSteps() {
        composeTestRule.setContent {
            stepCounter(currentStep = CreationStep.FREQUENCY)
        }

        onNodeWithTag(CreationStep.POSITIVE.name)
            .assertDoesNotExist()
        onNodeWithTag(CreationStep.NEGATIVE.name)
            .assertDoesNotExist()
        onNodeWithTag(CreationStep.REMIND.name)
            .assertDoesNotExist()
        onNodeWithTag(CreationStep.FINISHED.name)
            .assertDoesNotExist()
    }
}