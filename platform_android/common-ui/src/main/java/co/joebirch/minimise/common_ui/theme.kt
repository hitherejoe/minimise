package co.joebirch.minimise.common_ui

import androidx.ui.material.lightColorPalette
import androidx.compose.Composable
import androidx.ui.foundation.isSystemInDarkTheme
import androidx.ui.graphics.Color
import androidx.ui.material.MaterialTheme
import androidx.ui.res.colorResource

@Composable
fun MinimiseTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable() () -> Unit
) {
    MaterialTheme(
        colors = lightColorPalette(
            primary = colorResource(id = R.color.primaryColor),
            primaryVariant = colorResource(id = R.color.primaryDarkColor),
            onPrimary = Color.White,
            secondary = colorResource(id = R.color.secondaryColor),
            secondaryVariant = colorResource(id = R.color.secondaryDarkColor),
            onSecondary = Color.White,
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
