package co.joebirch.minimise.authentication

import androidx.compose.runtime.Composable
import co.joebirch.minimise.navigation.NavigationController

abstract class Composablefactory<ViewModel> {

    @Composable abstract fun Content(
        viewModel: ViewModel
    )

}