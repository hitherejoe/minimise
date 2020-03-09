package co.joebirch.minimise.common_ui

import android.view.ViewGroup
import androidx.compose.Composable
import androidx.compose.Composition
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.ui.core.setContent

fun ViewGroup.setContentWithLifecycle(
    lifecycle: LifecycleOwner,
    content: @Composable() () -> Unit
): Composition {
    val composition = setContent(content = content)

    val observer = object : DefaultLifecycleObserver {
        override fun onDestroy(owner: LifecycleOwner) {
            owner.lifecycle.removeObserver(this)
            composition.dispose()
        }
    }
    lifecycle.lifecycle.addObserver(observer)

    return composition
}