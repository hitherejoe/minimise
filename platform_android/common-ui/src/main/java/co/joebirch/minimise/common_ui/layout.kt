package co.joebirch.minimise.common_ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.emptyContent
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun RoundedBackgroundBox(
    modifier: Modifier = Modifier,
    children: @Composable () -> Unit = emptyContent(),
) {
    Box(
        modifier = modifier.background(
            MaterialTheme.colors.surface, RoundedCornerShape(16.dp)
        )
    ) {
        children()
    }
}