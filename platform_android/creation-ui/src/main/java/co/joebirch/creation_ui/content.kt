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
import androidx.compose.frames.modelListOf
import androidx.compose.state
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.ui.animation.ColorPropKey
import androidx.ui.animation.Transition
import androidx.ui.animation.animate
import androidx.ui.core.Alignment.Companion.CenterVertically
import androidx.ui.core.Alignment.Companion.Top
import androidx.ui.core.focus.FocusModifier
import androidx.ui.core.focus.FocusState
import androidx.ui.core.focus.focusState
import androidx.ui.foundation.shape.corner.RoundedCornerShape
import androidx.ui.geometry.Offset
import androidx.ui.graphics.imageFromResource
import androidx.ui.input.ImeAction
import androidx.ui.layout.RowScope.gravity
import androidx.ui.text.font.FontWeight
import androidx.ui.text.style.TextOverflow
import java.util.*
import kotlin.concurrent.schedule

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

@OptIn(ExperimentalLayout::class)
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
        CreationStep.CATEGORY -> creationState.store.isNotEmpty()
        else -> true
    }
}

@ExperimentalLayout
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
                                onNextStep()
                            }), gravity = ContentGravity.BottomEnd, children = {
                                Canvas(
                                    modifier = Modifier
                                        .wrapContentSize(align = Alignment.Center)
                                        .padding(20.dp)
                                ) {
                                    val image = imageFromResource(resources, R.drawable.arrow_right)
                                    drawCircle(buttonColor, state[sizeState])
                                    drawImage(
                                        image,
                                        topLeft = Offset(
                                            (this.center.x) - (image.width / 2),
                                            (this.center.y) - (image.width / 2)
                                        ),
                                        alpha = state[alphaState]
                                    )
                                }
                            })
                        }
                    }
                }
            },
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
                                    CreationStep.CATEGORY -> {
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
                                    CreationStep.POSITIVE -> {
                                        positiveStepComposable(
                                            creationState
                                        )
                                    }
                                    CreationStep.NEGATIVE -> {
                                        negativeStepComposable(creationState = creationState)
                                    }
                                    CreationStep.FINISHED -> {
                                        finishedComposable()
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
    VerticalScroller(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.gravity(align = Top)) {
            Spacer(modifier = Modifier.height(48.dp))
            Text(
                text = stringResource(id = R.string.hint_product_name),
                style = TextStyle(
                    textAlign = TextAlign.Center,
                    fontSize = TextUnit.Companion.Sp(26),
                    color = Color.White
                ),
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(48.dp))
            Box(
                shape = RoundedCornerShape(16.dp),
                backgroundColor = MaterialTheme.colors.secondary,
                modifier = Modifier.fillMaxWidth().sizeIn(minHeight = 80.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp).fillMaxWidth()) {
                    val state = state { androidx.ui.input.TextFieldValue(creationState.name) }
                    Spacer(modifier = Modifier.width(16.dp))
                    TextField(
                        value = state.value,
                        modifier = Modifier.padding(16.dp),
                        onValueChange = { value ->
                            onNameChanged(value.text)
                        },
                        cursorColor = Color.White,
                        textColor = Color.White
                    )
                    Divider(
                        modifier = Modifier.fillMaxWidth().height(2.dp)
                            .padding(start = 8.dp, end = 8.dp),
                        color = Color.White
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        }
    }
}

@ExperimentalLayout
@Composable
private fun storeStepComposable(
    creationState: CreationState,
    modifier: Modifier,
    onStoreChanged: (name: String) -> Unit
) {
    VerticalScroller(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.gravity(align = Top)) {
            Spacer(modifier = Modifier.height(48.dp))
            Text(
                text = "What categories does this item belong to?",
                textAlign = TextAlign.Center,
                fontSize = TextUnit.Companion.Sp(26),
                modifier = Modifier.fillMaxWidth(),
                color = Color.White
            )
            Spacer(modifier = Modifier.height(48.dp))
            val amenityList = listOf(
                "Art",
                "Automotive",
                "Beauty",
                "Books",
                "Clothing",
                "Electronics",
                "Gaming",
                "Tools",
                "Hobbies"
            )
            val selectedItems by state { modelListOf<Int>() }
            FlowRow(
                mainAxisAlignment = MainAxisAlignment.Center,
                crossAxisSpacing = 16.dp,
                mainAxisSpacing = 16.dp,
                mainAxisSize = SizeMode.Expand
            ) {
                amenityList.forEachIndexed { index, amenity ->
                    Box(
                        Modifier.clickable(onClick = {
                            if (selectedItems.contains(index)) {
                                selectedItems.remove(index)
                            } else {
                                selectedItems.add(index)
                            }
                        }).drawOpacity(if (selectedItems.contains(index)) 1f else 0.7f),
                        children = {
                            Text(
                                text = amenity,
                                overflow = TextOverflow.Ellipsis,
                                color = Color.White,
                                modifier = Modifier.padding(12.dp)
                            )
                        },
                        shape = RoundedCornerShape(16.dp),
                        backgroundColor = MaterialTheme.colors.secondary
                    )
                }
            }
        }
    }
}

@Composable
private fun positiveStepComposable(
    creationState: CreationState
) {
    val states = state { androidx.ui.input.TextFieldValue() }
    val focusModifiers = listOf(FocusModifier(), FocusModifier(), FocusModifier())
    VerticalScroller(modifier = Modifier.fillMaxSize()) {
        Spacer(modifier = Modifier.height(48.dp))
        Column(modifier = Modifier.gravity(align = Top)) {
            Text(
                text = "Can you list some reasons why you need this item?",
                textAlign = TextAlign.Center,
                fontSize = TextUnit.Companion.Sp(26),
                modifier = Modifier.fillMaxWidth(),
                color = Color.White
            )
            Spacer(modifier = Modifier.height(48.dp))

            labelTextField(1, focusModifiers[0], focusModifiers[1])
            Spacer(modifier = Modifier.height(36.dp))
            labelTextField(2, focusModifiers[1], focusModifiers[2])
            Spacer(modifier = Modifier.height(36.dp))
            labelTextField(3, focusModifiers[2])
        }
    }
}

@Composable
private fun negativeStepComposable(
    creationState: CreationState
) {
    val states = state { androidx.ui.input.TextFieldValue() }
    val focusModifiers = listOf(FocusModifier(), FocusModifier(), FocusModifier())

    VerticalScroller(modifier = Modifier.fillMaxSize()) {
        Spacer(modifier = Modifier.height(48.dp))
        Column(modifier = Modifier.gravity(align = Top)) {
            Text(
                text = "How about why you might not need this item?",
                textAlign = TextAlign.Center,
                fontSize = TextUnit.Companion.Sp(26),
                modifier = Modifier.fillMaxWidth(),
                color = Color.White
            )
            Spacer(modifier = Modifier.height(48.dp))

            labelTextField(1, focusModifiers[0], focusModifiers[1])
            Spacer(modifier = Modifier.height(36.dp))
            labelTextField(2, focusModifiers[1], focusModifiers[2])
            Spacer(modifier = Modifier.height(36.dp))
            labelTextField(3, focusModifiers[2])
        }
    }
}

@Composable
private fun finishedComposable(
) {
    VerticalScroller(modifier = Modifier.fillMaxSize()) {
        Spacer(modifier = Modifier.height(48.dp))
        Column(modifier = Modifier.gravity(align = Top)) {
            Text(
                text = "All done!",
                textAlign = TextAlign.Center,
                fontSize = TextUnit.Companion.Sp(26),
                modifier = Modifier.fillMaxWidth(),
                color = Color.White
            )
            Spacer(modifier = Modifier.height(48.dp))

            TextButton(onClick = {}) {
                Text(text = "Close", color = Color.White)
            }
        }
    }
}

@Composable
fun labelTextField(
    position: Int,
    focusModifiers: FocusModifier,
    nextModifier: FocusModifier? = null
) {
    val states = state { androidx.ui.input.TextFieldValue() }
    val hasFocus = focusModifiers.focusState == FocusState.Focused
    Box(
        shape = RoundedCornerShape(16.dp),
        backgroundColor = MaterialTheme.colors.secondary,
        modifier = Modifier.fillMaxWidth().sizeIn(minHeight = 80.dp)
            .drawOpacity(if (hasFocus) 1f else 0.6f)
    ) {
        Row(modifier = Modifier.padding(16.dp).fillMaxWidth()) {
            Text(text = "$position. ", color = Color.White, fontWeight = FontWeight.Bold,
            fontSize = TextUnit.Companion.Sp(16))
            Spacer(modifier = Modifier.width(16.dp))
            TextField(modifier = focusModifiers, value = states.value, onValueChange = {
                states.value = it
            }, cursorColor = Color.White, textColor = Color.White,
            imeAction = if (position < 3) ImeAction.Next else ImeAction.Done,
            onImeActionPerformed = {
                if (position < 3) {
                    focusModifiers.freeFocus()
                    nextModifier?.requestFocus()
                } else {
                    // go to next
                }
            })
        }
    }
}

@Composable
private fun frequencyStepComposable(
    creationState: CreationState,
    onFrequencyChanged: (frequency: Float) -> Unit
) {
    VerticalScroller(modifier = Modifier.fillMaxSize()) {
        Spacer(modifier = Modifier.height(48.dp))
        Column(modifier = Modifier.gravity(align = Top), horizontalGravity = CenterHorizontally) {
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
            Spacer(modifier = Modifier.height(48.dp))
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