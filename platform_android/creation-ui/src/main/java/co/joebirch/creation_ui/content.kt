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
import androidx.ui.res.stringArrayResource
import androidx.ui.res.stringResource
import androidx.ui.res.vectorResource
import androidx.ui.text.TextStyle
import androidx.ui.text.style.TextAlign
import androidx.ui.text.style.TextDecoration
import androidx.ui.unit.TextUnit
import androidx.ui.unit.dp
import co.joebirch.minimise.common_ui.MinimiseTheme
import co.joebirch.minimise.common_ui.setContentWithLifecycle
import co.joebirch.minimise.dashboard.CreationState
import co.joebirch.minimise.dashboard.CreationStep
import androidx.compose.Composable
import androidx.compose.state
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.ui.animation.Transition
import androidx.ui.core.Alignment.Companion.BottomEnd
import androidx.ui.core.Alignment.Companion.Top
import androidx.ui.core.focus.*
import androidx.ui.foundation.shape.corner.CircleShape
import androidx.ui.foundation.shape.corner.RoundedCornerShape
import androidx.ui.input.ImeAction
import androidx.ui.layout.RowScope.gravity
import androidx.ui.material.ripple.RippleIndication
import androidx.ui.semantics.SemanticsProperties.TestTag
import androidx.ui.text.font.FontWeight
import androidx.ui.text.style.TextOverflow

fun ViewGroup.composeDashboardContentTwo(
    lifecycleOwner: LifecycleOwner
): Any =
    setContentWithLifecycle(lifecycleOwner) {
        Box(
            backgroundColor = MaterialTheme.colors.primary,
            modifier = Modifier.fillMaxSize()
        )
}

fun ViewGroup.composeDashboardContent(
    lifecycleOwner: LifecycleOwner,
    uiState: LiveData<CreationState>,
    onNameChanged: (name: String) -> Unit,
    onCategoriesChanged: (store: List<String>) -> Unit,
    onFrequencyChanged: (frequency: Float) -> Unit,
    onRemindDays: (days: Int) -> Unit,
    onPositivesChanged: (frequency: List<String>) -> Unit,
    onNegativesChanged: (frequency: List<String>) -> Unit,
    onNextStep: () -> Unit,
    onPreviousStep: () -> Unit,
    onFormCompleted: () -> Unit
): Any = setContentWithLifecycle(lifecycleOwner) {
    ComposeInventoryContent(
        uiState, onNameChanged, onCategoriesChanged, onFrequencyChanged,
        onRemindDays, onPositivesChanged, onNegativesChanged, onNextStep, onPreviousStep,
        onFormCompleted
    )
}

@OptIn(ExperimentalLayout::class)
@Composable
private fun ComposeInventoryContent(
    uiState: LiveData<CreationState>,
    onNameChanged: (name: String) -> Unit,
    onCategoriesChanged: (store: List<String>) -> Unit,
    onFrequencyChanged: (frequency: Float) -> Unit,
    onRemindDays: (days: Int) -> Unit,
    onPositivesChanged: (frequency: List<String>) -> Unit,
    onNegativesChanged: (frequency: List<String>) -> Unit,
    onNextStep: () -> Unit,
    onPreviousStep: () -> Unit,
    onFormCompleted: () -> Unit
) {
    val viewState by uiState.observeAsState()
    if (viewState != null) {
        CreationContent(
            viewState!!.isLoading, selectedStep = viewState!!.currentStep,
            onNameChanged = onNameChanged, onCategoriesChanged = onCategoriesChanged,
            onFrequencyChanged = onFrequencyChanged,
            onRemindDays = onRemindDays,
            onPositivesChanged = onPositivesChanged,
            onNegativesChanged = onNegativesChanged,
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
        sizeState using tween(
            durationMillis = 200,
            easing = FastOutLinearInEasing
        )
        contentAlphaState using tween(
            durationMillis = 200,
            easing = FastOutLinearInEasing
        )
        alphaState using keyframes {
            durationMillis = 400
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
        //CreationStep.CATEGORY -> creationState.store.isNotEmpty()
        else -> true
    }
}

@OptIn(ExperimentalFocus::class)
@ExperimentalLayout
@Composable
internal fun CreationContent(
    isLoading: Boolean,
    creationState: CreationState,
    selectedStep: CreationStep,
    onNameChanged: (name: String) -> Unit,
    onCategoriesChanged: (name: List<String>) -> Unit,
    onFrequencyChanged: (frequency: Float) -> Unit,
    onRemindDays: (days: Int) -> Unit,
    onPositivesChanged: (frequency: List<String>) -> Unit,
    onNegativesChanged: (frequency: List<String>) -> Unit,
    onNextStep: () -> Unit,
    onPreviousStep: () -> Unit,
    onFormCompleted: () -> Unit
) {
    val animatingFab = state { true }
    val animatingContent = state { true }
    MinimiseTheme {
        Scaffold(
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
                                modifier = Modifier.gravity(Center)
                                    .fillMaxHeight()
                                    .drawOpacity(state[contentAlphaState])
                            ) {
                                stepCounter(selectedStep)
                                Box(modifier = Modifier.padding(16.dp)) {
                                    Spacer(modifier = Modifier.height(16.dp))
                                    when (selectedStep) {
                                        CreationStep.NAME -> {
                                            nameStepComposable(
                                                creationState,
                                                onNameChanged = onNameChanged
                                            )
                                        }
                                        CreationStep.CATEGORY -> {
                                            storeStepComposable(
                                                creationState,
                                                onCategories = onCategoriesChanged
                                            )
                                        }
                                        CreationStep.FREQUENCY -> {
                                            frequencyStepComposable(
                                                creationState,
                                                onFrequencyChanged = onFrequencyChanged
                                            )
                                        }
                                        CreationStep.REMIND -> {
                                            remindStepComposable(
                                                creationState,
                                                onRemindDays = onRemindDays
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
                                            finishedComposable(onFormCompleted)
                                        }
                                    }
                                }
                            }
                        }
                        if (selectedStep != CreationStep.NAME &&
                            selectedStep != CreationStep.FINISHED
                        ) {
                            Box(
                                shape = CircleShape,
                                modifier = Modifier.gravity(BottomStart).padding(16.dp)
                                    .clickable(
                                        onClick = {
                                            onNextStep()
                                        }, indication = RippleIndication(
                                            bounded = true,
                                            radius = 16.dp,
                                            color = Color.Black
                                        )
                                    )
                            ) {
                                Icon(
                                    asset = vectorResource(id = R.drawable.ic_baseline_arrow_back_24),
                                    tint = Color.White
                                )
                            }
                        }



                        if (selectedStep != CreationStep.FINISHED) {
                            Box(
                                shape = CircleShape,
                                modifier = Modifier.gravity(BottomEnd).padding(16.dp)
                                    .clickable(
                                        onClick = {
                                            onNextStep()
                                        }, indication = RippleIndication(
                                            bounded = true,
                                            radius = 16.dp,
                                            color = Color.Black
                                        )
                                    )
                            ) {
                                Icon(
                                    asset = vectorResource(id = R.drawable.ic_baseline_arrow_forward_24),
                                    tint = Color.White
                                )
                            }
                        }
                    }
                }
            })
    }
}

@Composable
private fun stepCounter(currentStep: CreationStep) {
    Row(modifier = Modifier.padding(top = 8.dp, start = 4.dp)) {
        repeat(CreationStep.values().count()) {
            Box(
                backgroundColor = MaterialTheme.colors.secondary,
                modifier = Modifier.height(6.dp).weight(1f)
                    .drawOpacity(if (it <= currentStep.ordinal) 1f else 0.6f)
                    .padding(end = 4.dp)
            )
        }
    }
}

@ExperimentalFocus
@Composable
private fun nameStepComposable(
    creationState: CreationState,
    onNameChanged: (name: String) -> Unit
) {
    ScrollableColumn(modifier = Modifier.fillMaxSize().gravity(align = Top)) {
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
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(16.dp).fillMaxWidth()) {
                Spacer(modifier = Modifier.width(16.dp))
                FilledTextField(
                    value = creationState.name,
                    onValueChange = { value ->
                        onNameChanged(value)
                    },
                    label = {

                    },
                    activeColor = Color.White,
                    shape = RoundedCornerShape(16.dp),
                    backgroundColor = MaterialTheme.colors.secondary,
                    modifier = Modifier.padding(16.dp).fillMaxWidth().sizeIn(minHeight = 80.dp)
                )
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}

@ExperimentalLayout
@Composable
private fun storeStepComposable(
    creationState: CreationState,
    onCategories: (name: List<String>) -> Unit
) {
    ScrollableColumn(modifier = Modifier.fillMaxSize().gravity(align = Top)) {
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
        val selectedItems = creationState.categories.toMutableList()
        FlowRow(
            mainAxisAlignment = MainAxisAlignment.Center,
            crossAxisSpacing = 16.dp,
            mainAxisSpacing = 16.dp,
            mainAxisSize = SizeMode.Expand
        ) {
            amenityList.forEachIndexed { _, amenity ->
                Box(
                    Modifier.clickable(onClick = {
                        if (selectedItems.contains(amenity)) {
                            selectedItems.remove(amenity)
                        } else {
                            selectedItems.add(amenity)
                        }
                        onCategories(selectedItems)
                    }).drawOpacity(if (selectedItems.contains(amenity)) 1f else 0.7f),
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

@ExperimentalFocus
@Composable
private fun positiveStepComposable(
    creationState: CreationState
) {
    val focusModifiers = listOf(FocusRequester(), FocusRequester(), FocusRequester())
    Spacer(modifier = Modifier.height(48.dp))
    ScrollableColumn(modifier = Modifier.fillMaxSize().gravity(align = Top)) {
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

@ExperimentalFocus
@Composable
private fun negativeStepComposable(
    creationState: CreationState
) {
    val states = state { androidx.ui.input.TextFieldValue() }
    val focusModifiers = listOf(FocusRequester(), FocusRequester(), FocusRequester())

    Spacer(modifier = Modifier.height(48.dp))
    ScrollableColumn(modifier = Modifier.fillMaxSize().gravity(align = Top)) {
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

@Composable
private fun finishedComposable(
    onFormCompleted: () -> Unit
) {
    Column(
        modifier = Modifier.gravity(align = Top).fillMaxSize(),
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        Text(
            text = "All done!",
            textAlign = TextAlign.Center,
            fontSize = TextUnit.Companion.Sp(26),
            modifier = Modifier.fillMaxWidth().padding(16.dp).weight(1f),
            color = Color.White
        )

        Text(
            text = "We'll come back to you in a few days before you purchase this item. " +
                    "Until then, take a few moments to reflect on the needs for this purchase.",
            textAlign = TextAlign.Center,
            fontSize = TextUnit.Companion.Sp(18),
            modifier = Modifier.fillMaxWidth().weight(4f).padding(16.dp),
            color = Color.White
        )

        Box(
            modifier = Modifier.gravity(align = CenterHorizontally).weight(1f),
            gravity = Center
        ) {
            Button(
                onClick = {
                    onFormCompleted()
                },
                backgroundColor = MaterialTheme.colors.secondary
            ) {
                Text(text = "Close", color = Color.White)
            }
        }
    }
}

@OptIn(ExperimentalFocus::class)
@Composable
fun labelTextField(
    position: Int,
    requester: FocusRequester,
    nextModifier: FocusRequester? = null
) {
    val states = state { androidx.ui.input.TextFieldValue() }
    val hasFocus = state { false }
    Box(
        shape = RoundedCornerShape(16.dp),
        backgroundColor = MaterialTheme.colors.secondary,
        modifier = Modifier.fillMaxWidth()
            .focusRequester(requester)
            .drawOpacity(if (hasFocus.value) 1f else 0.6f)
    ) {
        Row(modifier = Modifier.padding(16.dp).fillMaxWidth()) {
            Text(
                text = "$position. ", color = Color.White, fontWeight = FontWeight.Bold,
                fontSize = TextUnit.Companion.Sp(16)
            )
            FilledTextField(
                value = states.value,
                onValueChange = { value ->
                    states.value = value
                },
                label = {

                },
                onFocusChanged = { focus ->
                    hasFocus.value = focus
                },
                onImeActionPerformed = { _, _ ->
                    nextModifier?.requestFocus()
                },
                imeAction = if (position < 3) ImeAction.Next else ImeAction.Done,
                activeColor = Color.White,
                backgroundColor = Color.Transparent,
                modifier = Modifier.padding(16.dp).fillMaxWidth()
                    .focusRequester(requester)
            )
        }
    }
}

@Composable
private fun frequencyStepComposable(
    creationState: CreationState,
    onFrequencyChanged: (frequency: Float) -> Unit
) {
    Spacer(modifier = Modifier.height(48.dp))
    ScrollableColumn(
        modifier = Modifier.fillMaxSize().gravity(align = Top),
        horizontalGravity = CenterHorizontally
    ) {
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

        Text(
            text = stringArrayResource(id = R.array.frequency_options)[creationState.frequencyCount.toInt()],
            style = currentTextStyle().merge(TextStyle(color = Color.White)),
            modifier = Modifier.wrapContentWidth(align = CenterHorizontally)
                .padding(16.dp)
        )
    }
}

@Composable
private fun remindStepComposable(
    creationState: CreationState,
    onRemindDays: (remindDays: Int) -> Unit
) {
    Spacer(modifier = Modifier.height(48.dp))
    ScrollableColumn(
        modifier = Modifier.fillMaxSize().gravity(align = Top),
        horizontalGravity = CenterHorizontally
    ) {
        Text(
            text = stringResource(id = R.string.hint_remind_days),
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
        Slider(
            value = creationState.daysToRemind.toFloat(),
            onValueChange = {
                onRemindDays(it.toInt())
            },
            color = Color.White,
            valueRange = 0f..4f,
            steps = 3,
            modifier = Modifier.fillMaxWidth().padding(16.dp)
        )

        Text(
            text = stringArrayResource(id = R.array.reminder_options)[creationState.daysToRemind.toInt()],
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