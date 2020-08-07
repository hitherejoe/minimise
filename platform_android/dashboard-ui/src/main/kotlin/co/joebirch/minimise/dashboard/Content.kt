package co.joebirch.minimise.dashboard

import android.annotation.SuppressLint
import android.view.ViewGroup
import androidx.compose.animation.ColorPropKey
import androidx.compose.animation.animate
import androidx.compose.animation.core.FloatPropKey
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.transitionDefinition
import androidx.compose.animation.transition
import androidx.compose.foundation.Box
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Text
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumnItems
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.ripple.RippleIndication
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.state
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawOpacity
import androidx.compose.ui.draw.drawShadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.imageFromResource
import androidx.compose.ui.platform.ContextAmbient
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
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

@SuppressLint("Range")
private fun sizeTransitionDefinition(colorOne: Color, colorTwo: Color) = transitionDefinition<FabState> {
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
        sizeState using keyframes {
            durationMillis = 700
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
            backgroundColor = Color.White,
            floatingActionButton = {
                val resources = ContextAmbient.current.resources
                Box(
                    modifier = Modifier.clickable(
                        onClick = {
                            animatingFab.value = true
                            Timer().schedule(450) {
                                navigateToCreation()
                            }
                        }, indication = RippleIndication(
                            radius = 26.dp,
                            bounded = false
                        )
                    ).wrapContentSize()
                ) {
                    val state = transition(
                        definition = sizeTransitionDefinition(
                            MaterialTheme.colors.secondary,
                            MaterialTheme.colors.primary
                        ),
                        initState = FabState.Idle,
                        toState = if (!animatingFab.value) {
                            FabState.Idle
                        } else FabState.Exploded
                    )
                        Canvas(
                            modifier = Modifier.size(40.dp)
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
                }
            },
            bodyContent = {
                Column {
                    Box(
                        backgroundColor = MaterialTheme.colors.primary,
                        shape = RoundedCornerShape(0.dp, 0.dp, 18.dp, 18.dp)
                    ) {
                        Spacer(modifier = Modifier.preferredHeight(8.dp))
                        TopAppBar(title = {
                            Text(
                                text = "Inventory",
                                color = MaterialTheme.colors.onPrimary,
                                textAlign = TextAlign.Start,
                                fontSize = TextUnit.Companion.Sp(20),
                                fontWeight = FontWeight.Normal,
                                modifier = Modifier.fillMaxWidth()
                                    .drawOpacity(animate(if (animatingFab.value) 0f else 1f))
                                    .padding(top = 20.dp, bottom = 20.dp, start = 10.dp)
                            )
                        }, elevation = 0.dp)
                        Spacer(modifier = Modifier.preferredHeight(16.dp))
                        TabRow(
                            items = tabTitles,
                            scrollable = true,
                            contentColor = Color.White,
                            indicatorContainer = { tabPositions ->
                                TabRow.IndicatorContainer(
                                    tabPositions,
                                    categories.indexOf(currentCategory)
                                ) {
                                    Box(
                                        Modifier.fillMaxWidth().widthIn(maxWidth = 80.dp)
                                            .preferredHeight(5.dp),
                                        backgroundColor = Color.White,
                                        shape = RoundedCornerShape(16.dp, 16.dp, 0.dp, 0.dp)
                                    )
                                }
                            },
                            selectedIndex = categories.indexOf(currentCategory),
                            divider = { },
                            modifier = Modifier.wrapContentWidth().offset(x = (-36).dp)
                        ) { index, currentTab ->
                            Tab(
                                selected = categories.indexOf(currentCategory) == index,
                                onSelected = { updateSelectedCategory(categories[index]) }
                            )
                            {
                                val textColor = if (categories.indexOf(currentCategory) == index) {
                                    1f
                                } else {
                                    0.65f
                                }
                                Text(
                                    text = currentTab.title,
                                    fontWeight = FontWeight.Normal,
                                    textAlign = TextAlign.Center,
                                    color = Color.White,
                                    modifier = Modifier.padding(
                                        top = 16.dp,
                                        bottom = 12.dp,
                                        start = 16.dp,
                                        end = 16.dp
                                    ).drawOpacity(textColor)
                                )
                            }
                        }
                    }
                    Box(
                        backgroundColor = Color.White
                    ) {
                        if ((currentCategory == Category.PendingBelongings &&
                                    pendingBelongings.isEmpty()) ||
                            (currentCategory == Category.Belongings &&
                                    pendingBelongings.isEmpty())
                        ) {
                            emptyView(currentCategory)
                        } else {
                            Spacer(modifier = Modifier.height(12.dp))
                            LazyColumnItems(
                                items = pendingBelongings
                            ) { item ->
                                if ((currentCategory == Category.PendingBelongings)) {
                                    pendingItem(item)
                                } else if ((currentCategory == Category.Belongings)) {
                                    ownedItem(item)
                                }
                            }
                        }
                    }
                }
            })
    }
}

@Composable
internal fun ownedItem(item: Belonging) {
    Box(
        backgroundColor = MaterialTheme.colors.surface,
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier.wrapContentHeight().fillMaxWidth()
            .padding(16.dp)
            .drawShadow(2.dp, RoundedCornerShape(16.dp))
            .clickable(onClick = {})
    ) {
        ConstraintLayout(modifier = Modifier.fillMaxWidth()) {
            val (text1, text2, text3) = createRefs()

            Text(
                text = item.name,
                modifier = Modifier.constrainAs(text1) {
                    top.linkTo(this.parent.top, margin = 16.dp)
                    start.linkTo(this.parent.start, margin = 20.dp)
                },
                fontWeight = FontWeight.W600,
                fontSize = TextUnit.Companion.Sp(18),
                color = MaterialTheme.colors.onSurface
            )

            Text(
                text = item.store,
                modifier = Modifier.constrainAs(text2) {
                    start.linkTo(text1.start)
                    top.linkTo(text1.bottom, margin = 4.dp)
                    bottom.linkTo(this.parent.bottom, margin = 16.dp)
                },
                fontSize = TextUnit.Companion.Sp(12),
                color = MaterialTheme.colors.onSurface
            )
        }
    }
}

@Composable
internal fun pendingItem(item: Belonging) {
    Box(
        backgroundColor = MaterialTheme.colors.surface,
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier.wrapContentHeight().fillMaxWidth()
            .padding(16.dp)
            .drawShadow(2.dp, RoundedCornerShape(16.dp))
            .clickable(onClick = {})
    ) {
        ConstraintLayout(modifier = Modifier.fillMaxWidth()) {
            val (text1, text2, text3) = createRefs()

            Text(
                text = item.name,
                modifier = Modifier.constrainAs(text1) {
                    top.linkTo(this.parent.top, margin = 16.dp)
                    start.linkTo(this.parent.start, margin = 20.dp)
                },
                fontWeight = FontWeight.W600,
                fontSize = TextUnit.Companion.Sp(18),
                color = MaterialTheme.colors.onSurface
            )

            Text(
                text = item.store,
                modifier = Modifier.constrainAs(text2) {
                    start.linkTo(text1.start)
                    top.linkTo(text1.bottom, margin = 4.dp)
                    bottom.linkTo(this.parent.bottom, margin = 16.dp)
                },
                fontSize = TextUnit.Companion.Sp(12),
                color = MaterialTheme.colors.onSurface
            )

            Stack(
                modifier = Modifier.wrapContentSize()
                    .constrainAs(text3) {
                        centerVerticallyTo(this.parent)
                        end.linkTo(this.parent.end, margin = 16.dp)
                    }) {
                CircularProgressIndicator(
                    color = MaterialTheme.colors.secondary,
                    progress = 1f,
                    modifier = Modifier.gravity(align = Alignment.Center)
                        .drawOpacity(0.4f).size(55.dp)
                )
                CircularProgressIndicator(
                    color = MaterialTheme.colors.secondary,
                    progress = 0.5f,
                    modifier = Modifier.gravity(align = Alignment.Center).size(55.dp)
                )
                Text(
                    text = "1d",
                    modifier = Modifier.gravity(align = Alignment.Center)
                        .padding(top = 2.dp),
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colors.primary,
                    fontSize = TextUnit.Companion.Sp(14)
                )
            }
        }
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
        Spacer(modifier = Modifier.height(32.dp))
        Text(
            text = message,
            color = MaterialTheme.colors.primary,
            textAlign = TextAlign.Center,
            modifier = Modifier.gravity(align = Alignment.CenterHorizontally)
                .preferredWidth(260.dp)
        )
    }
}