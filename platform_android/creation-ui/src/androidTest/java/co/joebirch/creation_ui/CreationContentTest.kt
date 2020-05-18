package co.joebirch.creation_ui

import androidx.test.filters.MediumTest
import androidx.test.platform.app.InstrumentationRegistry
import androidx.ui.foundation.Text
import androidx.ui.foundation.TextFieldValue
import androidx.ui.material.MaterialTheme
import androidx.ui.material.Surface
import androidx.ui.res.stringResource
import androidx.ui.test.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@MediumTest
@RunWith(JUnit4::class)
class CreationContentTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private fun launchContent(creationData: CreationData = CreationData()) {
        composeTestRule.setContent {
            MaterialTheme {
                Surface {
                    CreationContent(CreationState(creationData))
                }
            }
        }
    }

    @Test
    fun productNameHintDisplayedByDefault() {
        launchContent()

        InstrumentationRegistry.getInstrumentation().context.getString(R.string.hint_product_name)
            .also { hint ->
                findByText(hint).assertIsDisplayed()
            }
    }

    @Test
    fun nextStepButtonDisabledWithoutProductNameEntered() {
        launchContent()
        findByTag("NextButton").doClick()

        InstrumentationRegistry.getInstrumentation().context.getString(R.string.hint_product_name)
            .also { hint ->
                findByText(hint).assertIsDisplayed()
            }
    }

    @Test
    fun firstStepDoesNotShowBackButton() {
        launchContent()
        findByTag("PreviousStep").assertDoesNotExist()
    }

    @Test
    fun backButtonDisplayedAfterFirstStep() {
        launchContent(CreationData(selectedStep = CreationStep.STORE))
        findByTag("PreviousStep").assertExists()
    }

    @Test
    fun canProgressToSecondStepAfterProductNameEntered() {
        launchContent(CreationData(name = TextFieldValue("Pixelbook")))
        findByTag("NextButton").doClick()

        InstrumentationRegistry.getInstrumentation().context.getString(R.string.hint_store_name)
            .also { hint ->
                findByText(hint).assertIsDisplayed()
            }
    }

    @Test
    fun nextStepButtonDisabledWithoutStoreNameEntered() {
        launchContent(CreationData(selectedStep = CreationStep.STORE))
        findByTag("NextButton").doClick()
        findByText("What store is this item from?").assertIsDisplayed()
    }

    @Test
    fun thirdStepDisplayedAfterStoreNameEntered() {
        launchContent(
            CreationData(
                store = TextFieldValue("Best Buy"),
                selectedStep = CreationStep.STORE
            )
        )
        findByTag("NextButton").doClick()
        InstrumentationRegistry.getInstrumentation().context.getString(R.string.hint_frequency)
            .also { hint ->
                findAllBySubstring(hint).first().assertIsDisplayed()
            }
    }

    @Test
    fun frequencyDisplaysOftenByDefault() {
        launchContent(
            CreationData(
                selectedStep = CreationStep.FREQUENCY
            )
        )

        InstrumentationRegistry.getInstrumentation().context.getString(R.string.frequency_often)
            .also { label ->
                findAllBySubstring(label).first().assertIsDisplayed()
            }
    }

    @Test
    fun usageFrequencyUpdatesLabel() {
        launchContent(
            CreationData(
                selectedStep = CreationStep.FREQUENCY
            )
        )

        findByTag("FrequencySlider").doGesture {
            sendSwipeLeft()
        }
        InstrumentationRegistry.getInstrumentation().context.getString(R.string.frequency_hardly)
            .also { label ->
                findAllBySubstring(label).first().assertIsDisplayed()
            }
    }

    private fun findAllBySubstring(
        text: String,
        ignoreCase: Boolean = false
    ): List<SemanticsNodeInteraction> {
        return findAll(
            hasSubstring(text, ignoreCase)
        )
    }
}