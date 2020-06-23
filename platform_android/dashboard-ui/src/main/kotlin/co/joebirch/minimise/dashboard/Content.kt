package co.joebirch.minimise.dashboard

import android.view.ViewGroup
import androidx.animation.FloatPropKey
import androidx.animation.transitionDefinition
import androidx.compose.Composable
import androidx.compose.state
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.ui.animation.*
import androidx.ui.core.*
import androidx.ui.foundation.*
import androidx.ui.geometry.Offset
import androidx.ui.graphics.Color
import androidx.ui.graphics.imageFromResource
import androidx.ui.layout.*
import androidx.ui.livedata.observeAsState
import androidx.ui.material.*
import androidx.ui.text.style.TextAlign
import androidx.ui.unit.dp
import co.joebirch.minimise.authentication.ui.R
import co.joebirch.minimise.common_ui.MinimiseTheme
import co.joebirch.minimise.common_ui.setContentWithLifecycle
import java.util.*
import kotlin.concurrent.schedule

fun ViewGroup.composeDashboardContent(
    lifecycleOwner: LifecycleOwner,
    state: LiveData<DashboardState>,
    categories: List<Category>,
    updateSelectedCategory: (Category) -> Unit,
    navigateToCreation: () -> Unit
): Any = setContentWithLifecycle(lifecycleOwner) {
    ComposeInventoryContent(state, categories, updateSelectedCategory, navigateToCreation)
}

@Composable
private fun ComposeInventoryContent(
    state: LiveData<DashboardState>,
    categories: List<Category>,
    updateSelectedCategory: (Category) -> Unit,
    navigateToCreation: () -> Unit
) {
    val viewState = state.observeAsState()
    DashboardContent(
        viewState.value!!.selectedCategory, categories,
        viewState.value!!.pendingBelongings,
        updateSelectedCategory,
        navigateToCreation
    )
}

val sizeState = FloatPropKey()
val alphaState = FloatPropKey()
val colorState = ColorPropKey()

private fun sizeTransitionDefinition(colorOne: Color, colorTwo: Color) = transitionDefinition {
    state("A") {
        this[alphaState] = 1f
        this[sizeState] = 75f
        this[colorState] = colorOne
    }
    state("B") {
        this[alphaState] = 0f
        this[sizeState] = 4000f
        this[colorState] = colorTwo
    }
    transition(fromState = "A", toState = "B") {
        sizeState using keyframes<Float> {
            duration = 700
            75f at 0
            35f at 120
            4000f at 700
        }
    }

}

@Composable
private fun DashboardContent(
    currentCategory: Category,
    categories: List<Category>,
    pendingBelongings: List<Belonging>,
    updateSelectedCategory: (Category) -> Unit,
    navigateToCreation: () -> Unit
) {
    MinimiseTheme {
        val tabTitles = categories.map { it }
        val animatingFab = state { false }
        Scaffold(
            topAppBar = {
                TopAppBar(title = {
                    Text(
                        text = "M",
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                            .drawOpacity(animate(if (animatingFab.value) 0f else 1f))
                    )
                }, elevation = 0.dp)
            },
            floatingActionButton = {
                val resources = ContextAmbient.current.resources

                Transition(
                    definition = sizeTransitionDefinition(
                        MaterialTheme.colors.secondary,
                        MaterialTheme.colors.primary
                    ),
                    initState = "A",
                    toState = if (!animatingFab.value) "A" else "B"
                ) { state ->
                    Box(modifier = Modifier.wrapContentSize().clickable(onClick = {
                        animatingFab.value = true
                        Timer().schedule(500) {
                            navigateToCreation()
                        }
                    }), gravity = ContentGravity.BottomEnd, children = {
                        Canvas(
                            modifier = Modifier
                                .wrapContentSize(align = Alignment.Center)
                                .padding(20.dp)
                        ) {
                            drawCircle(state[colorState], state[sizeState])
                            drawImage(
                                imageFromResource(resources, R.drawable.plus),
                                topLeft = Offset(-33.5f, -33.5f),
                                alpha = state[alphaState]
                            )
                        }
                    })
                }
                /*

Transition(
definition = sizeTransitionDefinition,
toState = if(!animatingFab.value) "A" else "B",
onStateChangeFinished = {
    navigateToCreation()
}
) { state ->

FloatingActionButton(
    onClick = {
        animatingFab.value = true
    },
    backgroundColor = animate(if (animatingFab.value) MaterialTheme.colors.primary else MaterialTheme.colors.secondary),
    modifier = Modifier.width(state[sizeState].dp).height(state[sizeState].dp)
        .offset((-16).dp, (-16).dp)
) {
    Icon(
        asset = Icons.Filled.Add,
        modifier = Modifier.drawOpacity(animate(if (animatingFab.value) 0f else 1f))
    )
}

                } */

            },
            bodyContent = {
                Column {
                    TabRow(
                        items = tabTitles,
                        selectedIndex = categories.indexOf(currentCategory)
                    ) { index, currentTab ->
                        Tab(
                            selected = categories.indexOf(currentCategory) == index,
                            onSelected = { updateSelectedCategory(categories[index]) }
                        )
                        {
                            Text(
                                text = currentTab.title,
                                modifier = Modifier.padding(16.dp)
                            )
                        }
                    }
                    if ((currentCategory == Category.PendingBelongings &&
                                pendingBelongings.isEmpty()) ||
                        (currentCategory == Category.Belongings &&
                                pendingBelongings.isEmpty())
                    ) {
                        emptyView(currentCategory)
                    }
                }
            })
    }
}

@Composable
private fun emptyView(category: Category) {
    val message = if (category == Category.PendingBelongings) {
        "It doesn't look like you have any items that you're considering buying."
    } else {
        "You don't currently have any belongings that you've purchased."
    }
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = message,
            textAlign = TextAlign.Center,
            modifier = Modifier.gravity(align = Alignment.CenterHorizontally)
                .preferredWidth(260.dp)
        )
    }
}