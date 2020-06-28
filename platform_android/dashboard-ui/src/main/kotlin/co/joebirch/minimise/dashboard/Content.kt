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
import androidx.ui.material.icons.Icons
import androidx.ui.material.icons.filled.Add
import androidx.ui.material.ripple.RippleIndication
import androidx.ui.res.colorResource
import androidx.ui.text.font.FontWeight
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

enum class FabState {
    Idle, Exploded
}

private fun sizeTransitionDefinition(colorOne: Color, colorTwo: Color) = transitionDefinition {
    state(FabState.Idle) {
        this[alphaState] = 1f
        this[sizeState] = 80f
        this[colorState] = colorOne
    }
    state(FabState.Exploded) {
        this[alphaState] = 0f
        this[sizeState] = 4000f
        this[colorState] = colorTwo
    }
    transition(fromState = FabState.Idle, toState = FabState.Exploded) {
        sizeState using keyframes<Float> {
            duration = 700
            80f at 0
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
            topBar = {
                TopAppBar(title = {
                    Text(
                        text = "M",
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                            .drawOpacity(animate(if (animatingFab.value) 0f else 1f))
                    )
                }, elevation = 0.dp)
            },
            bodyContent = {
                Stack {
                    Box(
                        modifier = Modifier.fillMaxSize().zIndex(1f),
                        gravity = ContentGravity.BottomEnd,
                        children = {
                            val resources = ContextAmbient.current.resources

                            Transition(
                                definition = sizeTransitionDefinition(
                                    MaterialTheme.colors.secondary,
                                    MaterialTheme.colors.primary
                                ),
                                initState = FabState.Idle,
                                toState = if (!animatingFab.value) {
                                    FabState.Idle
                                } else FabState.Exploded
                            ) { state ->
                                Box(
                                    modifier = Modifier.wrapContentSize().clickable(
                                        onClick = {
                                            animatingFab.value = true
                                            Timer().schedule(300) {
                                                navigateToCreation()
                                            }
                                        },
                                        indication = RippleIndication(
                                            radius = 26.dp,
                                            bounded = false
                                        )
                                    ),
                                    children = {
                                        Canvas(
                                            modifier = Modifier.size(80.dp)
                                        ) {
                                            drawCircle(state[colorState], state[sizeState])
                                            val i = imageFromResource(resources, R.drawable.plus)
                                            drawImage(
                                                imageFromResource(resources, R.drawable.plus),
                                                topLeft = Offset(
                                                    (this.center.x) - (i.width / 2),
                                                    (this.center.y) - (i.width / 2)
                                                ),
                                                alpha = state[alphaState]
                                            )
                                        }
                                    })
                            }
                        })

                    Column(modifier = Modifier.zIndex(0f)) {
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
            color = colorResource(id = co.joebirch.minimise.common_ui.R.color.text_secondary),
            textAlign = TextAlign.Center,
            modifier = Modifier.gravity(align = Alignment.CenterHorizontally)
                .preferredWidth(260.dp)
        )
    }
}