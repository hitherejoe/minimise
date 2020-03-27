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
    state: LiveData<Any>,
    categories: List<Category>
): Any = setContentWithLifecycle(lifecycleOwner) {
    ComposeInventoryContent(state, categories)
}

@Composable
private fun ComposeInventoryContent(
    state: LiveData<Any>,
    categories: List<Category>
) {
    val viewState = observe(state)
    if (viewState != null) {
        FormContent(categories)
    }
}

@Composable
private fun FormContent(
    categories: List<Category>
) {
    var tab by state { Category.PendingBelongings as Category }
    val tabTitles = categories.map { it }
    Column(
        modifier = LayoutSize.Fill,
        arrangement = Arrangement.SpaceEvenly
    ) {
        TopAppBar(title = {
            Text(text = "Minimise")
        })
        Align(alignment = Alignment.TopCenter) {
            TabRow(
                items = tabTitles, selectedIndex = categories.indexOf(tab)
            ) { index, currentTab ->
                Tab(
                    selected = categories.indexOf(tab) == index,
                    onSelected = { tab = categories[index] }
                )
                {
                    Text(text = currentTab.title, modifier = LayoutPadding(16.dp), style = TextStyle())
                }
            }
        }
    }
}