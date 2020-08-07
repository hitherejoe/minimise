package co.joebirch.minimise.common_ui

import android.view.ViewGroup
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Composition
import androidx.compose.runtime.Recomposer
import androidx.compose.ui.platform.setContent
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner

fun ViewGroup.setContentWithLifecycle(
    lifecycle: LifecycleOwner,
    content: @Composable() () -> Unit
): Composition {
    val composition = setContent(recomposer = Recomposer.current(), content = content)

    val observer = object : DefaultLifecycleObserver {
        override fun onDestroy(owner: LifecycleOwner) {
            owner.lifecycle.removeObserver(this)
            composition.dispose()
        }
    }
    lifecycle.lifecycle.addObserver(observer)

    return composition
}