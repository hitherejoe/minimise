package co.joebirch.minimise.creation

import android.annotation.SuppressLint
import android.view.ViewGroup
import androidx.compose.animation.core.*
import androidx.compose.animation.transition
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.RowScope.gravity
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.ripple.RippleIndication
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment.Companion.BottomEnd
import androidx.compose.ui.Alignment.Companion.BottomStart
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Alignment.Companion.Top
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawOpacity
import androidx.compose.ui.focus.ExperimentalFocus
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.setContent
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import co.joebirch.minimise.common_ui.MinimiseTheme
import co.joebirch.minimise.dashboard.CreationState
import co.joebirch.minimise.dashboard.CreationStep
import androidx.lifecycle.LiveData
import androidx.ui.tooling.preview.Preview

fun ViewGroup.composeDashboardContent(
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
): Any = setContent(Recomposer.current()) {
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
    viewState?.let {
        CreationContent(
            onNameChanged = onNameChanged, onCategoriesChanged = onCategoriesChanged,
            onFrequencyChanged = onFrequencyChanged,
            onRemindDays = onRemindDays,
            onPositivesChanged = onPositivesChanged,
            onNegativesChanged = onNegativesChanged,
            onNextStep = onNextStep, onPreviousStep = onPreviousStep,
            onFormCompleted = onFormCompleted, creationState = it
        )
    }
}

val sizeState = FloatPropKey()
val alphaState = FloatPropKey()
val contentAlphaState = FloatPropKey()

@SuppressLint("Range")
private val sizeTransitionDefinition = transitionDefinition<String> {
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

@OptIn(ExperimentalFocus::class)
@ExperimentalLayout
@Composable
internal fun CreationContent(
    creationState: CreationState,
    onNameChanged: ((name: String) -> Unit)? = null,
    onCategoriesChanged: ((store: List<String>) -> Unit)? = null,
    onFrequencyChanged: ((frequency: Float) -> Unit)? = null,
    onRemindDays: ((days: Int) -> Unit)? = null,
    onPositivesChanged: ((frequency: List<String>) -> Unit)? = null,
    onNegativesChanged: ((frequency: List<String>) -> Unit)? = null,
    onNextStep: (() -> Unit)? = null,
    onPreviousStep: (() -> Unit)? = null,
    onFormCompleted: (() -> Unit)? = null
) {
    MinimiseTheme {
        Scaffold(
            bodyContent = {
                Stack(modifier = Modifier.fillMaxSize()) {
                    Box(
                        backgroundColor = MaterialTheme.colors.primary,
                        modifier = Modifier.fillMaxSize()
                    )
                    if (creationState.isLoading) {
                        CircularProgressIndicator(
                            color = MaterialTheme.colors.secondary,
                            modifier = Modifier.gravity(Center)
                        )
                    } else {
                        val state = transition(
                            definition = sizeTransitionDefinition,
                            initState = "A",
                            toState = "B"
                        )
                        Column(
                            verticalArrangement = Arrangement.Center,
                            modifier = Modifier.gravity(Center)
                                .fillMaxHeight()
                                .drawOpacity(state[contentAlphaState])
                        ) {
                            stepCounter(
                                creationState.currentStep
                            )
                            Box(modifier = Modifier.padding(16.dp)) {
                                Spacer(modifier = Modifier.height(16.dp))
                                when (creationState.currentStep) {
                                    CreationStep.NAME -> {
                                        nameStepComposable(
                                            creationState,
                                            onNameChanged = onNameChanged,
                                            onNextStep
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
                                        negativeStepComposable()
                                    }
                                    CreationStep.FINISHED -> {
                                        finishedComposable(
                                            onFormCompleted
                                        )
                                    }
                                }
                            }
                        }
                        if (creationState.currentStep != CreationStep.NAME &&
                            creationState.currentStep != CreationStep.FINISHED
                        ) {
                            Box(
                                shape = CircleShape,
                                modifier = Modifier.gravity(BottomStart).padding(16.dp)
                                    .clickable(
                                        onClick = {
                                            onPreviousStep?.invoke()
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

                        if (creationState.currentStep != CreationStep.FINISHED) {
                            Box(
                                shape = CircleShape,
                                modifier = Modifier.gravity(BottomEnd).padding(16.dp)
                                    .clickable(
                                        onClick = {
                                            onNextStep?.invoke()
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
internal fun stepCounter(currentStep: CreationStep) {
    Row(modifier = Modifier.padding(top = 8.dp, start = 4.dp)) {
        CreationStep.values().forEachIndexed { index, creationStep ->
            val isStepViewed = index <= currentStep.ordinal
            Box(
                backgroundColor = MaterialTheme.colors.secondary,
                modifier = Modifier.height(6.dp).weight(1f)
                    .drawOpacity(if (isStepViewed) 1f else 0.5f)
                    .testTag(if (isStepViewed) creationStep.name else "")
                    .padding(end = 4.dp)
            )
        }
    }
}

@Composable
fun roundedBackgroundBox(
    children: @Composable () -> Unit = emptyContent()
) {
    Box(
        shape = RoundedCornerShape(16.dp),
        backgroundColor = MaterialTheme.colors.secondary,
        modifier = Modifier.fillMaxWidth()
    ) {
        children()
    }
}

@ExperimentalFocus
@Composable
private fun nameStepComposable(
    creationState: CreationState,
    onNameChanged: ((name: String) -> Unit)?,
    onNextStep: (() -> Unit)?
) {
    ScrollableColumn(modifier = Modifier.fillMaxSize().gravity(align = Top)) {
        Spacer(modifier = Modifier.height(48.dp))
        titleComposable(title = stringResource(id = R.string.hint_product_name))

        Spacer(modifier = Modifier.height(48.dp))
        roundedBackgroundBox {
            Column(modifier = Modifier.padding(16.dp).fillMaxWidth()) {
                Spacer(modifier = Modifier.width(16.dp))
                TextField(
                    value = creationState.name,
                    onValueChange = { value ->
                        onNameChanged?.invoke(value)
                    },
                    label = {

                    },
                    imeAction = ImeAction.Next,
                    onImeActionPerformed = { imeAction, _ ->
                        if (imeAction == ImeAction.Next) {
                            onNextStep?.invoke()
                        }
                    },
                    activeColor = Color.White,
                    backgroundColor = Color.Transparent,
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
    onCategories: ((store: List<String>) -> Unit)?
) {
    ScrollableColumn(modifier = Modifier.fillMaxSize().gravity(align = Top)) {
        Spacer(modifier = Modifier.height(48.dp))
        titleComposable(title = stringResource(id = R.string.title_categories))

        Spacer(modifier = Modifier.height(48.dp))
        val items = listOf(
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
            items.forEachIndexed { _, amenity ->
                Box(
                    Modifier.clickable(onClick = {
                        if (selectedItems.contains(amenity)) {
                            selectedItems.remove(amenity)
                        } else {
                            selectedItems.add(amenity)
                        }
                        onCategories?.invoke(selectedItems)
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
        titleComposable(title = stringResource(id = R.string.title_positive_reasons))

        Spacer(modifier = Modifier.height(48.dp))

        labelTextField(
            1,
            focusModifiers[0],
            focusModifiers[1]
        )
        Spacer(modifier = Modifier.height(36.dp))
        labelTextField(
            2,
            focusModifiers[1],
            focusModifiers[2]
        )
        Spacer(modifier = Modifier.height(36.dp))
        labelTextField(3, focusModifiers[2])
    }
}

@ExperimentalFocus
@Composable
internal fun negativeStepComposable(
) {
    val focusModifiers = listOf(FocusRequester(), FocusRequester(), FocusRequester())

    Spacer(modifier = Modifier.height(48.dp))
    ScrollableColumn(modifier = Modifier.fillMaxSize().gravity(align = Top)) {
        titleComposable(title = stringResource(id = R.string.title_negative_reasons))
        Spacer(modifier = Modifier.height(48.dp))

        labelTextField(
            1,
            focusModifiers[0],
            focusModifiers[1]
        )
        Spacer(modifier = Modifier.height(36.dp))
        labelTextField(
            2,
            focusModifiers[1],
            focusModifiers[2]
        )
        Spacer(modifier = Modifier.height(36.dp))
        labelTextField(3, focusModifiers[2])
    }
}

@Composable
private fun finishedComposable(
    onFormCompleted: (() -> Unit)?
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
                    onFormCompleted?.invoke()
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
    val states = state { TextFieldValue() }
    val hasFocus = state { false }
    roundedBackgroundBox {
        Row(modifier = Modifier.padding(16.dp).fillMaxWidth()) {
            Text(
                text = "$position. ", color = Color.White, fontWeight = FontWeight.Bold,
                fontSize = TextUnit.Companion.Sp(16)
            )
            TextField(
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
    onFrequencyChanged: ((frequency: Float) -> Unit)?
) {
    Spacer(modifier = Modifier.height(48.dp))
    ScrollableColumn(
        modifier = Modifier.fillMaxSize().gravity(align = Top),
        horizontalGravity = CenterHorizontally
    ) {
        titleComposable(title = stringResource(id = R.string.hint_frequency))
        Spacer(modifier = Modifier.height(48.dp))
        Slider(
            value = creationState.frequencyCount,
            onValueChange = {
                onFrequencyChanged?.invoke(it)
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
internal fun titleComposable(title: String) {
    Text(
        text = title,
        style = TextStyle(
            textAlign = TextAlign.Center,
            fontSize = TextUnit.Companion.Sp(26),
            color = MaterialTheme.colors.onPrimary
        ),
        modifier = Modifier.padding(16.dp).fillMaxWidth()
    )
}

@Composable
private fun remindStepComposable(
    creationState: CreationState,
    onRemindDays: ((days: Int) -> Unit)?
) {
    Spacer(modifier = Modifier.height(48.dp))
    ScrollableColumn(
        modifier = Modifier.fillMaxSize().gravity(align = Top),
        horizontalGravity = CenterHorizontally
    ) {
        titleComposable(title = stringResource(id = R.string.hint_remind_days))
        Spacer(modifier = Modifier.height(48.dp))
        Slider(
            value = creationState.daysToRemind.toFloat(),
            onValueChange = {
                onRemindDays?.invoke(it.toInt())
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