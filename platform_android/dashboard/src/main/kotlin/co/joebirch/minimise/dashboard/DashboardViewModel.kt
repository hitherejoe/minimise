package co.joebirch.minimise.dashboard

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import co.joebirch.minimise.android.core.di.BaseViewModel
import co.joebirch.minimise.android.core.di.default
import javax.inject.Inject

class DashboardViewModel @Inject constructor(
) : BaseViewModel(), DashboardView {

    private val uiState =
        MutableLiveData<DashboardState>().default(
            DashboardState.Idle()
        )

    fun observeAuthenticationState(): LiveData<DashboardState> = uiState

    override fun setSelectedCategory(category: Category) {
        uiState.postValue(
            uiState.value!!.build {
                this.category = category
            }
        )
    }
}