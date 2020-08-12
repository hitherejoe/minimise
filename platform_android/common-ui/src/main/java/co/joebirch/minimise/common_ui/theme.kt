package co.joebirch.minimise.common_ui

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource

@Composable
fun MinimiseTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable() () -> Unit
) {
    MaterialTheme(
        colors = lightColors(
            primary = colorResource(id = R.color.primaryColor),
            primaryVariant = colorResource(id = R.color.primaryDarkColor),
            onPrimary = colorResource(id = R.color.color_on_primary),
            secondary = colorResource(id = R.color.secondaryColor),
            secondaryVariant = colorResource(id = R.color.secondaryDarkColor),
            onSecondary = colorResource(id = R.color.color_on_secondary),
            error = colorResource(id = R.color.color_error),
            surface = colorResource(id = R.color.color_surface),
            onSurface = colorResource(id = R.color.color_on_surface),
            background = colorResource(id = R.color.color_background),
            onBackground = colorResource(id = R.color.color_on_background)
        ),
        content = content,
        typography = themeTypography
    )
}
