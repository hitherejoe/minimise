package co.joebirch.minimise.dashboard

import android.graphics.fonts.FontFamily
import android.view.ViewGroup
import androidx.compose.Composable
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.ui.core.Alignment
import androidx.ui.core.Modifier
import androidx.ui.foundation.Icon
import androidx.ui.foundation.Text
import androidx.ui.layout.*
import androidx.ui.livedata.observeAsState
import androidx.ui.material.*
import androidx.ui.material.icons.Icons
import androidx.ui.material.icons.filled.Add
import androidx.ui.text.font.Font
import androidx.ui.text.font.ResourceFont
import androidx.ui.text.font.fontFamily
import androidx.ui.text.style.TextAlign
import androidx.ui.unit.dp
import co.joebirch.minimise.authentication.ui.R
import co.joebirch.minimise.common_ui.MinimiseTheme
import co.joebirch.minimise.common_ui.observe
import co.joebirch.minimise.common_ui.setContentWithLifecycle

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
        Scaffold(
            topAppBar = {
                TopAppBar(title = {
                    Text(
                        text = "M",
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )
                }, elevation = 0.dp)
            },
            floatingActionButton = {
                FloatingActionButton(onClick = {
                    navigateToCreation()
                }) {
                    Icon(asset = Icons.Filled.Add)
                }
            },
            floatingActionButtonPosition = Scaffold.FabPosition.End,
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