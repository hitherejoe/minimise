package co.joebirch.minimise.dashboard

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import co.joebirch.minimise.android.core.di.BaseViewModel
import co.joebirch.minimise.android.core.di.default
import javax.inject.Inject

class DashboardViewModel @Inject constructor(
) : BaseViewModel() {

    private val uiState =
        MutableLiveData<Any>().default(
            ""
        )

    fun observeAuthenticationState(): LiveData<Any> = uiState

}