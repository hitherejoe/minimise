package co.joebirch.minimise.android.core.di

import androidx.lifecycle.ViewModel
import androidx.navigation.NavDirections
import co.joebirch.minimise.navigation.NavigationCommand

abstract class BaseViewModel : ViewModel() {

    val navigationCommands = SingleLiveEvent<NavigationCommand>()

    fun navigate(
        directions: NavDirections,
        finishActivity: Boolean = false
    ) {
        navigationCommands.postValue(if (finishActivity) {
            NavigationCommand.ToAndFinishActivity(directions)
        } else NavigationCommand.To(directions))
    }

}