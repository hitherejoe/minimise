package co.joebirch.minimise.dashboard

import android.view.ViewGroup
import androidx.compose.Composable
import androidx.compose.state
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.ui.core.Alignment
import androidx.ui.core.Text
import androidx.ui.layout.*
import androidx.ui.material.*
import androidx.ui.text.TextStyle
import androidx.ui.unit.dp
import co.joebirch.minimise.common_ui.observe
import co.joebirch.minimise.common_ui.setContentWithLifecycle

fun ViewGroup.composeDashboardContent(
    lifecycleOwner: LifecycleOwner,
    state: LiveData<DashboardState>,
    categories: List<Category>,
    updateSelectedCategory: (Category) -> Unit
): Any = setContentWithLifecycle(lifecycleOwner) {
    ComposeInventoryContent(state, categories, updateSelectedCategory)
}

@Composable
private fun ComposeInventoryContent(
    state: LiveData<DashboardState>,
    categories: List<Category>,
    updateSelectedCategory: (Category) -> Unit
) {
    val viewState = observe(state)
    if (viewState != null) {
        DashboardContent(viewState.selectedCategory, categories, updateSelectedCategory)
    }
}

@Composable
private fun DashboardContent(
    currentCategory: Category,
    categories: List<Category>,
    updateSelectedCategory: (Category) -> Unit
) {
    val tabTitles = categories.map { it }
    Scaffold(
        topAppBar = {
            TopAppBar(title = {
                Text(text = "Minimise")
            }, elevation = 0.dp)
        },
        bodyContent = {
            Column {

                TabRow(
                    items = tabTitles, selectedIndex = categories.indexOf(currentCategory)
                ) { index, currentTab ->
                    Tab(
                        selected = categories.indexOf(currentCategory) == index,
                        onSelected = { updateSelectedCategory(categories[index]) }
                    )
                    {
                        Text(
                            text = currentTab.title,
                            modifier = LayoutPadding(16.dp),
                            style = TextStyle()
                        )
                    }
                }
            }
        }
    )
}