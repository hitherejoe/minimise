package co.joebirch.minimise.dashboard

import androidx.compose.animation.animate
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumnFor
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.TabDefaults.tabIndicatorOffset
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.AmbientContext
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import co.joebirch.minimise.common_ui.MinimiseTheme
import co.joebirch.minimise.shared.dashboard.Belonging
import co.joebirch.minimise.shared.dashboard.Category
import javax.inject.Inject

class DashboardContentFactory @Inject constructor(
): Composablefactory<DashboardViewModel>() {

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
            val animatingFab = remember { mutableStateOf(false) }
            var toState by remember { mutableStateOf(MultiFabState.COLLAPSED) }
            Scaffold(
                backgroundColor = Color.White,
                floatingActionButton = {
                    MultiFloatingActionButton(
                        imageResource(id = R.drawable.plus),
                        listOf(
                            MultiFabItem(
                                "new",
                                imageResource(id = R.drawable.plus), "New item"
                            ),
                            MultiFabItem(
                                "existing",
                                imageResource(id = R.drawable.plus), "Owned item"
                            )
                        ), toState, true, {
                            toState = it
                        }
                    ) {

                    }
                    /*
                    val resources = ContextAmbient.current.resources
                    Box(
                        modifier = Modifier.clickable(
                            onClick = {
                                animatingFab.value = true
                                //Timer().schedule(450) {
                                   // navigateToCreation()
                               // }
                            }, indication = rememberRipple(
                                bounded = false,
                                radius = 26.dp
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
                     */
                },
                bodyContent = {
                    Box {
                        Column {
                            Column(
                                modifier = Modifier.background(
                                    color = MaterialTheme.colors.primary,
                                    shape = RoundedCornerShape(bottomEnd = 18.dp, bottomStart = 18.dp)
                                )
                            ) {
                                Spacer(modifier = Modifier.preferredHeight(8.dp))
                                TopAppBar(title = {
                                    Text(
                                        text = "",
                                        color = MaterialTheme.colors.onPrimary,
                                        textAlign = TextAlign.Start,
                                        fontSize = 20.sp,
                                        fontWeight = FontWeight.Normal,
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .alpha(animate(if (animatingFab.value) 0f else 1f))
                                            .padding(top = 20.dp, start = 10.dp)
                                    )
                                }, elevation = 0.dp)
                                Spacer(modifier = Modifier.preferredHeight(16.dp))
                                ScrollableTabRow(
                                    modifier = Modifier.width(350.dp),
                                    edgePadding = 24.dp,
                                    contentColor = Color.White,
                                    selectedTabIndex = categories.indexOf(currentCategory),
                                    divider = { },
                                    indicator = { tabPositions: List<TabPosition> ->
                                        val modifier = Modifier.tabIndicatorOffset(
                                            tabPositions[categories.indexOf(currentCategory)]
                                        )
                                        Modifier.offset()
                                        Box(
                                            modifier
                                                .fillMaxWidth()
                                                .preferredHeight(5.dp)
                                                .background(
                                                    Color.White,
                                                    RoundedCornerShape(
                                                        topStart = 16.dp,
                                                        topEnd = 16.dp
                                                    )
                                                )
                                        )
                                    }
                                ) {
                                    tabTitles.forEachIndexed { index, category ->
                                        Tab(
                                            selected = categories.indexOf(currentCategory) == index,
                                            onClick = { updateSelectedCategory(categories[index]) }
                                        )
                                        {
                                            val textColor =
                                                if (categories.indexOf(currentCategory) == index) {
                                                    1f
                                                } else {
                                                    0.65f
                                                }
                                            Text(
                                                text = category.title,
                                                fontWeight = FontWeight.Normal,
                                                textAlign = TextAlign.Center,
                                                color = Color.White,
                                                modifier = Modifier
                                                    .padding(
                                                        top = 16.dp,
                                                        bottom = 12.dp,
                                                        start = 16.dp,
                                                        end = 16.dp
                                                    )
                                                    .alpha(textColor)
                                            )
                                        }
                                    }
                                }
                            }
                            Box(
                                modifier = Modifier.background(Color.White)
                            ) {
                                if ((currentCategory == Category.PendingBelongings &&
                                            pendingBelongings.isEmpty()) ||
                                    (currentCategory == Category.Belongings &&
                                            pendingBelongings.isEmpty())
                                ) {
                                    emptyView(currentCategory)
                                } else {
                                    Spacer(modifier = Modifier.height(12.dp))
                                    LazyColumnFor(
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
                        val alpha = if (toState == MultiFabState.EXPANDED) 0.4f else 0f
                        Box(
                            modifier = Modifier
                               // .alpha(animateAsState(alpha).value)
                                .background(
                                    Color(AmbientContext.current.resources.getColor(R.color.transparent_black))
                                )
                                .fillMaxSize()
                        )
                    }
                })
        }
    }

    @Composable
    internal fun ownedItem(item: Belonging) {
        Box(
            modifier = Modifier
                .wrapContentHeight()
                .fillMaxWidth()
                .padding(16.dp)
                .shadow(2.dp, RoundedCornerShape(16.dp))
                .clickable(onClick = {})
                .background(
                    MaterialTheme.colors.surface,
                    RoundedCornerShape(16.dp)
                )
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
                    fontSize = 18.sp,
                    color = MaterialTheme.colors.onSurface
                )

                Text(
                    text = item.store,
                    modifier = Modifier.constrainAs(text2) {
                        start.linkTo(text1.start)
                        top.linkTo(text1.bottom, margin = 4.dp)
                        bottom.linkTo(this.parent.bottom, margin = 16.dp)
                    },
                    fontSize = 12.sp,
                    color = MaterialTheme.colors.onSurface
                )
            }
        }
    }

    @Composable
    internal fun pendingItem(item: Belonging) {
        Box(
            modifier = Modifier
                .wrapContentHeight()
                .fillMaxWidth()
                .padding(16.dp)
                .shadow(2.dp, RoundedCornerShape(16.dp))
                .clickable(onClick = {})
                .background(
                    MaterialTheme.colors.surface,
                    RoundedCornerShape(16.dp)
                )
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
                    fontSize = 18.sp,
                    color = MaterialTheme.colors.onSurface
                )

                Text(
                    text = item.store,
                    modifier = Modifier.constrainAs(text2) {
                        start.linkTo(text1.start)
                        top.linkTo(text1.bottom, margin = 4.dp)
                        bottom.linkTo(this.parent.bottom, margin = 16.dp)
                    },
                    fontSize = 12.sp,
                    color = MaterialTheme.colors.onSurface
                )

                Box(
                    modifier = Modifier
                        .wrapContentSize()
                        .constrainAs(text3) {
                            centerVerticallyTo(this.parent)
                            end.linkTo(this.parent.end, margin = 16.dp)
                        }) {
                    CircularProgressIndicator(
                        color = MaterialTheme.colors.secondary,
                        progress = 1f,
                        modifier = Modifier
                            .align(Alignment.Center)
                            .alpha(0.4f)
                            .size(55.dp)
                    )
                    CircularProgressIndicator(
                        color = MaterialTheme.colors.secondary,
                        progress = 0.5f,
                        modifier = Modifier
                            .align(Alignment.Center)
                            .size(55.dp)
                    )
                    Text(
                        text = "1d",
                        modifier = Modifier
                            .align(Alignment.Center)
                            .padding(top = 2.dp),
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colors.primary,
                        fontSize = 14.sp
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
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .preferredWidth(260.dp)
            )
        }
    }

    @Composable
    override fun Content(viewModel: DashboardViewModel) {
        val state by viewModel.uiState.observeAsState()
        DashboardContent(
            state!!.selectedCategory,
            listOf(Category.PendingBelongings, Category.Belongings),
            state!!.pendingBelongings,
            viewModel::setSelectedCategory,
            {  }
        )
    }
}