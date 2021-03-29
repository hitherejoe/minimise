package co.joebirch.minimise.android

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import co.joebirch.minimise.android.core.di.Preferences
import co.joebirch.minimise.android.core.di.default

class MainViewModel @ViewModelInject constructor(
    sharedPreferences: Preferences
) : ViewModel() {

    private var uiState =
        MutableLiveData<MainState>().default(
            MainState.Loading
        )

    fun observeState(): LiveData<MainState> = uiState

    init {
        sharedPreferences.accessToken?.let {
            uiState.postValue(MainState.Authenticated)
        } ?: run {
            uiState.postValue(MainState.NotAuthenticated)
        }
    }
}