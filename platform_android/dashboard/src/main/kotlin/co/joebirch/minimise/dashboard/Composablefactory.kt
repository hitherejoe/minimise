package co.joebirch.minimise.dashboard

import androidx.compose.runtime.Composable

abstract class Composablefactory<ViewModel> constructor() {

    @Composable abstract fun Content(viewModel: ViewModel)

}