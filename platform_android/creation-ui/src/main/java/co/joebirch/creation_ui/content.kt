package co.joebirch.creation_ui

import android.view.ViewGroup
import androidx.compose.*
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.ui.core.*
import androidx.ui.core.Alignment.Companion.BottomStart
import androidx.ui.core.Alignment.Companion.Center
import androidx.ui.core.Alignment.Companion.CenterHorizontally
import androidx.ui.foundation.*
import androidx.ui.graphics.Color
import androidx.ui.layout.*
import androidx.ui.livedata.observeAsState
import androidx.ui.material.*
import androidx.ui.material.icons.Icons
import androidx.ui.material.icons.filled.ArrowForward
import androidx.ui.material.icons.filled.Done
import androidx.ui.material.ripple.ripple
import androidx.ui.res.stringArrayResource
import androidx.ui.res.stringResource
import androidx.ui.res.vectorResource
import androidx.ui.text.TextStyle
import androidx.ui.text.style.TextAlign
import androidx.ui.text.style.TextDecoration
import androidx.ui.unit.TextUnit
import androidx.ui.unit.dp
import androidx.ui.unit.ipx
import co.joebirch.minimise.common_ui.setContentWithLifecycle
import co.joebirch.minimise.dashboard.CreationState
import co.joebirch.minimise.dashboard.CreationStep

fun ViewGroup.composeDashboardContent(
    lifecycleOwner: LifecycleOwner,
    uiState: LiveData<CreationState>,
    onNameChanged: (name: String) -> Unit,
    onStoreChanged: (store: String) -> Unit,
    onFrequencyChanged: (frequency: Float) -> Unit,
    onNextStep: () -> Unit,
    onPreviousStep: () -> Unit,
    onFormCompleted: () -> Unit
): Any = setContentWithLifecycle(lifecycleOwner) {
    ComposeInventoryContent(uiState, onNameChanged, onStoreChanged, onFrequencyChanged,
        onNextStep, onPreviousStep, onFormCompleted)
}

@Composable
private fun ComposeInventoryContent(
    uiState: LiveData<CreationState>,
    onNameChanged: (name: String) -> Unit,
    onStoreChanged: (store: String) -> Unit,
    onFrequencyChanged: (frequency: Float) -> Unit,
    onNextStep: () -> Unit,
    onPreviousStep: () -> Unit,
    onFormCompleted: () -> Unit
) {
    val viewState by uiState.observeAsState()
    if (viewState != null) {
        CreationContent(
            viewState!!.isLoading, selectedStep = viewState!!.currentStep,
            onNameChanged = onNameChanged, onStoreChanged = onStoreChanged,
            onFrequencyChanged = onFrequencyChanged,
            onNextStep = onNextStep, onPreviousStep = onPreviousStep,
            onFormCompleted = onFormCompleted, creationState = viewState!!
        )
    }
}

private fun isValid(
    creationState: CreationState,
    selectedStep: CreationStep
): Boolean {
    return when (selectedStep) {
        CreationStep.NAME -> creationState.name.isNotEmpty()
        CreationStep.STORE -> creationState.store.isNotEmpty()
        else -> true
    }
}

@Composable
internal fun CreationContent(
    isLoading: Boolean,
    creationState: CreationState,
    selectedStep: CreationStep,
    onNameChanged: (name: String) -> Unit,
    onStoreChanged: (store: String) -> Unit,
    onFrequencyChanged: (frequency: Float) -> Unit,
    onNextStep: () -> Unit,
    onPreviousStep: () -> Unit,
    onFormCompleted: () -> Unit
) {
    Scaffold(
        floatingActionButton = {
            if (!isLoading) {
                TestTag(tag = "NextButton") {
                    FloatingActionButton(
                        modifier = Modifier.ripple(enabled = false, radius = 0.dp),
                        onClick = {
                            if (selectedStep == CreationStep.FREQUENCY) {
                                onFormCompleted()
                            } else if (isValid(creationState, selectedStep)) {
                                onNextStep()
                            }
                        }, backgroundColor = if (isValid(creationState, selectedStep)) {
                            MaterialTheme.colors.secondary
                        } else Color.LightGray
                    ) {
                        if (selectedStep == CreationStep.FREQUENCY) {
                            Icon(asset = Icons.Filled.Done)
                        } else Icon(asset = Icons.Filled.ArrowForward)
                    }
                }
            }
        },
        floatingActionButtonPosition = Scaffold.FabPosition.End,
        bodyContent = {
            Stack(modifier = Modifier.fillMaxSize()) {
                Box(
                    backgroundColor = MaterialTheme.colors.primary,
                    modifier = Modifier.fillMaxSize()
                )
                if (isLoading) {
                    CircularProgressIndicator(
                        color = MaterialTheme.colors.secondary,
                        modifier = Modifier.gravity(Center)
                    )
                } else {
                    Column(modifier = Modifier.gravity(Center).padding(16.dp)) {
                        when (selectedStep) {
                            CreationStep.NAME -> {
                                nameStepComposable(creationState, onNameChanged = onNameChanged)
                            }
                            CreationStep.STORE -> {
                                storeStepComposable(creationState, onStoreChanged = onStoreChanged)
                            }
                            CreationStep.FREQUENCY -> {
                                frequencyStepComposable(creationState,
                                onFrequencyChanged = onFrequencyChanged)
                            }
                        }
                    }
                    if (selectedStep != CreationStep.NAME) {
                        TestTag(tag = "PreviousStep") {
                            Clickable(
                                onClick = {
                                    onPreviousStep()
                                }, modifier = Modifier.gravity(BottomStart).padding(
                                    start = 24.dp, bottom = 28.dp
                                ).ripple(bounded = false)
                            ) {
                                Icon(
                                    asset = vectorResource(id = R.drawable.ic_baseline_arrow_back_24),
                                    tint = Color.White
                                )
                            }
                        }
                    }
                }
            }
        })
}

@Composable
private fun nameStepComposable(
    creationState: CreationState,
    onNameChanged: (name: String) -> Unit
) {
    HintEditText(
        stringResource(id = R.string.hint_product_name),
        text = creationState.name,
        textStyle = TextStyle(
            textAlign = TextAlign.Center,
            fontSize = TextUnit.Companion.Sp(26),
            color = Color.White
        )
    ) {
        onNameChanged(it.text)
    }
}

@Composable
private fun storeStepComposable(creationState: CreationState,
                                onStoreChanged: (name: String) -> Unit) {
    HintEditText(
        stringResource(id = R.string.hint_store_name),
        text = creationState.store,
        textStyle = TextStyle(
            textAlign = TextAlign.Center,
            fontSize = TextUnit.Companion.Sp(26),
            color = Color.White
        )
    ) {
        onStoreChanged(it.text)
    }
}

@Composable
private fun frequencyStepComposable(creationState: CreationState,
                                    onFrequencyChanged: (frequency: Float) -> Unit) {
    Column(horizontalGravity = CenterHorizontally) {
        Text(
            text = stringResource(id = R.string.hint_frequency),
            style = TextStyle(
                textAlign = TextAlign.Center,
                fontSize = TextUnit.Companion.Sp(26),
                color = Color.White
            ),
            modifier = Modifier.padding(16.dp).fillMaxWidth()
        )

        TestTag(tag = "FrequencySlider") {
            Slider(
                value = creationState.frequencyCount,
                onValueChange = {
                    onFrequencyChanged(it)
                },
                color = Color.White,
                valueRange = 0f..4f,
                steps = 3,
                modifier = Modifier.fillMaxWidth().padding(16.dp)
            )
        }

        Text(
            text = stringArrayResource(id = R.array.frequency_options)[creationState.frequencyCount.toInt()],
            style = TextStyle(color = Color.White),
            modifier = Modifier.wrapContentWidth(align = CenterHorizontally)
                .padding(16.dp)
        )
    }
}

@Composable
fun HintEditText(
    hintText: String = "",
    text: String = "",
    modifier: Modifier = Modifier.None,
    textStyle: TextStyle = currentTextStyle(),
    onTextChange: (text: TextFieldValue) -> Unit
) {
    val selected = state { false }
    val state = state { TextFieldValue(text) }
    val inputField = @Composable {
        TextField(
            value = state.value,
            onFocusChange = {
                selected.value = it
            },
            modifier = modifier.plus(Modifier.fillMaxWidth()),
            onValueChange = {
                state.value = it
                onTextChange(it)
            },
            textStyle = textStyle.merge(TextStyle(textDecoration = TextDecoration.None))
        )
    }

    Layout(
        children = @Composable {
            inputField()
            Text(
                text = hintText,
                modifier = modifier,
                style = textStyle.merge(TextStyle(color = Color.White))
            )
        },
        measureBlock = { measurables: List<Measurable>, constraints: Constraints, _ ->
            val inputFieldPlace = measurables[0].measure(constraints)
            val hintEditPlace = measurables[1].measure(constraints)
            layout(
                inputFieldPlace.width,
                inputFieldPlace.height
            ) {
                inputFieldPlace.place(0.ipx, 0.ipx)
                if (state.value.text.isEmpty() && !selected.value)
                    hintEditPlace.place(0.ipx, 0.ipx)
            }
        })
}