package co.joebirch.minimise.common_ui

import androidx.compose.runtime.Composable

abstract class Composablefactory<ViewModel> {

    @Composable abstract fun Content(
        viewModel: ViewModel
    )

}