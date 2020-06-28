package co.joebirch.creation_ui

import android.view.ViewGroup
import androidx.animation.FastOutLinearInEasing
import androidx.animation.FloatPropKey
import androidx.animation.transitionDefinition
import androidx.animation.*
import androidx.compose.*
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
import androidx.ui.text.font.FontFamily
import androidx.ui.text.style.TextAlign
import androidx.ui.text.style.TextDecoration
import androidx.ui.unit.TextUnit
import androidx.ui.unit.dp
import co.joebirch.minimise.common_ui.MinimiseTheme
import co.joebirch.minimise.common_ui.setContentWithLifecycle
import co.joebirch.minimise.dashboard.CreationState
import co.joebirch.minimise.dashboard.CreationStep
import androidx.animation.transitionDefinition
import androidx.compose.Composable
import androidx.compose.state
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.ui.animation.ColorPropKey
import androidx.ui.animation.Transition
import androidx.ui.animation.animate
import androidx.ui.geometry.Offset
import androidx.ui.graphics.imageFromResource

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
    ComposeInventoryContent(
        uiState, onNameChanged, onStoreChanged, onFrequencyChanged,
        onNextStep, onPreviousStep, onFormCompleted
    )
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

val sizeState = FloatPropKey()
val alphaState = FloatPropKey()
val contentAlphaState = FloatPropKey()

private val sizeTransitionDefinition = transitionDefinition {
    state("A") {
        this[sizeState] = 0f
        this[alphaState] = 0f
        this[contentAlphaState] = 0f
    }
    state("B") {
        this[sizeState] = 80f
        this[alphaState] = 1f
        this[contentAlphaState] = 1f
    }

    transition(fromState = "A", toState = "B") {
        sizeState using tween<Float> {
            duration = 200
            easing = FastOutLinearInEasing
        }
        contentAlphaState using tween<Float> {
            duration = 200
            easing = FastOutLinearInEasing
        }
        alphaState using keyframes<Float> {
            duration = 400
            0f at 0
            0.1f at 225
            1f at 400
        }
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
    val animatingFab = state { true }
    val animatingContent = state { true }
    MinimiseTheme {
        Scaffold(
            floatingActionButton = {
                if (!isLoading) {
                    TestTag(tag = "NextButton") {
                        val resources = ContextAmbient.current.resources
                        val buttonColor = MaterialTheme.colors.secondary
                        Transition(
                            definition = sizeTransitionDefinition,
                            initState = "A",
                            toState = "B",
                            onStateChangeFinished = {
                                animatingFab.value = false
                            }
                        ) { state ->
                            Box(modifier = Modifier.wrapContentSize().clickable(onClick = {
                                animatingFab.value = true
                            }), gravity = ContentGravity.BottomEnd, children = {
                                Canvas(
                                    modifier = Modifier
                                        .wrapContentSize(align = Alignment.Center)
                                        .padding(20.dp)
                                ) {
                                    drawCircle(buttonColor, state[sizeState])
                                    drawImage(
                                        imageFromResource(resources, R.drawable.arrow_right),
                                        topLeft = Offset(-33.5f, -33.5f),
                                        alpha = state[alphaState]
                                    )
                                }
                            })
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
                        Transition(
                            definition = sizeTransitionDefinition,
                            initState = "A",
                            toState = "B"
                        ) { state ->
                            Column(
                                verticalArrangement = Arrangement.Center,
                                modifier = Modifier.gravity(Center).padding(16.dp)
                                    .fillMaxHeight()
                                    .drawOpacity(state[contentAlphaState])
                            ) {
                                when (selectedStep) {
                                    CreationStep.NAME -> {
                                        nameStepComposable(
                                            creationState,
                                            onNameChanged = onNameChanged,
                                            modifier = Modifier.wrapContentHeight()
                                        )
                                    }
                                    CreationStep.STORE -> {
                                        storeStepComposable(
                                            creationState,
                                            onStoreChanged = onStoreChanged,
                                            modifier = Modifier.wrapContentHeight()
                                        )
                                    }
                                    CreationStep.FREQUENCY -> {
                                        frequencyStepComposable(
                                            creationState,
                                            onFrequencyChanged = onFrequencyChanged
                                        )
                                    }
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
}

@Composable
private fun nameStepComposable(
    creationState: CreationState,
    modifier: Modifier,
    onNameChanged: (name: String) -> Unit
) {
    HintEditText(
        stringResource(id = R.string.hint_product_name),
        modifier = modifier,
        text = creationState.name,
        textStyle = currentTextStyle().merge(
            TextStyle(
                textAlign = TextAlign.Center,
                fontSize = TextUnit.Companion.Sp(26),
                color = Color.White
            )
        )
    ) {
        onNameChanged(it.text)
    }
}

@Composable
private fun storeStepComposable(
    creationState: CreationState,
    modifier: Modifier,
    onStoreChanged: (name: String) -> Unit
) {
    HintEditText(
        stringResource(id = R.string.hint_store_name),
        modifier = modifier,
        text = creationState.store,
        textStyle = currentTextStyle().merge(
            TextStyle(
                textAlign = TextAlign.Center,
                fontSize = TextUnit.Companion.Sp(26),
                color = Color.White
            )
        )
    ) {
        onStoreChanged(it.text)
    }
}

@Composable
private fun frequencyStepComposable(
    creationState: CreationState,
    onFrequencyChanged: (frequency: Float) -> Unit
) {
    Column(horizontalGravity = CenterHorizontally) {
        Text(
            text = stringResource(id = R.string.hint_frequency),
            style = currentTextStyle().merge(
                TextStyle(
                    textAlign = TextAlign.Center,
                    fontSize = TextUnit.Companion.Sp(26),
                    color = Color.White
                )
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
            style = currentTextStyle().merge(TextStyle(color = Color.White)),
            modifier = Modifier.wrapContentWidth(align = CenterHorizontally)
                .padding(16.dp)
        )
    }
}

@Composable
fun HintEditText(
    hintText: String = "",
    text: String = "",
    modifier: Modifier,
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
            modifier = Modifier.fillMaxWidth().plus(modifier),
            onValueChange = {
                state.value = it
                onTextChange(it)
            },
            cursorColor = Color.White,
            textStyle = textStyle.merge(TextStyle(textDecoration = TextDecoration.None))
        )
    }

    Layout(
        children = @Composable {
            inputField()
            Text(
                text = hintText,
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
                inputFieldPlace.place(0, 0)
                if (state.value.text.isEmpty() && !selected.value)
                    hintEditPlace.place(0, 0)
            }
        })
}